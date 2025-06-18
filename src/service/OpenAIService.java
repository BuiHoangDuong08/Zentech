package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import entity.ChatMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class OpenAIService {

    private static String OPENAI_API_URL;
    private static String API_KEY;
    private static String MODEL;
    private static int MAX_TOKENS;
    private static double TEMPERATURE; // độ sáng tạo

    private UserService userService;
    private InventoryService inventoryService;

    static {
        loadConfiguration();
    }

    private static void loadConfiguration() {
        Properties props = new Properties();
        try (InputStream input = OpenAIService.class.getClassLoader().getResourceAsStream("config/openai.properties")) {
            props.load(input);
            OPENAI_API_URL = props.getProperty("openai.api.url");
            API_KEY = props.getProperty("openai.api.key");
            MODEL = props.getProperty("openai.model");
            MAX_TOKENS = Integer.parseInt(props.getProperty("openai.max.tokens"));
            TEMPERATURE = Double.parseDouble(props.getProperty("openai.temperature"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String userMessage, List<ChatMessage> conversationHistory) {
        try {
            URL url = new URL(OPENAI_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setDoOutput(true);
            connection.setConnectTimeout(30_000);
            connection.setReadTimeout(60_000);

            //Body request
            String requestBody = buildRequestBody(userMessage, conversationHistory);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String responseStr = readResponseBody(connection.getInputStream());
                return parseResponse(responseStr);
            } else {
                String errorStr = readResponseBody(connection.getErrorStream());
                System.err.println("API ERROR (" + responseCode + "): " + errorStr);
                return getErrorMessage(responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi – không thể kết nối OpenAI: " + e.getMessage();
        }
    }

    private String buildRequestBody(String userMessage, List<ChatMessage> history) {
        StringBuilder json = new StringBuilder();

        String contextData = enrichUserMessage(userMessage).replaceAll("[\\r\\n]+", " ");

        json.append("{")
                .append("\"model\":\"").append(MODEL).append("\",")
                .append("\"max_tokens\":").append(MAX_TOKENS).append(',')
                .append("\"temperature\":").append(TEMPERATURE).append(',')
                .append("\"messages\":[");

        json.append("{\"role\": \"system\", \"content\": \"");
        json.append("Bạn là trợ lý AI tên Zen của phần mềm quản lý quán cà phê ZenTech. ");
        if (!contextData.isEmpty()) {
            json.append("Dữ liệu nội bộ: ").append(contextData);
        }
        json.append("Chỉ trả lời các câu hỏi liên quan đến phần mềm, sản phẩm, tài khoản và các chức năng của hệ thống. ");
        json.append("Nếu câu hỏi không liên quan, hãy từ chối lịch sự. ");
        json.append("Trả lời ngắn gọn, rõ ràng, tiếng Việt. ");
        json.append("Phần mềm có các chức năng: quản lý thẻ định danh, sản phẩm, kho, nhân viên, nhà cung cấp.");
        json.append("\"},");

        for (ChatMessage msg : history) {
            json.append("{\"role\":\"").append(msg.getRole()).append("\",\"content\":\"")
                    .append(escape(msg.getContent())).append("\"},");
        }

        json.append("{\"role\":\"user\",\"content\":\"")
                .append(escape(userMessage)).append("\"}");

        json.append("]}");
        return json.toString();
    }

    private String enrichUserMessage(String rawUserInput) {
        String lower = rawUserInput.toLowerCase();
        StringBuilder enriched = new StringBuilder();
        enriched.append("Câu hỏi người dùng: ").append(rawUserInput).append("\n");

        if (containsQuantityKeyword(lower)) {
            if (lower.contains("nhân viên") || lower.contains("tài khoản") || lower.contains("người dùng")) {
                userService = new UserService();
                enriched.append("Số lượng nhân viên hiện tại: ")
                        .append(userService.getUserCount()).append(". \n");
            }
            if (lower.contains("sản phẩm")) {
                inventoryService = new InventoryService();
                enriched.append("Tổng sản phẩm trong hệ thống: ")
                        .append(inventoryService.getProductCount()).append(". \n");
            }
        }
        return enriched.toString();
    }

    private boolean containsQuantityKeyword(String input) {
        return input.contains("bao nhiêu") || input.contains("số lượng") || input.contains("tổng số") || input.contains("mấy");
    }

    private String readResponseBody(InputStream is) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }

    private String parseResponse(String json) {
        final boolean DEBUG = false;
        try {
            JsonElement rootElm = JsonParser.parseString(json);
            if (!rootElm.isJsonObject()) {
                return "Xin lỗi, tôi chưa hiểu câu hỏi.";
            }
            JsonObject root = rootElm.getAsJsonObject();

            if (root.has("error")) {
                String code = root.getAsJsonObject("error").has("code")
                        ? root.getAsJsonObject("error").get("code").getAsString() : "unknown";
                return USER_ERROR.getOrDefault(code, USER_ERROR.get("unknown"));
            }

            JsonArray choices = root.getAsJsonArray("choices");
            if (choices == null || choices.isEmpty()) {
                return USER_ERROR.get("server_error");
            }
            for (JsonElement e : choices) {
                JsonObject msgObj = e.getAsJsonObject().getAsJsonObject("message");
                if (msgObj != null && "assistant".equals(msgObj.get("role").getAsString())) {
                    return msgObj.get("content").getAsString().trim();
                }
            }
            return USER_ERROR.get("server_error");
        } catch (JsonSyntaxException ex) {
            if (DEBUG) {
                ex.printStackTrace();
            }
            return "Đã xảy ra lỗi khi xử lý phản hồi. Vui lòng thử lại.";
        }
    }

    private String escape(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    private String getErrorMessage(int code) {
        switch (code) {
            case 401:
                return "Lỗi xác thực API key.";
            case 429:
                return "Đã vượt quá giới hạn API.";
            case 400:
                return "Yêu cầu không hợp lệ.";
            case 500:
                return "Lỗi server OpenAI.";
            case 503:
                return "Dịch vụ OpenAI tạm thời không khả dụng.";
            default:
                return "Lỗi OpenAI (" + code + ").";
        }
    }

    private static final Map<String, String> USER_ERROR = Map.ofEntries(
            Map.entry("invalid_api_key", "Khoá truy cập không hợp lệ. Liên hệ quản trị viên."),
            Map.entry("insufficient_quota", "Hết lượt sử dụng. Hãy thử lại sau."),
            Map.entry("rate_limit_exceeded", "Hệ thống đang quá tải, thử lại sau ít phút."),
            Map.entry("context_length_exceeded", "Câu hỏi quá dài, hãy rút gọn."),
            Map.entry("server_error", "Máy chủ đang bận, vui lòng thử lại sau."),
            Map.entry("timeout", "Kết nối chậm, kiểm tra mạng rồi thử lại."),
            Map.entry("unknown", "Xin lỗi, hiện tại tôi chưa thể trả lời."));
}

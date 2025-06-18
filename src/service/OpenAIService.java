package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import entity.ChatMessage;
import entity.Product;
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
import zentechx.menu.Menu;
//MỌI CONFIGURATION ĐỀU DỰA TRÊN DOCS CỦA OPENAI API
public class OpenAIService {

    private static String OPENAI_API_URL;
    private static String API_KEY;
    private static String MODEL;
    private static int MAX_TOKENS;
    private static double TEMPERATURE; // độ sáng tạo

    static {
        loadConfiguration();
    }

    private static void loadConfiguration() {
        Properties props = new Properties(); //object này để đọc file config

        try (InputStream input = OpenAIService.class.getClassLoader().getResourceAsStream("config/openai.properties")) {
            props.load(input);
            // doc gia tri
            OPENAI_API_URL = props.getProperty("openai.api.url");
            API_KEY = props.getProperty("openai.api.key");
            MODEL = props.getProperty("openai.model");
            // ep kieu string sang so nguyen
            MAX_TOKENS = Integer.parseInt(props.getProperty("openai.max.tokens"));
            // ep kieu string sang so thuc
            TEMPERATURE = Double.parseDouble(props.getProperty("openai.temperature"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method gui tin nhan den API
    public String sendMessage(String userMessage, List<ChatMessage> conversationHistory) {
        try {
            URL url = new URL(OPENAI_API_URL);
            // mở kết nối http đến API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // cấu hình request method và header
            connection.setRequestMethod("POST"); //thiết lập phương thức http là POST vì Openai API yêu cầu sử dụng POST method để gửi dữ liệu
            connection.setRequestProperty("Content-Type", "application/json"); //định dạng dữ liệu gửi là JSON để thông báo cho SV biết dữ liệu gửi đi có dạng này
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY); //xác thực bằng Bearer token vì Openai sử dụng Bearer token để xác định người dùng và kiểm tra quyền truy cập
            connection.setDoOutput(true); //cho phép ghi dữ liệu ra output stream vì mặc định HttpURLConnection không cho phép output
            connection.setConnectTimeout(30000); //thiết lập thời gian chờ tối đa là 30s để kết nối
            connection.setReadTimeout(60000); //thiết lập thời gian chờ tối đa để đọc data từ SV

            String requestBody = buildRequestBody(userMessage, conversationHistory);
            System.out.println(requestBody);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }

            //đọc response từ SV
            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);

            //nếu request thành công thì thực thi code trong if (200 OK)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //đọc và parse response
                String responseStr = readResponseBody(connection.getInputStream());
                System.out.println(responseStr);
                return parseResponse(responseStr);
            } else {
                String errorStr = readResponseBody(connection.getErrorStream());
                System.err.println("API LOI (Code " + responseCode + "): " + errorStr);
                return getErrorMessage(responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi, không thể kết nối! Lỗi: " + e.getMessage();
        }
    }

    //method này dùng để đọc toàn bộ nội dung từ inputStream và chuyển sang String
    private String readResponseBody(InputStream inputStream) throws IOException {
        //InputStreamReader để chuyển đổi byte stream thành character stream
        //StandardCharsets.UTF_8 để đọc đúng ký tự tiếng Việt và emoji
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            //cái này để lưu từng lines đọc được từ stream
            String line;

            //vòng lặp while ày để đọc từng dòng cho tới khi null (hết dữ liệu)
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    //Cái để parse JSON ra sử dụng gson của google
    private String parseResponse(String json) {

        //Bật để xem log DEV
        final boolean DEBUG = true;

        try {
            //JSON phải là object
            JsonElement rootElm = JsonParser.parseString(json);
            if (!rootElm.isJsonObject()) {
                if (DEBUG) {
                    System.err.println("DEV> Root không phải object: " + json);
                }
                return "Xin lỗi, tôi chưa thể xử lý câu hỏi. Bạn hãy thử lại sau.";
            }

            JsonObject root = rootElm.getAsJsonObject();

            //Lỗi Server
            if (root.has("error")) {
                JsonObject err = root.getAsJsonObject("error");
                String code = err.has("code") ? err.get("code").getAsString() : "unknown";
                if (DEBUG) {
                    System.err.printf("DEV> OpenAI error [%s]: %s%n",
                            code,
                            err.has("message") ? err.get("message").getAsString() : "");
                }

                return USER_ERROR.getOrDefault(code, USER_ERROR.get("unknown"));
            }

            //Lấy choices
            JsonArray choices = root.getAsJsonArray("choices");
            if (choices == null || choices.size() == 0) {
                if (DEBUG) {
                    System.err.println("DEV> Không có 'choices' trong phản hồi.");
                }
                return USER_ERROR.get("server_error");
            }

            //Tìm assistant đầu tiên
            for (JsonElement e : choices) {
                JsonObject msgObj = e.getAsJsonObject().getAsJsonObject("message");
                if (msgObj != null && "assistant".equals(msgObj.get("role").getAsString())) {
                    String content = msgObj.get("content").getAsString().trim();
                    if (DEBUG) {
                        System.out.println("DEV> content = " + content);
                    }
                    return content;
                }
            }

            //Không tìm thấy phản hồi từ Chat
            if (DEBUG) {
                System.err.println("DEV> Chưa tìm thấy role=assistant trong choices.");
            }
            return USER_ERROR.get("server_error");

        } catch (JsonSyntaxException ex) {
            if (DEBUG) {
                ex.printStackTrace();
            }
            return "Đã xảy ra lỗi khi xử lý phản hồi. Vui lòng thử lại.";
        } catch (Exception ex) {
            if (DEBUG) {
                ex.printStackTrace();
            }
            return USER_ERROR.get("unknown");
        }
    }

    private int findClosingQuote(String text, int startPos) {
        for (int i = startPos; i < text.length(); i++) {
            if (text.charAt(i) == '"') {
                // Kiểm tra xem dấu " này có bị escape không
                if (i == 0 || text.charAt(i - 1) != '\\') {
                    return i; // Tìm thấy dấu " không bị escape
                }
            }
        }
        return -1; // Không tìm thấy
    }

    private String buildRequestBody(String userMessage, List<ChatMessage> history) {
        StringBuilder json = new StringBuilder();
        json.append("{")
                .append("\"model\":\"").append(MODEL).append("\",")
                .append("\"max_tokens\":").append(MAX_TOKENS).append(',')
                .append("\"temperature\":").append(TEMPERATURE).append(',')
                .append("\"messages\":[");
        json.append("{\"role\": \"system\", \"content\": \"");
        json.append("Bạn là trợ lý AI tên Zen của phần mềm quản lý quán cafe ZenTech. ");
        json.append("Chỉ trả lời các câu hỏi liên quan đến phần mềm quản lý quán cà phê, sản phẩm, tài khoản và các chức năng của hệ thống. ");
        json.append("Nếu câu hỏi không liên quan, hãy từ chối trả lời một cách lịch sự. ");
        json.append("Trả lời ngắn gọn, rõ ràng và bằng tiếng Việt. ");
        json.append("Phần mềm ZenTech là phần mềm quản lý quán cà phê, có các chức năng: quản lý thẻ định danh, quản lý sản phẩm, quản lý kho.");
        json.append("\"},");
        //lịch sử (nếu có)
        for (ChatMessage msg : history) {
            json.append("{\"role\":\"").append(msg.getRole())
                    .append("\",\"content\":\"").append(escape(msg.getContent())).append("\"},");
        }
        //prompt mới
        json.append("{\"role\":\"user\",\"content\":\"").append(escape(userMessage)).append("\"}");
        json.append("]}");
        return json.toString();
    }

    private String listToString(List<?> list) {
        StringBuilder result = new StringBuilder();
        int index = 1;
        for (Object item : list) {
            result.append(index++).append(". ");
            result.append(item.toString()).append("\n");
        }
        return result.toString();
    }

    //thoát kí tự đặc biệt JSON
    private String escape(String s) {
        if (s == null) {
            return "";
        }
        return s
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
    }

    private String getErrorMessage(int responseCode) {
        switch (responseCode) {
            case 401:
                return "Lỗi xác thực API key. Vui lòng kiểm tra cấu hình API key.";
            case 429:
                return "Đã vượt quá giới hạn API. Vui lòng thử lại sau.";
            case 400:
                return "Yêu cầu không hợp lệ. Vui lòng thử lại với tin nhắn khác.";
            case 500:
                return "Lỗi server OpenAI. Vui lòng thử lại sau.";
            case 503:
                return "Dịch vụ OpenAI tạm thời không khả dụng. Vui lòng thử lại sau.";
            default:
                return "Lỗi từ OpenAI API (Code: " + responseCode + "). Vui lòng thử lại sau.";
        }
    }

    //Bắt lỗi (tránh đưa cho người dùng các lỗi quá hàn lâm)
    private static final Map<String, String> USER_ERROR = Map.ofEntries(
            Map.entry("invalid_api_key", "Khoá truy cập không hợp lệ. Vui lòng liên hệ quản trị viên."),
            Map.entry("insufficient_quota", "Tài khoản đã hết lượt sử dụng. Vui lòng chờ hoặc liên hệ hỗ trợ."),
            Map.entry("rate_limit_exceeded", "Hệ thống đang quá tải, bạn thử lại sau ít phút nhé."),
            Map.entry("context_length_exceeded", "Câu hỏi quá dài, hãy rút gọn rồi thử lại."),
            Map.entry("server_error", "Máy chủ đang bận, vui lòng thử lại sau."),
            Map.entry("timeout", "Kết nối chậm, kiểm tra mạng rồi thử lại."),
            Map.entry("unknown", "Xin lỗi, hiện tại tôi chưa thể trả lời. Bạn vui lòng thử lại sau!")
    );
}

package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class ConnectionHelper {
    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream input = ConnectionHelper.class.getResourceAsStream("/config/db.properties")) {
            Properties props = new Properties();
            props.load(input);

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

            //Optional: Load JDBC driver manually (for older versions)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tải cấu hình kết nối CSDL");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}


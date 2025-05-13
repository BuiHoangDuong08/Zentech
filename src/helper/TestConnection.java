package helper;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = ConnectionHelper.getConnection()) {
            System.out.println("Kết nối thành công tới CSDL!");
        } catch (Exception e) {
            System.out.println("Kết nối thất bại:");
            e.printStackTrace();
        }
    }
}

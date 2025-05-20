package dao;

import entity.User;
import helper.ConnectionHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {
    private Connection conn;
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE user_name = ?";
        try (Connection con = ConnectionHelper.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("full_name"));
                user.setGender(rs.getString("gender"));
                user.setAddress(rs.getString("address"));
                user.setDob(rs.getDate("dob"));
                user.setPhoneNumber(rs.getString("phone_number"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
public boolean updateUser(User user) {
    String sql = "UPDATE USER SET fullname=?, email=?, address=?, phonenumber=?, dob=?, gender=? WHERE id=?";

    try (Connection con = ConnectionHelper.getConnection(); 
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, user.getFullName());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getAddress());
        stmt.setString(4, user.getPhoneNumber());
        stmt.setDate(5, user.getDob());
        stmt.setString(6, user.getGender());
        stmt.setInt(7, user.getId()); // ✅ THÊM DÒNG NÀY

        int result = stmt.executeUpdate();
        System.out.println("Update result: " + result);
        return result > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}

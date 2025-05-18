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
                user.setId(rs.getInt("id"));
                user.setRoleId(rs.getInt("role_id"));
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
    String sql = "UPDATE USER SET FullName=?, Email=?, Address=?, PhoneNumber=?, DoB=?, Gender=?, Role_ID=? WHERE ID=?";
    try (Connection con = ConnectionHelper.getConnection(); 
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, user.getFullName());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getAddress());
        stmt.setString(4, user.getPhoneNumber());
        stmt.setDate(5, user.getDob()); // đảm bảo user.getDob() là java.sql.Date
        stmt.setString(6, user.getGender());
        stmt.setInt(7, user.getRoleId());
        stmt.setInt(8, user.getId());

        int result = stmt.executeUpdate();
        System.out.println("Update result: " + result);
        return result > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}

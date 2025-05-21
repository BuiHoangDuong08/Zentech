package dao;

import entity.User;
import helper.ConnectionHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface UserDAO {

    default List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, role_Id, userName, password, email, fullName, gender, address, dob, phoneNumber FROM USER";

        try (Connection con = ConnectionHelper.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setRoleId(rs.getInt("role_Id"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("fullName"));
                user.setGender(rs.getString("gender"));
                user.setAddress(rs.getString("address"));
                user.setDob(rs.getDate("dob"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                list.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    default boolean addUser(User user) {
        try (Connection con = ConnectionHelper.getConnection(); CallableStatement cs = con.prepareCall("{CALL insert_user(?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            cs.setInt(1, user.getRoleId());
            cs.setString(2, user.getUserName());
            cs.setString(3, user.getPassword());
            cs.setString(4, user.getEmail());
            cs.setString(5, user.getFullName());
            cs.setString(6, user.getGender());
            cs.setString(7, user.getAddress());
            cs.setDate(8, user.getDob());
            cs.setString(9, user.getPhoneNumber());
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean updateUser(User user) {
        try (Connection con = ConnectionHelper.getConnection(); CallableStatement cs = con.prepareCall("{CALL update_user(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            cs.setInt(1, user.getId());
            cs.setInt(2, user.getRoleId());
            cs.setString(3, user.getUserName());
            cs.setString(4, user.getPassword());
            cs.setString(5, user.getEmail());
            cs.setString(6, user.getFullName());
            cs.setString(7, user.getGender());
            cs.setString(8, user.getAddress());
            cs.setDate(9, user.getDob());
            cs.setString(10, user.getPhoneNumber());
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean deleteUser(int id) {
        try (Connection con = ConnectionHelper.getConnection(); CallableStatement cs = con.prepareCall("{CALL delete_user(?)}")) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean updatepassword(String newpass, String username, String oldpass) {
        String sqlupdatepass = "UPDATE USER SET password = ? WHERE username = ? AND password = ?";
        try (Connection conn = ConnectionHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sqlupdatepass)) {
            pst.setString(1, newpass);
            pst.setString(2, username);
            pst.setString(3, oldpass);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

package dao;

import entity.UserModel;
import helper.ConnectionHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface UserDAO {

    default boolean addUser(UserModel user) {
        try (Connection con = ConnectionHelper.getConnection(); CallableStatement cs = con.prepareCall("{CALL insert_user(?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            cs.setInt(1, 3);//mặc định khi tạo tài khoản quyền sẽ là nhân viên bán hàng
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

    default UserModel getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE user_name = ?";
        try (Connection con = ConnectionHelper.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UserModel user = new UserModel();
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

    default boolean updateUser(UserModel user) {
        String sql = "UPDATE USER SET fullname=?, email=?, address=?, phonenumber=?, dob=?, gender=? WHERE id=?";

        try (Connection con = ConnectionHelper.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getPhoneNumber());

            // ✅ Chuyển đổi ngày sinh từ LocalDate hoặc String sang java.sql.Date nếu cần
            java.sql.Date dob = user.getDob(); // đảm bảo user.setDob(java.sql.Date) từ trước
            stmt.setDate(5, dob);

            stmt.setString(6, user.getGender());

            stmt.setInt(7, user.getId());

            int result = stmt.executeUpdate();
            System.out.println("Update result: " + result);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean updatepassword(String newpass, String username) {
        String sqlupdatepass = "UPDATE USER SET password = ? WHERE username = ?";
        try (Connection conn = ConnectionHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sqlupdatepass)) {
            pst.setString(1, newpass);
            pst.setString(2, username);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default List<UserModel> getAllUsers() {
        List<UserModel> list = new ArrayList<>();
        String sql = "SELECT id, role_Id, userName, password, email, fullName, gender, address, dob, phoneNumber FROM USER";

        try (Connection con = ConnectionHelper.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UserModel user = new UserModel();
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

}

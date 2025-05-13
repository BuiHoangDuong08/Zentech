package dao;

import entity.Role;
import helper.ConnectionHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface RoleDAO {

    default List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT id, roleName FROM role";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setRoleName(rs.getString("roleName"));
                list.add(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    default boolean addRole(String name) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL insert_role(?)}")) {

            cs.setString(1, name);
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean updateRole(int id, String name) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL update_role(?, ?)}")) {

            cs.setInt(1, id);
            cs.setString(2, name);
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean deleteRole(int id) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL delete_role(?)}")) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

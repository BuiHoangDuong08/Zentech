
package dao;

import entity.RoleModel;
import helper.ConnectionHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class RoleDAO {
    public List<RoleModel> getAllRoles() {
        List<RoleModel> roles = new ArrayList<>();
        String sql = "SELECT ID, RoleName FROM ROLE";
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                roles.add(new RoleModel(rs.getInt("ID"), rs.getString("RoleName")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }
public String getRoleNameById(int id) {
    String sql = "SELECT RoleName FROM ROLE WHERE ID = ?";
    try (Connection con = ConnectionHelper.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("RoleName");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
public int getRoleIdByName(String roleName) {
    String sql = "SELECT ID FROM ROLE WHERE RoleName = ?";
    try (Connection con = ConnectionHelper.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, roleName);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("ID");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1; 
}
}

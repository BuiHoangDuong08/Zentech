package dao;

import entity.Category;
import helper.ConnectionHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface CategoryDAO {

    default List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT id, categoryName FROM category";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setCategoryName(rs.getString("categoryName"));
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    default boolean addCategory(String name) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL insert_category(?)}")) {

            cs.setString(1, name);
            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean updateCategory(int id, String name) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL update_category(?, ?)}")) {

            cs.setInt(1, id);
            cs.setString(2, name);
            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean deleteCategory(int id) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL delete_category(?)}")) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

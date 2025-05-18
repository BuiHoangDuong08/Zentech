package dao;

import entity.Product;
import helper.ConnectionHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface ProductDAO {

    default List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT ID, Category_ID, Name, Price, Active, Description, Image_URL FROM PRODUCT";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("ID"));
                p.setCategoryId(rs.getInt("Category_ID"));
                p.setName(rs.getString("Name"));
                p.setPrice(rs.getDouble("Price"));
                p.setActive(rs.getString("Active"));
                p.setDescription(rs.getString("Description"));
                p.setImageUrl(rs.getString("Image_URL"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    default boolean addProduct(Product p) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL insert_product(?, ?, ?, ?, ?, ?)}")) {

            cs.setInt(1, p.getCategoryId());
            cs.setString(2, p.getName());
            cs.setDouble(3, p.getPrice());
            cs.setString(4, p.getActive());
            cs.setString(5, p.getDescription());
            cs.setString(6, p.getImageUrl());
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean updateProduct(Product p) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL update_product(?, ?, ?, ?, ?, ?, ?)}")) {

            cs.setInt(1, p.getId());
            cs.setInt(2, p.getCategoryId());
            cs.setString(3, p.getName());
            cs.setDouble(4, p.getPrice());
            cs.setString(5, p.getActive());
            cs.setString(6, p.getDescription());
            cs.setString(7, p.getImageUrl());
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean deleteProduct(int id) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL delete_product(?)}")) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
package dao;

import entity.BillDetails;
import helper.ConnectionHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface BillDetailsDAO {

    default List<BillDetails> getAllBillDetails() {
        List<BillDetails> list = new ArrayList<>();
        String sql = "SELECT id, billId, productId, date, quantity, discount, totalPriceNoVAT, totalPriceWithVAT FROM BILLDETAILS";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BillDetails bd = new BillDetails();
                bd.setId(rs.getInt("id"));
                bd.setBillId(rs.getInt("billId"));
                bd.setProductId(rs.getInt("productId"));
                bd.setDate(rs.getTimestamp("date"));
                bd.setQuantity(rs.getInt("quantity"));
                bd.setDiscount(rs.getFloat("discount"));
                bd.setTotalPriceNoVAT(rs.getDouble("totalPriceNoVAT"));
                bd.setTotalPriceWithVAT(rs.getDouble("totalPriceWithVAT"));
                list.add(bd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    default boolean addBillDetail(BillDetails bd) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL insert_billdetails(?, ?, ?, ?, ?, ?, ?)}")) {

            cs.setInt(1, bd.getBillId());
            cs.setInt(2, bd.getProductId());
            cs.setTimestamp(3, bd.getDate());
            cs.setInt(4, bd.getQuantity());
            cs.setFloat(5, bd.getDiscount());
            cs.setDouble(6, bd.getTotalPriceNoVAT());
            cs.setDouble(7, bd.getTotalPriceWithVAT());
            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean updateBillDetail(BillDetails bd) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL update_billdetails(?, ?, ?, ?, ?, ?, ?, ?)}")) {

            cs.setInt(1, bd.getId());
            cs.setInt(2, bd.getBillId());
            cs.setInt(3, bd.getProductId());
            cs.setTimestamp(4, bd.getDate());
            cs.setInt(5, bd.getQuantity());
            cs.setFloat(6, bd.getDiscount());
            cs.setDouble(7, bd.getTotalPriceNoVAT());
            cs.setDouble(8, bd.getTotalPriceWithVAT());
            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean deleteBillDetail(int id) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL delete_billdetails(?)}")) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
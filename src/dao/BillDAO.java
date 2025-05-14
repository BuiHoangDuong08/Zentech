package dao;

import entity.Bill;
import helper.ConnectionHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface BillDAO {

    default List<Bill> getAllBills() {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT id, userId, cardId, status FROM BILL";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setUserId(rs.getInt("userId"));
                bill.setCardId(rs.getInt("cardId"));
                bill.setStatus(rs.getString("status"));
                list.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    default boolean addBill(Bill bill) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL insert_bill(?, ?, ?)}")) {

            cs.setInt(1, bill.getUserId());
            cs.setInt(2, bill.getCardId());
            cs.setString(3, bill.getStatus());
            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean updateBill(Bill bill) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL update_bill(?, ?, ?, ?)}")) {

            cs.setInt(1, bill.getId());
            cs.setInt(2, bill.getUserId());
            cs.setInt(3, bill.getCardId());
            cs.setString(4, bill.getStatus());
            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean deleteBill(int id) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL delete_bill(?)}")) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
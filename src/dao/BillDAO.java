package dao;

import entity.BillDetail;
import entity.Bills;
import helper.ConnectionHelper;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    public boolean markBillAsPaid(int billId) {
        String sql = "UPDATE BILL SET Status = 'PAID' WHERE ID = ?";
        try (Connection conn = ConnectionHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, billId);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<BillDetail> getAllBill() {
        List<BillDetail> listb = new ArrayList<>();
        String sql = "SELECT * FROM BILL JOIN BILLDETAILS ON BILL.ID = BILLDETAILS.BILL_ID";
        try (Connection conn = ConnectionHelper.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                BillDetail b = new BillDetail();
                b.getBill().setId(rs.getInt("BILL.ID"));
                b.getBill().setUser_id(rs.getInt("User_ID"));
                b.getBill().setCard_id(rs.getInt("Card_ID"));
                b.getBill().setStatus(rs.getString("Status"));
                b.setStt(rs.getInt("BILLDETAILS.ID"));
                b.setProduct_id(rs.getInt("BILLDETAILS.Product_ID"));
                b.setDate(rs.getDate("BILLDETAILS.Date"));
                b.setQuantity(rs.getInt("BILLDETAILS.Quantity"));
                b.setDiscount(rs.getFloat("BILLDETAILS.Discount"));
                b.setTotalprice_novat(rs.getDouble("BILLDETAILS.TotalPrice_NOVAT"));
                b.setTotalprice_withvat(rs.getDouble("BILLDETAILS.TotalPrice_WITHVAT"));
                listb.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listb;
    }

    public boolean insertFullBill(Bills bill, List<BillDetail> details) {
        String sqlBill = "INSERT INTO BILL(User_ID, Card_ID, Status) VALUES (?, ?, ?)";
        String sqlDetail = "INSERT INTO BILLDETAILS(Bill_ID, Product_ID, Date, Quantity, Discount, TotalPrice_NoVAT, TotalPrice_WithVAT) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionHelper.getConnection()) {
            conn.setAutoCommit(false);

            int billId = -1;
            try (PreparedStatement pst = conn.prepareStatement(sqlBill, Statement.RETURN_GENERATED_KEYS)) {
                pst.setInt(1, bill.getUser_id());
                pst.setInt(2, bill.getCard_id());
                pst.setString(3, bill.getStatus());

                int affected = pst.executeUpdate();
                if (affected == 0) {
                    conn.rollback();
                    return false;
                }

                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        billId = rs.getInt(1);
                    } else {
                        conn.rollback();
                        throw new SQLException("Không lấy được ID hóa đơn.");
                    }
                }
            }

            try (PreparedStatement pst = conn.prepareStatement(sqlDetail)) {
                for (BillDetail bd : details) {
                    pst.setInt(1, billId);
                    pst.setInt(2, bd.getProduct_id());
                    pst.setDate(3, bd.getDate());
                    pst.setInt(4, bd.getQuantity());
                    pst.setFloat(5, bd.getDiscount());
                    pst.setDouble(6, bd.getTotalprice_novat());
                    pst.setDouble(7, bd.getTotalprice_withvat());
                    pst.addBatch();
                }

                int[] results = pst.executeBatch();
                for (int i : results) {
                    if (i == -1) {
                        conn.rollback();
                        return false;
                    }
                }
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa hóa đơn
    public int delete(int id) {
        String sql = "DELETE FROM BILL WHERE ID = ?";
        try (Connection conn = ConnectionHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(int billId, List<BillDetail> details) {
        String sqlDetail = "INSERT INTO BILLDETAILS(Bill_ID, Product_ID, Date, Quantity, Discount, TotalPrice_NoVAT, TotalPrice_WithVAT) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sqlDetail)) {
            for (BillDetail bd : details) {
                pst.setInt(1, billId);
                pst.setInt(2, bd.getProduct_id());
                pst.setDate(3, bd.getDate());
                pst.setInt(4, bd.getQuantity());
                pst.setFloat(5, bd.getDiscount());
                pst.setDouble(6, bd.getTotalprice_novat());
                pst.setDouble(7, bd.getTotalprice_withvat());
                pst.addBatch();
                int[] results = pst.executeBatch();
                for (int i : results) {
                    if (i == -1) {
                        conn.rollback();
                        return 0;
                    }
                }
            }
            return pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

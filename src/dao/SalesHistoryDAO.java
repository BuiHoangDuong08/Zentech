package dao;

import entity.SalesHistorymodel;
import helper.ConnectionHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface SalesHistoryDAO {

    default List<SalesHistorymodel> getAllSalesHistory() {
        List<SalesHistorymodel> list = new ArrayList<>();
        String sql
                = "SELECT "
                + "  sh.Bill_ID, "
                + "  sh.Date, "
                + "  sh.Username, "
                + "  GROUP_CONCAT(CONCAT(p.Name, ' (x', bd.Quantity, ')') SEPARATOR ', ') AS Items, "
                + "  SUM(bd.Quantity) AS TotalQuantity, "
                + "  SUM(bd.TotalPrice_WithVAT) AS TotalAmount, "
                + "  sh.Status "
                + "FROM SALESHISTORY sh "
                + "JOIN BILLDETAILS bd ON sh.Bill_ID = bd.Bill_ID "
                + "JOIN PRODUCT p ON bd.Product_ID = p.ID "
                + "GROUP BY sh.Bill_ID, sh.Date, sh.Username, sh.Status "
                + "ORDER BY sh.Bill_ID";
        try (Connection con = ConnectionHelper.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SalesHistorymodel sh = new SalesHistorymodel();
                sh.setBillId(rs.getInt("Bill_ID"));
                sh.setDate(rs.getDate("Date"));
                sh.setUsername(rs.getString("Username"));
                sh.setItems(rs.getString("Items"));
                sh.setTotalQuantity(rs.getInt("TotalQuantity"));
                sh.setTotalAmount(rs.getDouble("TotalAmount"));
                sh.setStatus(rs.getString("Status"));
                list.add(sh);
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }

        return list;
    }

    default List<SalesHistorymodel> searchByBillId(int billId) {
        List<SalesHistorymodel> list = new ArrayList<>();

        String sql = "SELECT "
                + "sh.Bill_ID, "
                + "sh.Date, "
                + "sh.Username, "
                + "COUNT(DISTINCT d.Product_ID) AS Items, "
                + "SUM(d.Quantity) AS TotalQuantity, "
                + "sh.TotalAmount, "
                + "sh.Status "
                + "FROM SALESHISTORY sh "
                + "JOIN BILLDETAILS d ON sh.Bill_ID = d.Bill_ID "
                + "WHERE sh.Bill_ID = ? "
                + "GROUP BY sh.Bill_ID, sh.Date, sh.Username, sh.TotalAmount, sh.Status";

        try (Connection conn = ConnectionHelper.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SalesHistorymodel model = new SalesHistorymodel();
                model.setBillId(rs.getInt("Bill_ID"));
                model.setDate(rs.getDate("Date"));
                model.setUsername(rs.getString("Username"));
                model.setItems(rs.getString("Items"));
                model.setTotalQuantity(rs.getInt("TotalQuantity"));
                model.setTotalAmount(rs.getDouble("TotalAmount"));
                model.setStatus(rs.getString("Status"));
                list.add(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}

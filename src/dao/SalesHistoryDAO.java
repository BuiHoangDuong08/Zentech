package dao;

import entity.SalesHistorymodel;
import helper.ConnectionHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SalesHistoryDAO {

    default List<SalesHistorymodel> getAllSalesHistory() {
        List<SalesHistorymodel> list = new ArrayList<>();

        String sql
                = "SELECT \n"
                + "    sh.ID,\n"
                + "    sh.Bill_ID,\n"
                + "    sh.Date,\n"
                + "    sh.Username,\n"
                + "    COUNT(DISTINCT d.Product_ID) AS Items,\n"
                + "    SUM(d.Quantity) AS TotalQuantity,\n"
                + "    sh.TotalAmount,\n"
                + "    sh.Status\n"
                + "FROM SALESHISTORY sh\n"
                + "JOIN BILLDETAILS d ON sh.Bill_ID = d.Bill_ID\n"
                + "GROUP BY\n"
                + "    sh.ID,\n"
                + "    sh.Bill_ID,\n"
                + "    sh.Date,\n"
                + "    sh.Username,\n"
                + "    sh.TotalAmount,\n"
                + "    sh.Status";

        try (Connection con = ConnectionHelper.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SalesHistorymodel sh = new SalesHistorymodel();
                sh.setId(rs.getInt("ID"));
                sh.setBillId(rs.getInt("Bill_ID"));
                sh.setDate(rs.getDate("Date"));
                sh.setUsername(rs.getString("Username"));
                sh.setItems(rs.getInt("Items"));
                sh.setTotalQuantity(rs.getInt("TotalQuantity"));
                sh.setTotalAmount(rs.getDouble("TotalAmount"));
                sh.setStatus(rs.getString("Status"));
                list.add(sh);
            }
        } catch (Exception e) {
            System.out.println("loi: " + e.getMessage());
        }

        return list;
    }

    default boolean deleteById(int id) {
        String sql = "DELETE FROM SALESHISTORY WHERE ID = ?";
        try (Connection conn = ConnectionHelper.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    default List<SalesHistorymodel> searchByBillId(int billId) {
        List<SalesHistorymodel> list = new ArrayList<>();

        String sql = "SELECT "
                + "sh.ID, "
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
                + "GROUP BY sh.ID, sh.Bill_ID, sh.Date, sh.Username, sh.TotalAmount, sh.Status";

        try (Connection conn = ConnectionHelper.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SalesHistorymodel model = new SalesHistorymodel();
                model.setId(rs.getInt("ID"));
                model.setBillId(rs.getInt("Bill_ID"));
                model.setDate(rs.getDate("Date"));
                model.setUsername(rs.getString("Username"));
                model.setItems(rs.getInt("Items"));
                model.setTotalQuantity(rs.getInt("TotalQuantity"));
                model.setTotalAmount(rs.getDouble("TotalAmount"));
                model.setStatus(rs.getString("Status"));
                list.add(model);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}

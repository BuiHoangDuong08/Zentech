package dao;

import entity.Card;
import helper.ConnectionHelper;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface CardDAO {

    default List<Card> getAllCards() {
        List<Card> list = new ArrayList<>();
        String sql = "SELECT id, status FROM CARD";

        try (Connection con = ConnectionHelper.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Card card = new Card();
                card.setId(rs.getInt("id"));
                card.setStatus(rs.getString("status"));
                list.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    default boolean addCard(String status) {
        try (Connection con = ConnectionHelper.getConnection(); CallableStatement cs = con.prepareCall("{CALL insert_card(?)}")) {

            cs.setString(1, status);
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean updateCard(int id, String status) {
        try (Connection con = ConnectionHelper.getConnection(); CallableStatement cs = con.prepareCall("{CALL update_card(?, ?)}")) {

            cs.setInt(1, id);
            cs.setString(2, status);
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean deleteCard(int id) {
        try (Connection con = ConnectionHelper.getConnection(); CallableStatement cs = con.prepareCall("{CALL delete_card(?)}")) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean lockCard(String cardId) {
        String sql = "UPDATE CARD SET Status = 'LOCKED' WHERE ID = ?";
        try (Connection con = ConnectionHelper.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cardId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean unlockCard(String cardId) {
        String sql = "UPDATE CARD SET Status = 'ACTIVE' WHERE ID = ?";
        try (Connection con = ConnectionHelper.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cardId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default String getCardStatus(String cardId) {
        String status = null;
        String sql = "SELECT Status FROM CARD WHERE ID = ?";
        try (Connection con = ConnectionHelper.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cardId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                status = rs.getString("Status");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}

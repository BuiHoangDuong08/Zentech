package dao;

import entity.Card;
import helper.ConnectionHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface CardDAO {

    default List<Card> getAllCards() {
        List<Card> list = new ArrayList<>();
        String sql = "SELECT id, status FROM CARD";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL insert_card(?)}")) {
            cs.setString(1, status);
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean updateCard(int id, String status) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL update_card(?, ?)}")) {

            cs.setInt(1, id);
            cs.setString(2, status);
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    default boolean deleteCard(int id) {
        try (Connection con = ConnectionHelper.getConnection();
             CallableStatement cs = con.prepareCall("{CALL delete_card(?)}")) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
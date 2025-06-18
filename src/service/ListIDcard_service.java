package service;

import com.formdev.flatlaf.FlatClientProperties;

import dao.ActivityDAO;
import dao.CardDAO;
import entity.Card;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import raven.toast.Notifications;
import zentech.application.Application;

public class ListIDcard_service {

    static CardDAO card = new CardDAO() {
    };
    static List<Card> listc = new ArrayList<>();

    public void LoadDataTable(List<Card> listc, JTable jTable1) {
        listc = card.getAllCards();
        String[] title = {"ID", "Status"};
        DefaultTableModel model = new DefaultTableModel(title, 0);
        for (Card c : listc) {
            model.addRow(new Object[]{c.getId(), c.getStatus()});
        }
        jTable1.setModel(model);
    }

    public void exportToExcel(JTable table) {
        try (FileWriter writer = new FileWriter("IDCards.csv")) {
            // Ghi dòng tiêu đề
            for (int i = 0; i < table.getColumnCount(); i++) {
                writer.write(table.getColumnName(i));
                if (i < table.getColumnCount() - 1) {
                    writer.write(",");
                }
            }
            writer.write("\n");

            // Ghi dữ liệu
            for (int row = 0; row < table.getRowCount(); row++) {
                for (int col = 0; col < table.getColumnCount(); col++) {
                    Object value = table.getValueAt(row, col);
                    writer.write(value != null ? value.toString() : "");
                    if (col < table.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.write("\n");
            }

            JOptionPane.showMessageDialog(null, "Xuất Excel (.csv) thành công!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất Excel: " + e.getMessage());
        }
    }

    public void exportToWord(JTable table) {
        try (FileWriter writer = new FileWriter("IDCards.doc")) {
            writer.write("Danh sách thẻ ID\n\n");

            for (int row = 0; row < table.getRowCount(); row++) {
                for (int col = 0; col < table.getColumnCount(); col++) {
                    String columnName = table.getColumnName(col);
                    Object value = table.getValueAt(row, col);
                    writer.write(columnName + ": " + (value != null ? value.toString() : "") + "\n");
                }
                writer.write("\n-----------------------------\n");
            }

            JOptionPane.showMessageDialog(null, "Xuất Word (.doc) thành công!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất Word: " + e.getMessage());
        }
    }

    public void add(JComboBox cbo_Status) {
        int ret = JOptionPane.showConfirmDialog(null, "Are you sure", "Add", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            String status = cbo_Status.getSelectedItem().toString();
            boolean rs = card.addCard(status);
            if (rs == true) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Saved successfully");
                JOptionPane.showMessageDialog(null, "");
                ActivityDAO.logActivity(Application.getAppInstance().getCurrentUser(), "ADD IDCARD " + status);
            }
        }
    }

    public void showDetail(JTable jTable1, JTextField txt_ID, JComboBox cbo_Status) {
        int index = jTable1.getSelectedRow();
        int id = (int) jTable1.getValueAt(index, 0);
        String status = (String) jTable1.getValueAt(index, 1);
        txt_ID.setText(String.valueOf(id));
        cbo_Status.setSelectedItem(status);
    }

    public void delete(JTextField txt_ID) {
        int ret = JOptionPane.showConfirmDialog(null, "Are you sure", "Add", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txt_ID.getText());
            boolean rs = card.deleteCard(id);
            if (rs == true) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Deleted successfully");
                // Log activity
                ActivityDAO.logActivity(Application.getAppInstance().getCurrentUser(), "DELETE IDCARD " + id);
            }
        }
    }

    public void editinitComponents(JTextField txt_Search, JTextField txt_ID) {
        txt_Search.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search");
        txt_ID.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ID");
        txt_ID.setEditable(false);

    }

    public void Update(JComboBox cbo_Status, JTextField txt_ID) {
        int ret = JOptionPane.showConfirmDialog(null, "Are you sure", "Add", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txt_ID.getText());
            String status = cbo_Status.getSelectedItem().toString();
            boolean rs = card.updateCard(id, status);
            if (rs == true) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Updated successfully");
                // Log activity
                ActivityDAO.logActivity(Application.getAppInstance().getCurrentUser(), "UPDATE IDCARD " + id + " " + status);
            }
        }
    }

    public void Find(JTable jTable1, JTextField txt_Search) {
        DefaultTableModel ob = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        jTable1.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(txt_Search.getText()));
    }
}

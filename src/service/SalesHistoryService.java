package service;

import entity.SalesHistorymodel;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dao.SalesHistoryDAO;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.TableRowSorter;
import static jdk.javadoc.internal.doclets.toolkit.util.DocPath.parent;

public class SalesHistoryService {

    private SalesHistoryDAO shDao = new SalesHistoryDAO();

    public void loadSalesHistoryToTable(JTable table, List<SalesHistorymodel> list) {
        list = shDao.getAllSalesHistory();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (SalesHistorymodel sh : list) {
            model.addRow(new Object[]{
                sh.getId(),
                sh.getBillId(),
                sh.getDate(),
                sh.getUsername(),
                sh.getItems(),
                sh.getTotalQuantity(),
                sh.getTotalAmount(),
                sh.getStatus()
            });
        }
    }

    public void deleteSelectedRow(JTable table) {
        int selectedRowView = table.getSelectedRow();

        if (selectedRowView == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this line??", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int selectedRowModel = table.convertRowIndexToModel(selectedRowView);
        int id = (int) table.getModel().getValueAt(selectedRowModel, 0);

        SalesHistoryDAO dao = new SalesHistoryDAO();
        boolean success = dao.deleteById(id);

        if (success) {
            ((DefaultTableModel) table.getModel()).removeRow(selectedRowModel);
            JOptionPane.showMessageDialog(null, "Deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Delete failed!");
        }
    }

    public void searchByBillId(String billIdText, JTable table, JTextField timKiem) {
    DefaultTableModel ob = (DefaultTableModel) table.getModel();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(ob);
    table.setRowSorter(sorter);

    sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
        @Override
        public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
            String keyword = timKiem.getText().trim().toLowerCase();
            for (int i = 0; i < entry.getValueCount(); i++) {
                Object cell = entry.getValue(i);
                String cellText = String.valueOf(cell).toLowerCase();
                if (cellText.contains(keyword)) {
                    return true;
                }
            }
            return false;
        }
    });
    }

public void Find(JTable table, JTextField search) {
        DefaultTableModel ob = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        table.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(search.getText()));
    }

    public void showDetail(JTable table, JTextField fieldID, JTextField fieldStatus) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Lấy dữ liệu từ jTable2
            int id = (int) table.getValueAt(selectedRow, 0);
            String status = table.getValueAt(selectedRow, 7).toString(); // Cột 7 là "Status"

            // Set vào các ô text
            fieldID.setText(String.valueOf(id));
            fieldStatus.setText(status);
        }
    }
}

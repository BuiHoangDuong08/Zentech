package service;

import entity.SalesHistorymodel;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dao.SalesHistoryDAO;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import dao.ActivityDAO;
import zentech.application.Application;

public class SalesHistoryService implements SalesHistoryDAO{

    public void loadSalesHistoryToTable(JTable table, List<SalesHistorymodel> list) {
        list = getAllSalesHistory();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (SalesHistorymodel sh : list) {
            model.addRow(new Object[]{
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
                        // Log activity
                        ActivityDAO.logActivity(Application.getAppInstance().getCurrentUser(), "SEARCH SALESHISTORY " + keyword);
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
        // Lấy dữ liệu từ dòng được chọn
        String billId = table.getValueAt(selectedRow, 0).toString(); // Cột 0 là Bill_ID
        String status = table.getValueAt(selectedRow, 6).toString(); // Cột 6 là Status

        // Gán vào ô text
        fieldID.setText(billId);
        fieldStatus.setText(status);
        // Log activity
        ActivityDAO.logActivity(Application.getAppInstance().getCurrentUser(), "VIEW SALESHISTORY DETAIL " + billId);
    }
}
}
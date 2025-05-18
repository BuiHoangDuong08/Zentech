package controller;

import entity.SalesHistorymodel;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dao.SalesHistoryDAO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SalesHistoryController {
    private SalesHistoryDAO shDao = new SalesHistoryDAO();

    public void loadSalesHistoryToTable(JTable table) {
        List<SalesHistorymodel> list = shDao.getAllSalesHistory();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // clear table

        for (SalesHistorymodel sh : list) {
            model.addRow(new Object[] {
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
    
 private JTable jTable2;


    public SalesHistoryController(JTable jTable2) {
        this.jTable2 = jTable2;
    }

    public void deleteSelectedRow() {
        int selectedRowView = jTable2.getSelectedRow();

        if (selectedRowView == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa dòng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int selectedRowModel = jTable2.convertRowIndexToModel(selectedRowView);
        int id = (int) jTable2.getModel().getValueAt(selectedRowModel, 0);

        SalesHistoryDAO dao = new SalesHistoryDAO();
        boolean success = dao.deleteById(id);

        if (success) {
            ((DefaultTableModel) jTable2.getModel()).removeRow(selectedRowModel);
            JOptionPane.showMessageDialog(null, "Xóa thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Xóa thất bại!");
        }
    }
    
   public void searchByBillId(String billIdText, JTable table) {
        try {
            int billId = Integer.parseInt(billIdText); 
            List<SalesHistorymodel> list = shDao.searchByBillId(billId);

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); 

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
        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Mã hóa đơn không hợp lệ!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

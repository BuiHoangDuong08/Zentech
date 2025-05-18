/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package zentech.application.form.other;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import zentechx.menu.EventCellInputChange;
import zentechx.menu.ModelItemSell;
import zentechx.menu.QtyCellEditor;
import entity.Product;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import service.ProductService;

/**
 *
 * @author RGB
 */
public class MenuSelection extends javax.swing.JPanel {

    /**
     * Creates new form MenuSelection
     */
    ProductService productService = new ProductService();

    public MenuSelection() {
        initComponents();
        customTableEvent();
        showData();
    }

    private void customTableEvent() {
        tblProduct.getColumnModel().getColumn(3).setCellEditor(new QtyCellEditor(new EventCellInputChange() {
            @Override
            public void inputChanged() {
                syncSelectedItemsAndSum();
            }
        }));
        tblProduct.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });

        // Ẩn cột chứa object ModelItemSell
        tblProduct.getColumnModel().getColumn(0).setMinWidth(0);
        tblProduct.getColumnModel().getColumn(0).setPreferredWidth(0);
        tblProduct.getColumnModel().getColumn(0).setMaxWidth(0);

        // Ẩn cột chứa image ModelItemSell
        tblProduct.getColumnModel().getColumn(6).setMinWidth(0);
        tblProduct.getColumnModel().getColumn(6).setPreferredWidth(0);
        tblProduct.getColumnModel().getColumn(6).setMaxWidth(0);
    }

    private void syncSelectedItemsAndSum() {
        DefaultTableModel modelSelected = (DefaultTableModel) tblGetInfo.getModel();
        modelSelected.setRowCount(0); // Clear table
        int total = 0;

        DecimalFormat df = new DecimalFormat("$ #,###");

        for (int i = 0; i < tblProduct.getRowCount(); i++) {
            ModelItemSell item = (ModelItemSell) tblProduct.getValueAt(i, 0);
            if (item.getQty() > 0) {
                modelSelected.addRow(new Object[]{
                    item.getProductName(),
                    item.getQty(),
                    df.format(item.getPrice()) // định dạng lại giá
                });
                total += item.getTotal();
            }
        }

        lbTotal.setText(df.format(total));
    }

    private void showData() {
        List<Product> productList = productService.getAllData();
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (Product p : productList) {
            ImageIcon image = createImageIcon(p.getImageUrl());

            // Thêm ảnh vào cột cuối cùng
            ModelItemSell item = new ModelItemSell(p.getId(), p.getName(), 0, p.getPrice(), 0);
            model.addRow(new Object[]{
                item, // Object ModelItemSell (ẩn)
                p.getId(), // ID
                p.getName(), // Tên
                0, // Qty
                p.getPrice(), // Giá
                0, // Tổng
                image // Ảnh
            });
        }

        tblProduct.setRowHeight(60); // Đảm bảo đủ chỗ hiển thị ảnh
    }

    private ImageIcon createImageIcon(String path) {
        try {
            ImageIcon icon = new ImageIcon(path); // path có thể là đường dẫn ảnh hoặc URL
            Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void filterProductByName(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        List<Product> productList = productService.getAllData();

        for (Product p : productList) {
            if (p.getName().toLowerCase().contains(keyword)) {
                ImageIcon image = createImageIcon(p.getImageUrl());

                ModelItemSell item = new ModelItemSell(p.getId(), p.getName(), 0, p.getPrice(), 0);
                model.addRow(new Object[]{
                    item, // Ẩn
                    p.getId(), // ID
                    p.getName(), // Name
                    0, // Qty
                    p.getPrice(), // Price
                    0, // Total
                    image // Ẩn
                });
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGetInfo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        btnPay = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnReceipt = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Menu Selection");

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Object", "ID", "Name", "Qty", "Price", "Total", "Image"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProduct);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblGetInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Quantity", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblGetInfo);

        jLabel1.setText("Total: ");

        lbTotal.setText("$ 0.00");

        btnPay.setText("Pay");

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnReceipt.setText("Receipt");
        btnReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReceiptActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        lblImage.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.darkGray));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
                        .addComponent(btnReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 140, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbTotal))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel3.setText("Search");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)))
                        .addContainerGap(635, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(15, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReceiptActionPerformed
        // TODO add your handling code here:
        try {
            String userHome = System.getProperty("user.home");
            String desktopPath = userHome + File.separator + "Desktop";
            String filePath = desktopPath + File.separator + "zentech_receipt.pdf";

            Document document = new Document(PageSize.A6);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font smallFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

            // Tiêu đề
            Paragraph title = new Paragraph("ZENTECH\n\n", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Thông tin nhân viên & khách hàng
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{1f, 1f});
            infoTable.addCell(getCell("Staff", PdfPCell.ALIGN_LEFT, smallFont));
            infoTable.addCell(getCell("Admin", PdfPCell.ALIGN_RIGHT, smallFont));
            infoTable.addCell(getCell("Customer", PdfPCell.ALIGN_LEFT, smallFont));
            infoTable.addCell(getCell("MR A", PdfPCell.ALIGN_RIGHT, smallFont));
            document.add(infoTable);

            document.add(new Paragraph("-------------------------------------------------------------------", smallFont));

            // Bảng sản phẩm
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2f, 1f, 1f, 1f});
            table.addCell(new Phrase("Name", boldFont));
            table.addCell(new Phrase("Qty", boldFont));
            table.addCell(new Phrase("Price", boldFont));
            table.addCell(new Phrase("Total", boldFont));

            DefaultTableModel model = (DefaultTableModel) tblGetInfo.getModel();
            double grandTotal = 0;

            for (int i = 0; i < model.getRowCount(); i++) {
                String name = model.getValueAt(i, 0).toString();
                int qty = Integer.parseInt(model.getValueAt(i, 1).toString());
                String priceStr = model.getValueAt(i, 2).toString().replace("$", "").replace(",", "").trim();
                double price = Double.parseDouble(priceStr);
                double itemTotal = price * qty;
                grandTotal += itemTotal;

                table.addCell(new Phrase(name, smallFont));
                table.addCell(new Phrase(String.valueOf(qty), smallFont));
                table.addCell(new Phrase(String.format("$ %.2f", price), smallFont));
                table.addCell(new Phrase(String.format("$ %.2f", itemTotal), smallFont));
            }

            document.add(table);
            document.add(new Paragraph("-------------------------------------------------------------------", smallFont));

            // Tổng tiền
            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(100);
            totalTable.setWidths(new float[]{3f, 1f});
            totalTable.addCell(getCell("Total", PdfPCell.ALIGN_LEFT, boldFont));
            totalTable.addCell(getCell(String.format("$ %.2f", grandTotal), PdfPCell.ALIGN_RIGHT, boldFont));
            document.add(totalTable);

            document.close();
            JOptionPane.showMessageDialog(this, "Đã lưu hóa đơn tại Desktop (zentech_receipt.pdf)");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi xuất PDF: " + e.getMessage());
        }
    }

// Hàm phụ để tạo ô căn trái/phải
    private PdfPCell getCell(String text, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }//GEN-LAST:event_btnReceiptActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        String keyword = txtSearch.getText().trim().toLowerCase();
        filterProductByName(keyword);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void tblProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblProduct.getSelectedRow();
        if (selectedRow != -1) {
            ImageIcon icon = (ImageIcon) tblProduct.getValueAt(selectedRow, 6); // Cột 6 là image (ẩn)
            Image image = icon.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(image));
        }
    }//GEN-LAST:event_tblProductMouseClicked

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // TODO add your handling code here:
        // Dừng chỉnh sửa nếu đang sửa ô nào đó
        if (tblProduct.isEditing()) {
            tblProduct.getCellEditor().stopCellEditing();
        }

        // Xóa toàn bộ dữ liệu trong bảng tblGetInfo
        DefaultTableModel model = (DefaultTableModel) tblGetInfo.getModel();
        model.setRowCount(0); // Xóa toàn bộ dòng

        // Đồng thời reset tất cả qty trong bảng tblProduct về 0
        for (int i = 0; i < tblProduct.getRowCount(); i++) {
            ModelItemSell item = (ModelItemSell) tblProduct.getValueAt(i, 0);
            item.setQty(0);
            item.setTotal(0);
            tblProduct.setValueAt(0, i, 3); // Cập nhật lại Qty cột 3
            tblProduct.setValueAt("$ 0", i, 5); // Cập nhật lại Total cột 5
        }

        // Reset tổng tiền về 0
        lbTotal.setText("$ 0");
    }//GEN-LAST:event_btnRemoveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPay;
    private javax.swing.JButton btnReceipt;
    private javax.swing.JButton btnRemove;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JLabel lblImage;
    private javax.swing.JTable tblGetInfo;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}

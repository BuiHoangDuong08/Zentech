package zentech.application.form.other;

import entity.Card;
import java.awt.Component;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import raven.toast.Notifications;
import service.MenuSelectionService;
import zentechx.menu.QtyCellEditor;

public class MenuSelection extends javax.swing.JPanel {

    MenuSelectionService menuSelectionService = new MenuSelectionService();
    static List<Card> listc = new ArrayList<>();

    public MenuSelection() {
        initComponents();
        customTableEvent();
        showProductsData();
        showCardsData();

        tblProduct.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        tblProduct.setRowHeight(30);
        tblProduct.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 18));

        tblGetInfo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        tblGetInfo.setRowHeight(30);
        tblGetInfo.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 18));

    }

    private void showProductsData() {
        menuSelectionService.loadProductsToTable(tblProduct);
    }

    private void showCardsData() {
        menuSelectionService.loadCardstoTable(listc, tblIDCard);
    }

    private void customTableEvent() {
        // Gán QtyCellEditor như cũ
        tblProduct.getColumnModel().getColumn(3).setCellEditor(new QtyCellEditor(() -> syncSelectedItemsAndTotal()));
        tblProduct.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });

        // Renderer định dạng giá kiểu tiền tệ
        tblProduct.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            private final NumberFormat currencyFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                if (value instanceof Number) {
                    setText(currencyFormat.format(((Number) value).doubleValue()) + " đ");
                } else {
                    setText(String.valueOf(value));
                }
                return this;
            }
        });

        tblProduct.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });

        // Ẩn cột 0 và 6
        for (int i : new int[]{0, 6}) {
            tblProduct.getColumnModel().getColumn(i).setMinWidth(0);
            tblProduct.getColumnModel().getColumn(i).setPreferredWidth(0);
            tblProduct.getColumnModel().getColumn(i).setMaxWidth(0);
        }
    }

    private void syncSelectedItemsAndTotal() {
        menuSelectionService.syncSelectedItemsAndTotal(tblProduct, tblGetInfo, lbTotal);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jScrollPane3 = new javax.swing.JScrollPane();
        tblIDCard = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmoArrange = new javax.swing.JComboBox<>();

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
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
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

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

        tblIDCard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblIDCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblIDCardMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblIDCard);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(143, 143, 143)
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbTotal))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(111, 111, 111)
                                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(btnPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReceipt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lbTotal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108)))
                .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Search");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        jLabel9.setText("MENU SELECTION");

        cmoArrange.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mặc định", "Cà phê", "Trà", "Nước trái cây", "Sinh tố", "Đồ ăn vặt", "Bánh ngọt", "Đồ ăn sáng", "Đồ ăn tối" }));
        cmoArrange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmoArrangeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmoArrange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(128, 128, 128)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9)
                    .addComponent(cmoArrange, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReceiptActionPerformed
        menuSelectionService.generateReceipt(tblGetInfo, this, lbTotal);
    }//GEN-LAST:event_btnReceiptActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        menuSelectionService.filterProducts(txtSearch, tblProduct);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        menuSelectionService.resetAll(tblProduct, tblGetInfo, lbTotal);
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void tblProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblProduct.getSelectedRow();
        if (selectedRow != -1) {
            // Giả sử cột ảnh nằm ở cột số 6 (index bắt đầu từ 0)
            Object imageObj = tblProduct.getValueAt(selectedRow, 6);

            if (imageObj instanceof ImageIcon) {
                lblImage.setIcon((ImageIcon) imageObj);
            } else if (imageObj instanceof String) {
                // Nếu là đường dẫn chuỗi thì gọi lại hàm createImageIcon
                ImageIcon icon = menuSelectionService.createImageIcon(TOOL_TIP_TEXT_KEY);
                lblImage.setIcon(icon);
            }
        }
    }//GEN-LAST:event_tblProductMouseClicked

    private void cmoArrangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmoArrangeActionPerformed
        // TODO add your handling code here:
        // Lưu lại qty trước khi lọc
        menuSelectionService.preserveQtyBeforeFilter(tblProduct);
        
        // Map tên loại sang categoryId
        Map<String, Integer> categoryMap = Map.of(
                "Mặc định", 0,
                "Cà phê", 1,
                "Trà", 2,
                "Nước trái cây", 3,
                "Sinh tố", 4,
                "Đồ ăn vặt", 5,
                "Bánh ngọt", 6,
                "Đồ ăn sáng", 7,
                "Đồ ăn tối", 8
        );

        String selectedCategory = (String) cmoArrange.getSelectedItem();
        int categoryId = categoryMap.getOrDefault(selectedCategory, 0);
        
        menuSelectionService.filterProductsByCategory(categoryId, tblProduct);
        
        // Đồng bộ lại bảng info và tổng tiền
        syncSelectedItemsAndTotal();
        
        if (tblProduct.isEditing()) {
            tblProduct.getCellEditor().stopCellEditing();
        }
        
        tblProduct.clearSelection();
        lblImage.setIcon(null);
    }//GEN-LAST:event_cmoArrangeActionPerformed

    private String selectedCardId = null;

    private void tblIDCardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblIDCardMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblIDCard.getSelectedRow();
        System.out.println("Số dòng trong tblGetInfo: " + tblGetInfo.getRowCount());
        if (selectedRow != -1) {
            String cardId = tblIDCard.getValueAt(selectedRow, 0).toString(); // Giả sử ID nằm ở cột 0

            // Kiểm tra trạng thái thẻ trước
            String status = menuSelectionService.getCardStatus(cardId);
            if ("LOCKED".equalsIgnoreCase(status)) {
                JOptionPane.showMessageDialog(MenuSelection.this, "Thẻ đã khóa, không thể chọn.");
                return; // thoát hàm, không tiếp tục
            }

            int confirm = JOptionPane.showConfirmDialog(
                    MenuSelection.this,
                    "Bạn có muốn chọn thẻ này không?",
                    "Xác nhận chọn thẻ",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean result = menuSelectionService.selectCard(cardId, tblGetInfo);
                if (result) {
                    JOptionPane.showMessageDialog(MenuSelection.this, "Thẻ đã được chuyển sang trạng thái LOCKED.");
                    selectedCardId = cardId;
                    showCardsData();
                    menuSelectionService.resetAll(tblProduct, tblGetInfo, lbTotal);
                } else {
                    JOptionPane.showMessageDialog(MenuSelection.this, "Không thể chọn thẻ. Phải có đơn hàng trước.");
                }
            }
        }
    }//GEN-LAST:event_tblIDCardMouseClicked

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        // TODO add your handling code here:
        if (selectedCardId == null) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn thẻ nào.");
            return;
        }

        boolean result = menuSelectionService.releaseCard(selectedCardId);
        if (result) {
            JOptionPane.showMessageDialog(this, "Thanh toán thành công. Trạng thái thẻ đã được đặt lại TRỐNG.");
            selectedCardId = null; // Reset
            showCardsData();
        } else {
            JOptionPane.showMessageDialog(this, "Không thể cập nhật trạng thái thẻ.");
        }
    }//GEN-LAST:event_btnPayActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPay;
    private javax.swing.JButton btnReceipt;
    private javax.swing.JButton btnRemove;
    private javax.swing.JComboBox<String> cmoArrange;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JLabel lblImage;
    private javax.swing.JTable tblGetInfo;
    private javax.swing.JTable tblIDCard;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}

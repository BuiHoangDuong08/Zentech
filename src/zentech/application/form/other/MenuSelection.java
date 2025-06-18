package zentech.application.form.other;

import dao.BillDAO;
import dao.CardDAO;
import dao.SalesHistoryDAO;
import dao.ActivityDAO;
import entity.BillDetail;
import entity.Bills;
import entity.Card;
import entity.UserModel;
import java.awt.Component;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import service.MenuSelectionService;
import zentech.application.Application;
import zentechx.menu.QtyCellEditor;

public class MenuSelection extends javax.swing.JPanel {

    MenuSelectionService menuSelectionService = new MenuSelectionService();
    static List<Card> listc = new ArrayList<>();
    private UserModel usm;
    private BillDAO bd = new BillDAO();
    CardDAO cd = new CardDAO() {
    };
    private String selectedCardId = null;

    public MenuSelection(UserModel usm) {
        this.usm = usm;
        initComponents();
        customTableEvent();
        showProductsData();
        LoadBill();
        LoadDataCbo();
        tblProduct.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        tblProduct.setRowHeight(30);
        tblProduct.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 18));
        tblGetInfo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        tblGetInfo.setRowHeight(30);
        tblGetInfo.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 18));

    }

    public void LoadBill() {
        String[] title = {"Bill id", "Card id", "Stt", "Product id", "Quantity", "Date", "Status", "Totalprice withvat"};
        DefaultTableModel model = new DefaultTableModel(title, 0);
        model.setRowCount(0);
        for (BillDetail bd : bd.getAllBill()) {
            if (bd.getBill().getStatus().equalsIgnoreCase("unpaid")) {
                model.addRow(
                        new Object[]{
                            bd.getBill().getId(),
                            bd.getBill().getCard_id(),
                            bd.getStt(),
                            bd.getProduct_id(),
                            bd.getQuantity(),
                            bd.getDate(),
                            bd.getBill().getStatus(),
                            bd.getTotalprice_withvat(),});
            }
        }
        jTable1.setModel(model);
    }

    private void showProductsData() {
        menuSelectionService.loadProductsToTable(tblProduct);
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

        for (int i : new int[]{0, 6}) {
            tblProduct.getColumnModel().getColumn(i).setMinWidth(0);
            tblProduct.getColumnModel().getColumn(i).setPreferredWidth(0);
            tblProduct.getColumnModel().getColumn(i).setMaxWidth(0);
        }
    }

    private void syncSelectedItemsAndTotal() {
        menuSelectionService.syncSelectedItemsAndTotal(tblProduct, tblGetInfo, lbTotal);
    }

    public void LoadDataCbo() {
        jComboBox1.removeAllItems();

        List<Card> cards = cd.getAllCards();
        for (Card c : cards) {
            if ("ACTIVE".equalsIgnoreCase(c.getStatus())) {
                jComboBox1.addItem(String.valueOf(c.getId()));
            }
        }
    }

    public Bills getFrom() {
        int index = tblGetInfo.getSelectedRow();
        int id = Integer.parseInt(jComboBox1.getSelectedItem().toString());
        Bills b = new Bills();
        b.setCard_id(id);
        b.setUser_id(usm.getId());
        b.setStatus("UNPAID");
        return b;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtSearch = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGetInfo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        btnPay = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnReceipt = new javax.swing.JButton();
        Save = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmoArrange = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Oder"));

        tblGetInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Quantity", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGetInfo.getTableHeader().setReorderingAllowed(false);
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

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Refesh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReceipt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(lbTotal)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReceipt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Product"));

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
        tblProduct.getTableHeader().setReorderingAllowed(false);
        tblProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblProductMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tblProduct);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("BIll"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Bill id", "Card id", "Stt", "Product id", "Quantity", "Date", "Status", "Price"
            }
        ));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmoArrange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmoArrange, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReceiptActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        int billId = (int) jTable1.getValueAt(selectedRow, 0);
        menuSelectionService.generateReceiptFromPaidBill(billId, this);
        // Log activity
        ActivityDAO.logActivity(usm.getUserName(), "PRINT RECEIPT BILL " + billId);
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

    }//GEN-LAST:event_tblProductMouseClicked

    private void cmoArrangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmoArrangeActionPerformed
        // TODO add your handling code here:
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
    }//GEN-LAST:event_cmoArrangeActionPerformed


    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần thanh toán.");
            return;
        }

        int billId = (int) jTable1.getValueAt(selectedRow, 0);
        String cardId = String.valueOf(jTable1.getValueAt(selectedRow, 1));

        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận thanh toán hóa đơn #" + billId + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean paidSuccess = bd.markBillAsPaid(billId);

        boolean cardUnlocked = cd.unlockCard(cardId);

        if (paidSuccess && cardUnlocked) {
            JOptionPane.showMessageDialog(this, "Thanh toán thành công! Thẻ #" + cardId + " đã được mở khóa.");

            List<BillDetail> billDetails = new ArrayList<>();
            for (BillDetail b : bd.getAllBill()) {
                if (b.getBill().getId() == billId) {
                    billDetails.add(b);
                }
            }

            double totalAmount = 0;
            int totalQuantity = 0;
            java.sql.Date date = null;

            for (BillDetail b : billDetails) {
                totalAmount += b.getTotalprice_withvat();
                totalQuantity += b.getQuantity();
                date = b.getDate();
            }

            SalesHistoryDAO shDAO = new SalesHistoryDAO() {
            };
            boolean inserted = shDAO.insertToSalesHistory(
                    billId,
                    usm.getFullName(),
                    totalAmount,
                    "PAID",
                    date != null ? date : new java.sql.Date(System.currentTimeMillis()),
                    totalQuantity
            );

            if (!inserted) {
                JOptionPane.showMessageDialog(this, "Lưu lịch sử bán hàng thất bại.");
            }

            menuSelectionService.generateReceiptFromPaidBill(billId, this);
            // Log activity
            ActivityDAO.logActivity(usm.getUserName(), "PAY BILL " + billId);
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra trong quá trình thanh toán.");
        }

        LoadBill();
        LoadDataCbo();
    }//GEN-LAST:event_btnPayActionPerformed

    private void tblProductMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProductMouseEntered


    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        // TODO add your handling code here:
        Bills bill = getFrom();
        List<BillDetail> details = menuSelectionService.getBillDetailsFromTable(tblGetInfo);
        if (details.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có sản phẩm nào để lưu.");
            return;
        }
        boolean success = bd.insertFullBill(bill, details);
        if (success) {
            JOptionPane.showMessageDialog(this, "Lưu hóa đơn thành công.");
            cd.lockCard(String.valueOf(bill.getCard_id()));
            menuSelectionService.resetAll(tblProduct, tblGetInfo, lbTotal);
            // Log activity
            ActivityDAO.logActivity(usm.getUserName(), "SAVE BILL CARD " + bill.getCard_id());
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn.");
        }
        LoadBill();
        LoadDataCbo();
    }//GEN-LAST:event_SaveActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int index = jTable1.getSelectedRow();
        int id = (int) jTable1.getValueAt(index, 0);
        int chooser = JOptionPane.showConfirmDialog(this, "Bạn xóa hóa đơn có mã " + id, "Xóa", JOptionPane.YES_OPTION);
        if (chooser == JOptionPane.YES_OPTION) {
            int rs = bd.delete(id);
            if (rs > 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công hóa đơn có mã " + id);
                LoadBill();
                // Log activity
                ActivityDAO.logActivity(usm.getUserName(), "DELETE BILL " + id);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn không thành công");
                return;
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        LoadBill();
        showProductsData();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int index = jTable1.getSelectedRow();
        int billid = (int) jTable1.getValueAt(index, 0);
        List<BillDetail> details = menuSelectionService.getBillDetailsFromTable(tblGetInfo);
        if (details.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có sản phẩm nào để lưu.");
            return;
        }
        int success = bd.update(billid, details);
        if (success > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhập hóa đơn thành công.");
            menuSelectionService.resetAll(tblProduct, tblGetInfo, lbTotal);
            // Log activity
            ActivityDAO.logActivity(usm.getUserName(), "UPDATE BILL " + billid);
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhập hóa đơn.");
        }
        LoadBill();
        LoadDataCbo();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Save;
    private javax.swing.JButton btnPay;
    private javax.swing.JButton btnReceipt;
    private javax.swing.JButton btnRemove;
    private javax.swing.JComboBox<String> cmoArrange;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JTable tblGetInfo;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}

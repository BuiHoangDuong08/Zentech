package zentech.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import com.sun.imageio.plugins.png.RowFilter;
import dao.ProductDAO;
import entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ProductManager extends javax.swing.JPanel {

    static List<Product> listp = new ArrayList<>();
    static ProductDAO pd = new ProductDAO() {
    };

    public ProductManager() {
        initComponents();
        editinitComponents();
        LoadDataTable();
    }

    public void editinitComponents() {
        txt_Search.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search");
        txt_ID.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "...");
        txt_name.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "...");
        txta_Description.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, ".......");
        txt_ID.setEditable(false);
    }

    public void LoadDataTable() {
        listp = pd.getAllProducts();
        DefaultTableModel model = (DefaultTableModel) tbl_Product.getModel();
        model.setRowCount(0);
        for (Product p : listp) {
            model.addRow(new Object[]{p.getId(), p.getCategoryId(), p.getName(), p.getPrice(), p.getActive(), p.getDescription()});
        }
    }

    public void ShowDeatail() {
        int select = tbl_Product.getSelectedRow();
//        get value select table
        int id = (int) tbl_Product.getValueAt(select, 0);
        int idcate = (int) tbl_Product.getValueAt(select, 1);
        String name = (String) tbl_Product.getValueAt(select, 2);
        double price = (double) tbl_Product.getValueAt(select, 3);
        String active = (String) tbl_Product.getValueAt(select, 4);
        String des = (String) tbl_Product.getValueAt(select, 5);
//        setText
        txt_ID.setText(String.valueOf(id));
        cbo_catagoryid.setSelectedItem(String.valueOf(idcate));
        txt_name.setText(name);
        txt_price.setText(String.valueOf(price));
        cbo_active.setSelectedItem(active);
        txta_Description.setText(des);
    }

    public void find() {
        DefaultTableModel ob = (DefaultTableModel) tbl_Product.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        tbl_Product.setRowSorter(obj);
        obj.setRowFilter(javax.swing.RowFilter.regexFilter(txt_Search.getText()));
    }

    public Product getFrom() {
        Product p = new Product();
        p.setId(Integer.parseInt(txt_ID.getText()));
        p.setCategoryId(Integer.parseInt(cbo_catagoryid.getSelectedItem().toString()));
        p.setName(txt_name.getText());
        p.setPrice(Double.parseDouble(txt_price.getText()));
        p.setActive(cbo_active.getSelectedItem().toString());
        p.setDescription(txta_Description.getText());
        p.setImageUrl(txt_Images.getText());
        return p;
    }

    public Product getFromAdd() {
        Product p = new Product();
        p.setCategoryId(Integer.parseInt(cbo_catagoryid.getSelectedItem().toString()));
        p.setName(txt_name.getText());
        p.setPrice(Double.parseDouble(txt_price.getText()));
        p.setActive(cbo_active.getSelectedItem().toString());
        p.setDescription(txta_Description.getText());
        p.setImageUrl(txt_Images.getText());
        return p;
    }

    public void add() {
        int ret = JOptionPane.showConfirmDialog(this, "Are you sure", "Add", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            Product p = getFromAdd();
            boolean rs = pd.addProduct(p);
            if (rs == true) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                LoadDataTable();
            }
        }
    }

    public void delele() {
        int ret = JOptionPane.showConfirmDialog(this, "Are you sure", "Delete", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txt_ID.getText());
            boolean rs = pd.deleteProduct(id);
            if (rs == true) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                LoadDataTable();
            }
        }
    }

    public void update() {
        int ret = JOptionPane.showConfirmDialog(this, "Are you sure", "Add", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            Product p = getFrom();
            boolean rs = pd.addProduct(p);
            if (rs == true) {
                JOptionPane.showMessageDialog(this, "Cập nhập thành công");
                LoadDataTable();
            }
        }
    }

    public void newInput() {
        txt_ID.setText("");
        cbo_catagoryid.setSelectedIndex(0);
        txt_name.setText("");
        txt_price.setText("");
        cbo_active.setSelectedIndex(0);
        txta_Description.setText("");
    }

    @SuppressWarnings("unchecDked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btn_Word = new javax.swing.JButton();
        btn_Excel = new javax.swing.JButton();
        btn_Update = new javax.swing.JButton();
        btn_New = new javax.swing.JButton();
        btn_Save = new javax.swing.JButton();
        btn_Delete = new javax.swing.JButton();
        txt_Search = new javax.swing.JTextField();
        txt_ID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbo_catagoryid = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txt_name = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbo_active = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txta_Description = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        txt_price = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_Product = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txt_Images = new javax.swing.JTextField();

        jTextField1.setText("jTextField1");

        jLabel8.setText("jLabel8");

        jTextField2.setText("jTextField2");

        jTextField3.setText("jTextField3");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        jLabel1.setText("PRODUCT MANAGER");

        btn_Word.setText("Word");
        btn_Word.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_WordActionPerformed(evt);
            }
        });

        btn_Excel.setText("Excel");

        btn_Update.setText("Update");
        btn_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UpdateActionPerformed(evt);
            }
        });

        btn_New.setText("New");
        btn_New.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NewActionPerformed(evt);
            }
        });

        btn_Save.setText("Save");
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        btn_Delete.setText("Delete");
        btn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteActionPerformed(evt);
            }
        });

        txt_Search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_SearchKeyReleased(evt);
            }
        });

        txt_ID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Id:");

        jLabel2.setText("Category Id:");

        cbo_catagoryid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));

        jLabel4.setText("Name:");

        txt_name.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel5.setText("Active:");

        cbo_active.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVE", "LOCKED" }));

        jLabel6.setText("Description:");

        txta_Description.setColumns(20);
        txta_Description.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txta_Description.setRows(5);
        jScrollPane2.setViewportView(txta_Description);

        jLabel7.setText("Price:");

        tbl_Product.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Id category", "Name", "Price", "Active", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_Product.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ProductMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_Product);
        if (tbl_Product.getColumnModel().getColumnCount() > 0) {
            tbl_Product.getColumnModel().getColumn(0).setPreferredWidth(1);
            tbl_Product.getColumnModel().getColumn(1).setPreferredWidth(1);
            tbl_Product.getColumnModel().getColumn(2).setPreferredWidth(5);
            tbl_Product.getColumnModel().getColumn(3).setPreferredWidth(5);
            tbl_Product.getColumnModel().getColumn(4).setPreferredWidth(2);
            tbl_Product.getColumnModel().getColumn(5).setPreferredWidth(20);
        }

        jLabel9.setText("Images:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Word)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Excel))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_New)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Save)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Delete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Update))
                    .addComponent(txt_ID)
                    .addComponent(jLabel2)
                    .addComponent(cbo_catagoryid, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(txt_name)
                    .addComponent(jLabel5)
                    .addComponent(cbo_active, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel7)
                    .addComponent(txt_price)
                    .addComponent(jLabel9)
                    .addComponent(txt_Images)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_Excel)
                        .addComponent(btn_Word))
                    .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbo_catagoryid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(4, 4, 4)
                        .addComponent(txt_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbo_active, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Images, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_Delete)
                            .addComponent(btn_Save)
                            .addComponent(btn_Update)
                            .addComponent(btn_New))
                        .addContainerGap())
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_WordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_WordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_WordActionPerformed

    private void btn_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UpdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btn_UpdateActionPerformed

    private void btn_NewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NewActionPerformed
        // TODO add your handling code here:
        newInput();
    }//GEN-LAST:event_btn_NewActionPerformed

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        // TODO add your handling code here:
        add();
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteActionPerformed
        // TODO add your handling code here:
        delele();
    }//GEN-LAST:event_btn_DeleteActionPerformed

    private void txt_SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SearchKeyReleased
        // TODO add your handling code here:
        find();
    }//GEN-LAST:event_txt_SearchKeyReleased

    private void tbl_ProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ProductMouseClicked
        // TODO add your handling code here:
        ShowDeatail();
    }//GEN-LAST:event_tbl_ProductMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Delete;
    private javax.swing.JButton btn_Excel;
    private javax.swing.JButton btn_New;
    private javax.swing.JButton btn_Save;
    private javax.swing.JButton btn_Update;
    private javax.swing.JButton btn_Word;
    private javax.swing.JComboBox<String> cbo_active;
    private javax.swing.JComboBox<String> cbo_catagoryid;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTable tbl_Product;
    private javax.swing.JTextField txt_ID;
    private javax.swing.JTextField txt_Images;
    private javax.swing.JTextField txt_Search;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_price;
    private javax.swing.JTextArea txta_Description;
    // End of variables declaration//GEN-END:variables
}

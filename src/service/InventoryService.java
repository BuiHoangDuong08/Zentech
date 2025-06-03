/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ProductDAO;
import entity.Product;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author RGB
 */
public class InventoryService implements ProductDAO {

    public void loadProductToTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        List<Product> list = getAllProducts();
        for (Product p : list) {
            model.addRow(new Object[]{
                p.getId(), p.getCategoryId(), p.getName(), p.getPrice(),
                p.getActive(), p.getDescription(), p.getImageUrl()
            });
        }
    }

    public void hideImageColumn(JTable table) {
        table.getColumnModel().getColumn(6).setMinWidth(0);
        table.getColumnModel().getColumn(6).setMaxWidth(0);
        table.getColumnModel().getColumn(6).setWidth(0);
    }

    public void clearForm(JTextField txtProductID, JTextField txtName, JTextField txtPrice,
            JComboBox<String> cmoActive, JTextField txtDescription,
            JLabel lblImage, JLabel lblID) {
        txtProductID.setText("");
        txtName.setText("");
        txtPrice.setText("");
        cmoActive.setSelectedItem(null);
        txtDescription.setText("");
        lblImage.setIcon(null);
        lblImage.putClientProperty("imagePath", null);
        lblID.setText("");
    }

    public Product getProductInput(JLabel lblID, JTextField txtProductID, JTextField txtName, JTextField txtPrice,
            JComboBox<String> cmoActive, JTextField txtDescription, JLabel lblImage, Component parent) {
        try {
            String idText = lblID.getText().trim();
            Integer id = idText.isEmpty() ? null : Integer.parseInt(idText);
            int categoryId = Integer.parseInt(txtProductID.getText().trim());
            String name = txtName.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());
            String status = cmoActive.getSelectedItem().toString();
            String description = txtDescription.getText().trim();
            String imagePath = lblImage.getClientProperty("imagePath").toString();

            return new Product(id, categoryId, name, price, status, description, imagePath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Dữ liệu không hợp lệ: " + e.getMessage());
            return null;
        }
    }

    public void addProduct(Product p, Component parent, JTable table) {
        if (p != null) {
            boolean success = addProduct(p);
            if (success) {
                JOptionPane.showMessageDialog(parent, "Thêm sản phẩm thành công!");
                loadProductToTable(table);
            } else {
                JOptionPane.showMessageDialog(parent, "Không thể thêm sản phẩm.");
            }
        }
    }

    public void updateProduct(Product p, Component parent, JTable table, Runnable clearForm) {
        if (p != null && p.getId() != null) {
            try {
                // Lấy ảnh cũ từ DB trước khi cập nhật
                String oldImagePath = getImagePathById(p.getId());

                boolean success = updateProduct(p);

                if (success) {
                    // Nếu ảnh mới khác ảnh cũ và ảnh cũ không null thì xóa file ảnh cũ
                    if (oldImagePath != null && !oldImagePath.isEmpty()
                            && !oldImagePath.equals(p.getImageUrl())) {

                        File oldImageFile = new File(System.getProperty("user.dir"), oldImagePath);
                        if (oldImageFile.exists()) {
                            if (!oldImageFile.delete()) {
                                System.out.println("Không thể xóa ảnh cũ: " + oldImageFile.getAbsolutePath());
                            }
                        }
                    }

                    JOptionPane.showMessageDialog(parent, "Cập nhật sản phẩm thành công!");
                    loadProductToTable(table);
                    clearForm.run();
                } else {
                    JOptionPane.showMessageDialog(parent, "Cập nhật thất bại.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Lỗi khi cập nhật: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(parent, "Vui lòng chọn sản phẩm để cập nhật.");
        }
    }

   public void deleteProduct(String idText, Component parent, JTable table, Runnable clearForm) {
    if (idText.isEmpty()) {
        JOptionPane.showMessageDialog(parent, "Vui lòng chọn sản phẩm để xóa.");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(parent, "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        int id = Integer.parseInt(idText);

        // Lấy ảnh của sản phẩm trước khi xóa
        String imagePath = getImagePathById(id);

        boolean success = deleteProduct(id);
        if (success) {
            // Xóa file ảnh sau khi xóa DB thành công
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(System.getProperty("user.dir"), imagePath);
                if (imageFile.exists()) {
                    if (!imageFile.delete()) {
                        System.out.println("Không thể xóa ảnh: " + imageFile.getAbsolutePath());
                    }
                }
            }

            JOptionPane.showMessageDialog(parent, "Xóa sản phẩm thành công.");
            clearForm.run();
            loadProductToTable(table);
        } else {
            JOptionPane.showMessageDialog(parent, "Không thể xóa sản phẩm.");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(parent, "ID không hợp lệ.");
    }
}

    public void chooseImage(JLabel lblImage, Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh sản phẩm");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png", "jpeg", "gif"));

        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String fileName = selectedFile.getName();

                // Thư mục gốc của project
                File baseDir = new File(System.getProperty("user.dir"));

                // Thư mục image trong project: Zentech/image
                File imagesDir = new File(baseDir, "images");

                // Kiểm tra thư mục image đã tồn tại chưa (KHÔNG tạo mới nếu không có)
                if (!imagesDir.exists()) {
                    JOptionPane.showMessageDialog(parent, "Thư mục ảnh không tồn tại: " + imagesDir.getAbsolutePath());
                    return;
                }

                // File đích cần copy vào
                File destination = new File(imagesDir, fileName);

                // Ghi đè nếu đã có
                Files.copy(selectedFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Hiển thị ảnh
                ImageIcon icon = new ImageIcon(destination.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));

                // Gán đường dẫn tương đối để lưu vào DB (ví dụ: image/tenanh.jpg)
                String relativePath = "images/" + fileName;
                lblImage.putClientProperty("imagePath", relativePath);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Lỗi tải ảnh: " + e.getMessage());
            }
        }
    }

    public void fillFormFromTable(JTable table, int row, JTextField txtProductID, JTextField txtName, JTextField txtPrice,
            JComboBox<String> cmoActive, JTextField txtDescription, JLabel lblImage, JLabel lblID) {

        // Lấy dữ liệu từ bảng
        String id = table.getValueAt(row, 0).toString();
        String categoryId = table.getValueAt(row, 1).toString();
        String name = table.getValueAt(row, 2).toString();
        String price = table.getValueAt(row, 3).toString();
        String status = table.getValueAt(row, 4).toString();
        String description = table.getValueAt(row, 5).toString();
        String imagePath = table.getValueAt(row, 6).toString(); // VD: "image/tenanh.jpg"

        // Gán dữ liệu vào form
        lblID.setText(id);
        txtProductID.setText(categoryId);
        txtName.setText(name);
        txtPrice.setText(price);
        cmoActive.setSelectedItem(status);
        txtDescription.setText(description);

        // Xử lý ảnh
        if (imagePath != null && !imagePath.isEmpty()) {
            lblImage.putClientProperty("imagePath", imagePath); // Lưu lại để update về sau

            // Lấy đường dẫn ảnh đầy đủ dựa trên thư mục gốc của project
            File imageFile = new File(System.getProperty("user.dir"), imagePath);

            if (imageFile.exists()) {
                ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));
            } else {
                lblImage.setIcon(null);
                System.out.println("Ảnh không tồn tại tại: " + imageFile.getAbsolutePath());
            }
        } else {
            lblImage.setIcon(null);
            lblImage.putClientProperty("imagePath", null);
        }
    }

    public void filterProductByName(JTextField txtSearch, JTable table1) {
        DefaultTableModel ob = (DefaultTableModel) table1.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        table1.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(txtSearch.getText()));
    }
}

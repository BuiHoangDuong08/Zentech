/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.ProductDAO;
import entity.Product;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import zentechx.menu.ModelItemSell;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import dao.CardDAO;
import entity.Card;
import static service.ListIDcard_service.card;

/**
 *
 * @author RGB
 */
public class MenuSelectionService implements ProductDAO, CardDAO {

    public List<Product> getAllData() {
        return getAllProducts();
    }

    public void loadProductsToTable(JTable table) {
        List<Product> products = getAllData();  // Lấy toàn bộ danh sách sản phẩm
        loadProductsToTable(table, products);   // Gọi hàm nạp dữ liệu chung
    }

    public void loadProductsToTable(JTable table, List<Product> products) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);  // Xóa dữ liệu cũ

        for (Product p : products) {
            ModelItemSell item = new ModelItemSell(p.getId(), p.getName(), 0, p.getPrice(), 0);
            model.addRow(new Object[]{
                item, // Dữ liệu ẩn hoặc dùng renderer riêng
                p.getId(), // Mã sản phẩm
                p.getName(), // Tên sản phẩm
                0, // Số lượng ban đầu
                p.getPrice(), // Giá bán
                0.0, // Thành tiền (sẽ tính sau)
                createImageIcon(p.getImageUrl()) // Ảnh sản phẩm
            });
        }

        table.setRowHeight(60); // Hiển thị hình ảnh đẹp hơn
    }
    
        public void loadCardstoTable(List<Card> listc, JTable jTable1) {
        listc = getAllCards();
        String[] title = {"ID", "Status"};
        DefaultTableModel model = new DefaultTableModel(title, 0);
        for (Card c : listc) {
            model.addRow(new Object[]{c.getId(), c.getStatus()});
        }
        jTable1.setModel(model);
    }

    public void filterProducts(JTextField txtSearch, JTable table1) {
        DefaultTableModel ob = (DefaultTableModel) table1.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        table1.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(txtSearch.getText()));
    }

    public void syncSelectedItemsAndTotal(JTable tblProduct, JTable tblGetInfo, javax.swing.JLabel lbTotal) {
        DefaultTableModel modelSelected = (DefaultTableModel) tblGetInfo.getModel();
        modelSelected.setRowCount(0);
        int total = 0;
        DecimalFormat df = new DecimalFormat("#,### ₫");

        for (int i = 0; i < tblProduct.getRowCount(); i++) {
            int modelRow = tblProduct.convertRowIndexToModel(i); // ✨ CHỈNH Ở ĐÂY
            ModelItemSell item = (ModelItemSell) tblProduct.getModel().getValueAt(modelRow, 0);

            if (item.getQty() > 0) {
                modelSelected.addRow(new Object[]{
                    item.getProductName(),
                    item.getQty(),
                    df.format(item.getPrice())
                });
                total += item.getTotal();
            }
        }

        lbTotal.setText(df.format(total));
    }

    public void resetAll(JTable tblProduct, JTable tblGetInfo, javax.swing.JLabel lbTotal) {
        if (tblProduct.isEditing()) {
            tblProduct.getCellEditor().stopCellEditing();
        }

        DefaultTableModel model = (DefaultTableModel) tblGetInfo.getModel();
        model.setRowCount(0);

        for (int i = 0; i < tblProduct.getRowCount(); i++) {
            int modelRow = tblProduct.convertRowIndexToModel(i);
            ModelItemSell item = (ModelItemSell) tblProduct.getModel().getValueAt(modelRow, 0);
            item.setQty(0);
            item.setTotal(0);
            tblProduct.getModel().setValueAt(0, modelRow, 3);
            tblProduct.getModel().setValueAt("0 ₫", modelRow, 5);
        }

        lbTotal.setText("0 ₫");
    }

    public void generateReceipt(JTable tblGetInfo, javax.swing.JPanel parent, javax.swing.JLabel lbTotal) {
        try {
            String desktopPath = File.separator + "D:\\";
            String filePath = desktopPath + File.separator + "zentech_receipt.pdf";

            Document document = new Document(PageSize.A6);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            BaseFont baseFont = BaseFont.createFont("/zentech/font/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 16, Font.BOLD);
            Font smallFont = new Font(baseFont, 10, Font.NORMAL);
            Font boldFont = new Font(baseFont, 10, Font.BOLD);

            Paragraph title = new Paragraph("ZENTECH\n\n", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{1f, 1f});
            infoTable.addCell(getCell("Staff", PdfPCell.ALIGN_LEFT, smallFont));
            infoTable.addCell(getCell("Admin", PdfPCell.ALIGN_RIGHT, smallFont));
            infoTable.addCell(getCell("Customer", PdfPCell.ALIGN_LEFT, smallFont));
            infoTable.addCell(getCell("MR A", PdfPCell.ALIGN_RIGHT, smallFont));
            document.add(infoTable);

            document.add(new Paragraph("-------------------------------------------------------------------\n\n", smallFont));

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
                String priceStr = model.getValueAt(i, 2).toString().replace("₫", "").replace(",", "").trim();
                double price = Double.parseDouble(priceStr);
                double itemTotal = price * qty;
                grandTotal += itemTotal;

                table.addCell(new Phrase(name, smallFont));
                table.addCell(new Phrase(String.valueOf(qty), smallFont));

                Phrase pricePhrase = new Phrase();
                pricePhrase.add(new Chunk(String.format("%.3f ", price), smallFont));
                Font largerFont = new Font(baseFont, 12, Font.BOLD);
                Chunk currencyChunk = new Chunk("₫", largerFont);
                currencyChunk.setTextRise(-2f); // đẩy chữ "₫" xuống một chút để thẳng hàng
                pricePhrase.add(currencyChunk);
                table.addCell(pricePhrase);

                Phrase itemTotalPhrase = new Phrase();
                itemTotalPhrase.add(new Chunk(String.format("%.3f ", itemTotal), smallFont));
                currencyChunk.setTextRise(-2f); // đẩy chữ "₫" xuống một chút để thẳng hàng
                itemTotalPhrase.add(currencyChunk);
                table.addCell(itemTotalPhrase);
            }

            document.add(table);
            document.add(new Paragraph("-------------------------------------------------------------------", smallFont));

            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(100);
            totalTable.setWidths(new float[]{3f, 1f});
// Cột "Total" label
            PdfPCell labelCell = new PdfPCell(new Phrase("Total", boldFont));
            labelCell.setBorder(PdfPCell.NO_BORDER);
            labelCell.setHorizontalAlignment(Element.ALIGN_LEFT);

// Cột giá trị tổng tiền
            Phrase grandTotalPhrase = new Phrase();
            grandTotalPhrase.add(new Chunk(String.format("%.3f ", grandTotal), smallFont));
            Font largerFont = new Font(baseFont, 12, Font.BOLD);
            Chunk currencyChunk = new Chunk("₫", largerFont);
            currencyChunk.setTextRise(-2f);  // Đẩy chữ "₫" xuống cho thẳng hàng
            grandTotalPhrase.add(currencyChunk);

            PdfPCell valueCell = new PdfPCell(grandTotalPhrase);
            valueCell.setBorder(PdfPCell.NO_BORDER);
            valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

// Thêm 2 ô vào bảng
            totalTable.addCell(labelCell);
            totalTable.addCell(valueCell);
            document.add(totalTable);

            document.close();
            JOptionPane.showMessageDialog(parent, "Đã lưu hóa đơn tại Desktop (zentech_receipt.pdf)");
            System.out.println(Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Lỗi xuất PDF: " + e.getMessage());
        }
    }

    public ImageIcon createImageIcon(String imagePath) {
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(System.getProperty("user.dir"), imagePath);
                if (imageFile.exists()) {
                    ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                    Image img = icon.getImage().getScaledInstance(165, 165, Image.SCALE_SMOOTH); // Đổi size tùy theo lblImage
                    return new ImageIcon(img);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private PdfPCell getCell(String text, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }

    public void filterProductsByCategory(int categoryId, JTable tblProduct) {
        List<Product> allProducts = getAllProducts(); // Giả sử đã có hàm lấy toàn bộ sản phẩm

        List<Product> filtered;
        if (categoryId == 0) { // Mặc định: show tất cả
            filtered = allProducts;
        } else {
            filtered = allProducts.stream()
                    .filter(p -> p.getCategoryId() == categoryId)
                    .collect(Collectors.toList());
        }
        loadProductsToTable(tblProduct, filtered);
    }

    private List<Product> selectedProducts = new ArrayList<>();

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public boolean selectCard(String cardId, JTable tblGetInfo) {
        if (tblGetInfo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm!");
            return false;
        }

        return lockCard(cardId);
    }

    public boolean releaseCard(String cardId) {
        return unlockCard(cardId);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.ProductDAO;
import entity.Product;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import zentechx.menu.ModelItemSell;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author RGB
 */
public class MenuSelectionService implements ProductDAO {

    public List<Product> getAllData() {
        return getAllProducts();
    }

    public void loadProductsToTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        List<Product> products = getAllData();
        for (Product p : products) {
            model.addRow(new Object[]{
                new ModelItemSell(p.getId(), p.getName(), 0, p.getPrice(), 0),
                p.getId(),
                p.getName(),
                0,
                p.getPrice(),
                0,
                createImageIcon(p.getImageUrl())
            });
        }
        table.setRowHeight(60);
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
        DecimalFormat df = new DecimalFormat("$ #,###");

        for (int i = 0; i < tblProduct.getRowCount(); i++) {
            ModelItemSell item = (ModelItemSell) tblProduct.getValueAt(i, 0);
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
            ModelItemSell item = (ModelItemSell) tblProduct.getValueAt(i, 0);
            item.setQty(0);
            item.setTotal(0);
            tblProduct.setValueAt(0, i, 3);
            tblProduct.setValueAt("$ 0", i, 5);
        }

        lbTotal.setText("$ 0");
    }

    public void generateReceipt(JTable tblGetInfo, javax.swing.JPanel parent, javax.swing.JLabel lbTotal) {
        try {
            
            String desktopPath = File.separator + "D:\\";
            String filePath = desktopPath + File.separator + "zentech_receipt.pdf";

            Document document = new Document(PageSize.A6);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font smallFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

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

            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(100);
            totalTable.setWidths(new float[]{3f, 1f});
            totalTable.addCell(getCell("Total", PdfPCell.ALIGN_LEFT, boldFont));
            totalTable.addCell(getCell(String.format("$ %.2f", grandTotal), PdfPCell.ALIGN_RIGHT, boldFont));
            document.add(totalTable);

            document.close();
            JOptionPane.showMessageDialog(parent, "Đã lưu hóa đơn tại Desktop (zentech_receipt.pdf)");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Lỗi xuất PDF: " + e.getMessage());
        }
    }

    public ImageIcon createImageIcon(String path) {
        try {
            ImageIcon icon = new ImageIcon(path);
            Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private PdfPCell getCell(String text, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }
}

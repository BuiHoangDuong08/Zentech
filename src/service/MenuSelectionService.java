package service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import dao.BillDAO;
import dao.CardDAO;
import dao.ProductDAO;
import entity.BillDetail;
import entity.Card;
import entity.Product;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import zentechx.menu.ModelItemSell;

public class MenuSelectionService implements ProductDAO, CardDAO {

    private List<Product> selectedProducts = new ArrayList<>();

    public List<Product> getAllData() {
        return getAllProducts();
    }

    public void loadProductsToTable(JTable table) {
        loadProductsToTable(table, getAllData());
    }

    public void loadProductsToTable(JTable table, List<Product> products) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Product p : products) {
            ModelItemSell item = new ModelItemSell(p.getId(), p.getName(), 0, p.getPrice(), 0);
            model.addRow(new Object[]{
                item,
                p.getId(),
                p.getName(),
                0,
                p.getPrice(),
                0.0,
                createImageIcon(p.getImageUrl())
            });
        }

        table.setRowHeight(60);
    }

    public void loadCardstoTable(List<Card> listc, JTable jTable1) {
        if (listc == null) {
            listc = getAllCards();
        }
        String[] title = {"ID", "Status"};
        DefaultTableModel model = new DefaultTableModel(title, 0);
        for (Card c : listc) {
            model.addRow(new Object[]{c.getId(), c.getStatus()});
        }
        jTable1.setModel(model);
    }

    public void filterProducts(JTextField txtSearch, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(txtSearch.getText()));
    }

    public void syncSelectedItemsAndTotal(JTable tblProduct, JTable tblGetInfo, JLabel lbTotal) {
        DefaultTableModel selectedModel = (DefaultTableModel) tblGetInfo.getModel();
        selectedModel.setRowCount(0);
        int total = 0;
        DecimalFormat df = new DecimalFormat("#,### ₫");

        for (int i = 0; i < tblProduct.getRowCount(); i++) {
            int modelRow = tblProduct.convertRowIndexToModel(i);
            ModelItemSell item = (ModelItemSell) tblProduct.getModel().getValueAt(modelRow, 0);
            if (item.getQty() > 0) {
                selectedModel.addRow(new Object[]{
                    item.getProductId(),
                    item.getProductName(),
                    item.getQty(),
                    df.format(item.getPrice())
                });
                total += item.getTotal();
            }
        }

        lbTotal.setText(df.format(total));
    }

    public void resetAll(JTable tblProduct, JTable tblGetInfo, JLabel lbTotal) {
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

    public void generateReceipt(JTable tblGetInfo, JPanel parent, JLabel lbTotal) {
        try {
            String filePath = "D:\\zentech_receipt.pdf";
            Document document = new Document(PageSize.A6);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            BaseFont baseFont = BaseFont.createFont("zentech/font/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 16, Font.BOLD);
            Font smallFont = new Font(baseFont, 10, Font.NORMAL);
            Font boldFont = new Font(baseFont, 10, Font.BOLD);

            Paragraph title = new Paragraph("ZENTECH\n\n", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{1f, 1f});
            infoTable.addCell(getCell("Staff", Element.ALIGN_LEFT, smallFont));
            infoTable.addCell(getCell("Admin", Element.ALIGN_RIGHT, smallFont));
            infoTable.addCell(getCell("Customer", Element.ALIGN_LEFT, smallFont));
            infoTable.addCell(getCell("MR A", Element.ALIGN_RIGHT, smallFont));
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
                String name = String.valueOf(model.getValueAt(i, 0));
                int qty = Integer.parseInt(String.valueOf(model.getValueAt(i, 1)));
                String priceStr = String.valueOf(model.getValueAt(i, 2)).replace("₫", "").replace(",", "").trim();
                double price = Double.parseDouble(priceStr);
                double itemTotal = price * qty;
                grandTotal += itemTotal;

                table.addCell(new Phrase(name, smallFont));
                table.addCell(new Phrase(String.valueOf(qty), smallFont));
                table.addCell(formatCurrencyPhrase(price, smallFont, baseFont));
                table.addCell(formatCurrencyPhrase(itemTotal, smallFont, baseFont));
            }

            document.add(table);
            document.add(new Paragraph("-------------------------------------------------------------------", smallFont));

            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(100);
            totalTable.setWidths(new float[]{3f, 1f});
            totalTable.addCell(getCell("Total", Element.ALIGN_LEFT, boldFont));
            totalTable.addCell(new PdfPCell(formatCurrencyPhrase(grandTotal, smallFont, baseFont)) {
                {
                    setBorder(PdfPCell.NO_BORDER);
                    setHorizontalAlignment(Element.ALIGN_RIGHT);
                }
            });
            document.add(totalTable);

            document.close();
            JOptionPane.showMessageDialog(parent, "Đã lưu hóa đơn tại Desktop (zentech_receipt.pdf)");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Lỗi xuất PDF: " + e.getMessage());
        }
    }

    public void filterProductsByCategory(int categoryId, JTable tblProduct) {
        List<Product> allProducts = getAllProducts();
        List<Product> filtered = (categoryId == 0)
                ? allProducts
                : allProducts.stream().filter(p -> p.getCategoryId() == categoryId).collect(Collectors.toList());
        loadProductsToTable(tblProduct, filtered);
    }

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

    public ImageIcon createImageIcon(String imagePath) {
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(System.getProperty("user.dir"), imagePath);
                if (imageFile.exists()) {
                    ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                    Image img = icon.getImage().getScaledInstance(165, 165, Image.SCALE_SMOOTH);
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

    private Phrase formatCurrencyPhrase(double amount, Font font, BaseFont baseFont) {
        Phrase phrase = new Phrase();
        phrase.add(new Chunk(String.format("%.3f ", amount), font));
        Chunk currency = new Chunk("₫", new Font(baseFont, 12, Font.BOLD));
        currency.setTextRise(-2f);
        phrase.add(currency);
        return phrase;
    }

    public void generateReceiptFromPaidBill(int billId, JPanel parent) {
        try {
            BillDAO bd = new BillDAO();
            List<BillDetail> details = bd.getAllBill();  // Giả định đã có toàn bộ danh sách
            List<BillDetail> filtered = details.stream()
                    .filter(d -> d.getBill().getId() == billId)
                    .collect(Collectors.toList());

            if (filtered.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Không tìm thấy sản phẩm nào cho hóa đơn.");
                return;
            }

            String filePath = "D:\\zentech_paid_receipt_bill_" + billId + ".pdf";
            Document document = new Document(PageSize.A6);

            PdfWriter.getInstance(document,
                    new FileOutputStream(filePath));
            document.open();

            BaseFont baseFont = BaseFont.createFont("zentech/font/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 16, Font.BOLD);
            Font normalFont = new Font(baseFont, 10, Font.NORMAL);
            Font boldFont = new Font(baseFont, 10, Font.BOLD);

            Paragraph title = new Paragraph("ZENTECH - HÓA ĐƠN THANH TOÁN\n\n", titleFont);

            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);

            BillDetail header = filtered.get(0);

            PdfPTable infoTable = new PdfPTable(2);

            infoTable.setWidthPercentage(
                    100);
            infoTable.addCell(getCell("Mã hóa đơn", Element.ALIGN_LEFT, boldFont));
            infoTable.addCell(getCell(String.valueOf(billId), Element.ALIGN_RIGHT, normalFont));
            infoTable.addCell(getCell("Mã thẻ", Element.ALIGN_LEFT, boldFont));
            infoTable.addCell(getCell(String.valueOf(header.getBill().getCard_id()), Element.ALIGN_RIGHT, normalFont));
            infoTable.addCell(getCell("Ngày", Element.ALIGN_LEFT, boldFont));
            infoTable.addCell(getCell(header.getDate().toString(), Element.ALIGN_RIGHT, normalFont));
            document.add(infoTable);

            document.add(
                    new Paragraph("\n------------------------------------------------\n", normalFont));

            PdfPTable table = new PdfPTable(4);

            table.setWidthPercentage(
                    100);
            table.setWidths(
                    new float[]{2f,
                        1f, 1f, 1f});
            table.addCell(
                    new Phrase("Product ID", boldFont));
            table.addCell(
                    new Phrase("Qty", boldFont));
            table.addCell(
                    new Phrase("Price", boldFont));
            table.addCell(
                    new Phrase("Total", boldFont));

            double grandTotal = 0;
            for (BillDetail d : filtered) {
                int qty = d.getQuantity();
                double price = d.getTotalprice_withvat() / qty;
                double total = d.getTotalprice_withvat();
                grandTotal += total;

                table.addCell(new Phrase(String.valueOf(d.getProduct_id()), normalFont));
                table.addCell(new Phrase(String.valueOf(qty), normalFont));
                table.addCell(new Phrase(String.format("%.0f ₫", price), normalFont));
                table.addCell(new Phrase(String.format("%.0f ₫", total), normalFont));
            }

            document.add(table);

            document.add(
                    new Paragraph("\nTổng cộng: " + String.format("%.0f ₫", grandTotal), boldFont));
            document.close();

            JOptionPane.showMessageDialog(parent,
                    "Hóa đơn đã được in tại:\n" + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Lỗi khi in hóa đơn: " + e.getMessage());
        }
    }

    public List<BillDetail> getBillDetailsFromTable(JTable tblGetInfo) {
        List<BillDetail> list = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tblGetInfo.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                int productId = Integer.parseInt(model.getValueAt(i, 0).toString());
                int quantity = Integer.parseInt(model.getValueAt(i, 2).toString());
                String priceStr = model.getValueAt(i, 3).toString().replace("₫", "").replace(",", "").trim();
                double price = Double.parseDouble(priceStr);

                Date sqlDate = Date.valueOf(LocalDateTime.now().toLocalDate());

                BillDetail bd = new BillDetail();
                bd.setProduct_id(productId);
                bd.setQuantity(quantity);
                bd.setDate(sqlDate);
                bd.setDiscount(0f);
                bd.setTotalprice_novat(price * quantity);
                bd.setTotalprice_withvat(price * quantity);

                list.add(bd);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return list;
    }
}

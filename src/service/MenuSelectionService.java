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
import dao.CardDAO;
import entity.Product;
import entity.Card;
import zentechx.menu.ModelItemSell;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class MenuSelectionService implements ProductDAO, CardDAO {

    private final Map<Integer, Integer> qtyMap = new HashMap<>();
    private List<ModelItemSell> allItemSellList = new ArrayList<>();
    private final Map<String, ModelItemSell> itemStateMap = new HashMap<>();

    public List<Product> getAllData() {
        return getAllProducts();
    }

    public void loadProductsToTable(JTable table) {
        List<Product> products = getAllData();
        loadProductsToTable(table, products);
    }

    public void loadProductsToTable(JTable table, List<Product> products) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Product p : products) {
            String key = String.valueOf(p.getId());

            ModelItemSell item;

            if (itemStateMap.containsKey(key)) {
                item = itemStateMap.get(key);
            } else {
                int preservedQty = qtyMap.getOrDefault(p.getId(), 0);
                double price = p.getPrice();
                double total = preservedQty * price;

                item = new ModelItemSell(p.getId(), p.getName(), preservedQty, price, total);
                itemStateMap.put(key, item);
                allItemSellList.add(item);
            }

            model.addRow(new Object[]{
                item,
                p.getId(),
                p.getName(),
                item.getQty(),
                p.getPrice(),
                item.getTotal(),
                createImageIcon(p.getImageUrl())
            });
        }

        table.setRowHeight(60);
    }

    public void filterProductsByCategory(int categoryId, JTable tblProduct) {
        if (tblProduct.isEditing()) {
            tblProduct.getCellEditor().stopCellEditing();
        }
        List<Product> filtered = categoryId == 0 ? getAllData() :
            getAllData().stream()
                        .filter(p -> p.getCategoryId() == categoryId)
                        .collect(Collectors.toList());
        loadProductsToTable(tblProduct, filtered);
    }

    public void preserveQtyBeforeFilter(JTable tblProduct) {
        qtyMap.clear();
        for (int i = 0; i < tblProduct.getRowCount(); i++) {
            int modelRow = tblProduct.convertRowIndexToModel(i);
            ModelItemSell item = (ModelItemSell) tblProduct.getModel().getValueAt(modelRow, 0);
            qtyMap.put(item.getProductId(), item.getQty());
        }
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

    public void syncSelectedItemsAndTotal(JTable tblProduct, JTable tblGetInfo, JLabel lbTotal) {
        DefaultTableModel modelSelected = (DefaultTableModel) tblGetInfo.getModel();
        modelSelected.setRowCount(0);
        int total = 0;
        DecimalFormat df = new DecimalFormat("#,### ₫");

        for (ModelItemSell item : allItemSellList) {
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

            BaseFont baseFont = BaseFont.createFont("/zentech/font/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 16, Font.BOLD);
            Font smallFont = new Font(baseFont, 10, Font.NORMAL);
            Font boldFont = new Font(baseFont, 10, Font.BOLD);
            Font largerFont = new Font(baseFont, 12, Font.BOLD);

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
                table.addCell(formatCurrencyPhrase(price, smallFont, largerFont));
                table.addCell(formatCurrencyPhrase(itemTotal, smallFont, largerFont));
            }

            document.add(table);
            document.add(new Paragraph("-------------------------------------------------------------------", smallFont));

            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(100);
            totalTable.setWidths(new float[]{3f, 1f});
            totalTable.addCell(getCell("Total", PdfPCell.ALIGN_LEFT, boldFont));
            totalTable.addCell(new PdfPCell(formatCurrencyPhrase(grandTotal, smallFont, largerFont)) {{
                setBorder(PdfPCell.NO_BORDER);
                setHorizontalAlignment(Element.ALIGN_RIGHT);
            }});
            document.add(totalTable);

            document.close();
            JOptionPane.showMessageDialog(parent, "Đã lưu hóa đơn tại Desktop (zentech_receipt.pdf)");
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

    private Phrase formatCurrencyPhrase(double value, Font numberFont, Font currencyFont) {
        Phrase phrase = new Phrase();
        phrase.add(new Phrase(String.format("%.3f ", value), numberFont));
        Chunk currencyChunk = new Chunk("₫", currencyFont);
        currencyChunk.setTextRise(-2f);
        phrase.add(currencyChunk);
        return phrase;
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

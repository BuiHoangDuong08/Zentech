package entity;

import java.sql.Timestamp;

public class BillDetails {
    private int id;
    private int billId;
    private int productId;
    private Timestamp date;
    private int quantity;
    private float discount;
    private double totalPriceNoVAT;
    private double totalPriceWithVAT;

    public BillDetails() {}

    public BillDetails(int id, int billId, int productId, Timestamp date, int quantity, float discount,
                       double totalPriceNoVAT, double totalPriceWithVAT) {
        this.id = id;
        this.billId = billId;
        this.productId = productId;
        this.date = date;
        this.quantity = quantity;
        this.discount = discount;
        this.totalPriceNoVAT = totalPriceNoVAT;
        this.totalPriceWithVAT = totalPriceWithVAT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public double getTotalPriceNoVAT() {
        return totalPriceNoVAT;
    }

    public void setTotalPriceNoVAT(double totalPriceNoVAT) {
        this.totalPriceNoVAT = totalPriceNoVAT;
    }

    public double getTotalPriceWithVAT() {
        return totalPriceWithVAT;
    }

    public void setTotalPriceWithVAT(double totalPriceWithVAT) {
        this.totalPriceWithVAT = totalPriceWithVAT;
    }

    @Override
    public String toString() {
        return "BillDetails{" + "id=" + id + ", billId=" + billId + ", productId=" + productId + ", date=" + date + ", quantity=" + quantity + ", discount=" + discount + ", totalPriceNoVAT=" + totalPriceNoVAT + ", totalPriceWithVAT=" + totalPriceWithVAT + '}';
    }
}


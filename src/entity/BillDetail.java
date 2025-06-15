package entity;

import java.sql.Date;

public class BillDetail {

    private int stt;
    private int product_id;
    private Date date;
    private int quantity;
    private float discount;
    private double totalprice_novat;
    private double totalprice_withvat;
    private Bills bill = new Bills();

    public BillDetail() {
    }

    public BillDetail(int stt, int product_id, Date date, int quantity, float discount, double totalprice_novat, double totalprice_withvat) {
        this.stt = stt;
        this.product_id = product_id;
        this.date = date;
        this.quantity = quantity;
        this.discount = discount;
        this.totalprice_novat = totalprice_novat;
        this.totalprice_withvat = totalprice_withvat;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public double getTotalprice_novat() {
        return totalprice_novat;
    }

    public void setTotalprice_novat(double totalprice_novat) {
        this.totalprice_novat = totalprice_novat;
    }

    public double getTotalprice_withvat() {
        return totalprice_withvat;
    }

    public void setTotalprice_withvat(double totalprice_withvat) {
        this.totalprice_withvat = totalprice_withvat;
    }

    public Bills getBill() {
        return bill;
    }

    public void setBill(Bills bill) {
        this.bill = bill;
    }

}

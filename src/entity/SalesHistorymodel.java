package entity;

import java.sql.Date; // Để dùng kiểu Date từ CSDL

public class SalesHistorymodel {
    private int id;
    private int billId;
    private Date date;
    private String username;
    private String items;
    private int totalQuantity;
    private double totalAmount;
    private String status;

    public void setId(int id) {
        this.id = id;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getBillId() {
        return billId;
    }

    public Date getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public String getItems() {
        return items;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public SalesHistorymodel(int id, int billId, Date date, String username, String items, int totalQuantity, double totalAmount, String status) {
        this.id = id;
        this.billId = billId;
        this.date = date;
        this.username = username;
        this.items = items;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public SalesHistorymodel() {
    }

}
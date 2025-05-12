package entity;

public class Bill {
    private int id;
    private int userId;
    private int cardId;
    private String status; //UNPAID, PAID, CANCELLED

    public Bill() {}

    public Bill(int id, int userId, int cardId, String status) {
        this.id = id;
        this.userId = userId;
        this.cardId = cardId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bill{" + "id=" + id + ", userId=" + userId + ", cardId=" + cardId + ", status=" + status + '}';
    }
}


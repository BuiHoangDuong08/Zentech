package entity;

public class Card {
    private int id;
    private String status; //ACTIVE, WAIT, LOCKED

    public Card() {}

    public Card(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Card{id=" + id + ", status='" + status + "'}";
    }
}


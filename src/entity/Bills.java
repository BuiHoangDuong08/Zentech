package entity;

public class Bills {

    private int id;
    private int user_id;
    private int card_id;
    private String status;

    public Bills(int id, int user_id, int card_id, String status) {
        this.id = id;
        this.user_id = user_id;
        this.card_id = card_id;
        this.status = status;
    }

    public Bills() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

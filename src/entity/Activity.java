/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDateTime;

/**
 *
 * @author Duc Pham Ngoc
 */
public class Activity {
    private int id;               // Nếu bạn cần lưu tự động INCREMENT thì có thể bỏ qua setter
    private String userId;        // hoặc int userId tuỳ bạn
    private String action;        // “LOGIN” hoặc “LOGOUT”
    private LocalDateTime timestamp;

    public Activity() {
    }

    public Activity(String userId, String action, LocalDateTime timestamp) {
        this.userId = userId;
        this.action = action;
        this.timestamp = timestamp;
    }

    // getters và setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

package entity;

import java.sql.Date;

public class User {
    private int id;
    private int roleId;
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private String gender; //MALE, FEMALE, NONE
    private String address;
    private Date dob;
    private String phoneNumber;

    public User() {}

    public User(int id, int roleId, String userName, String password, String email,
                String fullName, String gender, String address, Date dob, String phoneNumber) {
        this.id = id;
        this.roleId = roleId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}


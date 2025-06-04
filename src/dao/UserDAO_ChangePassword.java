/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.UserModel;
import helper.ConnectionHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Duc Pham Ngoc
 */
public class UserDAO_ChangePassword {
    public static UserDAO_ChangePassword getInstance(){
        return new UserDAO_ChangePassword();
    }
    
     public void sendOpt(String email, String opt){
        int result;
        try {
            Connection con = (Connection) ConnectionHelper.getConnection();
            String sql = "UPDATE USER SET otp = ? WHERE Email = ?;";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, opt);
            pst.setString(2, email);
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public UserModel selectByEmail(String t) {
        UserModel usm = null;
        try {
            Connection con = ConnectionHelper.getConnection();
            String sql = "SELECT * FROM USER WHERE Email = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("ID");
                int roleId = rs.getInt("Role_ID");
                String userName = rs.getString("UserName");
                String password = rs.getString("Password");
                String email = rs.getString("Email");
                String fullName = rs.getString("FullName");
                String gender = rs.getString("Gender");
                String address = rs.getString("Address");
                java.sql.Date dob = rs.getDate("DOB");
                String phoneNumber = rs.getString("PhoneNumber");
                String otp = rs.getString("otp");
                usm = new UserModel(id, roleId, userName, password, email, fullName, gender, address, dob, phoneNumber);
                return usm;
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usm; 
    }
    
    public boolean checkOtp(String email, String otp){
        boolean check = false;
        try {
            Connection con = (Connection) ConnectionHelper.getConnection();
            String sql = "SELECT * FROM USER WHERE Email = ? AND otp = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, otp);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                check = true;
                return check;
            }
        } catch (Exception e) {
        }
        return check;
    }
    
    public void updatePass(String email, String password){
        int result;
        try {
            Connection con = (Connection) ConnectionHelper.getConnection();
            String sql = "UPDATE USER SET Password = ? WHERE Email = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, password);
            pst.setString(2, email);
            result = pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }
}

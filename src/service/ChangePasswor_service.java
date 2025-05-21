/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.UserDAO;
import entity.User;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ChangePasswor_service {

    static UserDAO ud = new UserDAO() {
    };

    public boolean checknull(JTextField username, JTextField oldpass, JTextField newpass, JTextField confrimpass) {
        if (username.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter username");
            return false;
        }
        if (oldpass.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter old Password");
            return false;
        }
        if (newpass.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter new Password");
            return false;
        }
        if (confrimpass.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please confirm new password");
            return false;
        } else {
            return true;
        }
    }

    public void getChangePassword(JTextField us, JTextField op, JTextField np, JTextField cp) {
        boolean check1 = false;
        boolean check2 = false;
        if (checknull(us, op, np, cp)) {
            for (User u : ud.getAllUsers()) {
                if (u.getUserName().equals(us.getText().trim()) && u.getPassword().equals(op.getText().trim())) {
                    check1 = true;
                    if (np.getText().equals(cp.getText().trim())) {
                        check2 = true;
                        boolean rs = ud.updatepassword(cp.getText().trim(), us.getText().trim(), op.getText().trim());
                        if (rs == true) {
                            JOptionPane.showMessageDialog(null, "Password changed successfully");
                            return;
                        }
                    }
                }
            }

            if (check1 == false) {
                JOptionPane.showMessageDialog(null, "Wrong login name or password");
                return;
            }
            if (check2 == false) {
                JOptionPane.showMessageDialog(null, "Confirm password does not match");
                return;
            }
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.UserDAO;
import entity.UserModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static service.LoginFrom_service.bsv;

public class ChangePasswor_service {

    static UserDAO ud = new UserDAO() {
    };
    static BCrypt_service bsv = new BCrypt_service();

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
            for (UserModel u : ud.getAllUsers()) {
                String user = u.getUserName();
                String pass = u.getPassword();
                if (user.equals(us.getText()) && bsv.checkPassword(op.getText(), pass)) {
                    check1 = true;
                    if (np.getText().equals(cp.getText().trim())) {
                        check2 = true;
                        String hashpass = bsv.hashPassword(cp.getText());
                        boolean rs = ud.updatepassword(hashpass, us.getText().trim());
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

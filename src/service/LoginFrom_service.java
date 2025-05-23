package service;

import dao.UserDAO;
import entity.UserModel;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import zentech.application.Application;

public class LoginFrom_service extends JPanel {

    static UserDAO ud = new UserDAO() {
    };
     static BCrypt_service bsv = new BCrypt_service();

    public void getLogin(JTextField usn, JTextField pass) {
        String usn1 = usn.getText().trim();
        String pass1 = pass.getText().trim();
        boolean checklogin = false;
        if (checkNull(usn, pass)) {
            for (UserModel u : ud.getAllUsers()) {
                if (u.getUserName().equals(usn1) && bsv.checkPassword(pass1, u.getPassword())) {
                    checklogin = true;
                    JOptionPane.showMessageDialog(null, "Login successfully");
                    this.setVisible(false);
                    Application.login();

                    return;
                }
            }
            if (checklogin == false) {
                JOptionPane.showMessageDialog(null, "Login Falied");
                return;
            }
        }
    }

    public boolean checkNull(JTextField usn, JTextField pass) {
        if (usn.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter username");
            return false;
        }
        if (pass.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter password");
            return false;
        } else {
            return true;
        }
    }
}

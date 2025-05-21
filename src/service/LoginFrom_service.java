package service;

import dao.UserDAO;
import entity.User;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import zentech.application.Application;

public class LoginFrom_service extends JPanel {

    static UserDAO ud = new UserDAO() {
    };

    public void getLogin(JTextField usn, JTextField pass) {
        String usn1 = usn.getText().trim();
        String pass1 = pass.getText().trim();
        boolean checklogin = false;
        if (checkNull(usn, pass)) {
            for (User u : ud.getAllUsers()) {
                if (u.getUserName().equals(usn1) && u.getPassword().equals(pass1)) {
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

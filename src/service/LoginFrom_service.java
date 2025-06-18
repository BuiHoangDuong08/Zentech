package service;

import dao.ActivityDAO;
import dao.UserDAO;
import entity.Activity;
import entity.UserModel;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import raven.toast.Notifications;
import zentech.application.Application;
import zentech.application.Login;
import zentech.application.form.other.User;

public class LoginFrom_service extends JPanel {

    UserService userService = new UserService();
    static UserDAO ud = new UserDAO() {
    };
    static BCrypt_service bsv = new BCrypt_service();
    UserModel u = null;

    public boolean checkNull(JTextField usn, JTextField pass) {
        if (usn.getText().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Please enter username");
            return false;
        }
        if (pass.getText().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Please enter password");
            return false;
        } else {
            return true;
        }
    }
}

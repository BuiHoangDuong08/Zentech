package service;

import dao.UserDAO;
import entity.UserModel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import raven.toast.Notifications;
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
                    try {
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Login successfully");
                        this.setVisible(false);
                        Application.login();
                    } catch (Exception ex) {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "There was an error while logging in.");
                    }
                    return;
                }
            }
            if (checklogin == false) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Wrong username or password");
                return;
            }
        }
    }

    public boolean checkNull(JTextField usn, JTextField pass) {
        if (usn.getText().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_RIGHT, "Please enter username");
            return false;
        }
        if (pass.getText().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.BOTTOM_RIGHT, "Please enter password");
            return false;
        } else {
            return true;
        }
    }
}

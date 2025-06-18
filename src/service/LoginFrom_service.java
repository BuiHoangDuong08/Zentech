package service;

import dao.ActivityDAO;
import dao.UserDAO;
import entity.Activity;
import entity.UserModel;
import java.time.LocalDateTime;
import javax.swing.JPanel;
import javax.swing.JTextField;
import raven.toast.Notifications;
import zentech.application.Application;
import zentech.application.form.other.User;

public class LoginFrom_service extends JPanel {
    UserService userService = new UserService();
    static UserDAO ud = new UserDAO() {
    };
    static BCrypt_service bsv = new BCrypt_service();

    UserModel u = null;


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
//                        this.u = new UserModel();
//                        u.setFullName(u.getFullName());
//                        u.setRoleId(u.getRoleId());
                        Application app = new Application(u);
                        this.setVisible(false);
                        app.setVisible(true);
                        ActivityDAO.insert(new Activity(usn1, "LOGIN", LocalDateTime.now()));
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

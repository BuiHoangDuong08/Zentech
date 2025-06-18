package service;

import dao.UserDAO;
import entity.UserModel;

import javax.swing.JTextField;
import raven.toast.Notifications;

public class ChangePassword_service {

    static UserDAO ud = new UserDAO() {
    };
    static BCrypt_service bsv = new BCrypt_service();

    public boolean checknull(JTextField username, JTextField oldpass, JTextField newpass, JTextField confrimpass) {
        if (username.getText().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Please enter username");

            return false;
        }
        if (oldpass.getText().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Please enter old Password");
            return false;
        }
        if (newpass.getText().isEmpty()) {

            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Please enter new Password");

            return false;
        }
        if (confrimpass.getText().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Please confirm new password");
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

                            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Password changed successfully");

                            return;
                        }
                    }
                }
            }
            if (check1 == false) {

                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Wrong login name or password");

                return;
            }
            if (check2 == false) {

                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Confirm password does not match");
                return;
            }
        }
    }
}

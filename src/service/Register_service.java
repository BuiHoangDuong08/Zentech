package service;

import dao.UserDAO;
import entity.UserModel;
import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import raven.toast.Notifications;
import static service.ListIDcard_service.card;

public class Register_service implements UserDAO {

    static BCrypt_service bsv = new BCrypt_service();

    public boolean checkNull(JTextField txtUserName, JTextField txtPassword, JTextField txtCnfrimpass,
            JTextField txtEmail, JTextField txtFullName, JRadioButton rdoMALE,
            JRadioButton rdoFEMALE, JTextArea txtaAddress, JTextField txtdob, JTextField txtPhoneNumber) {
        //Kiểm tra rỗng 
        if (txtUserName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter username");
            return false;
        }
        if (txtPassword.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter password");
            return false;
        }
        if (txtCnfrimpass.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please confirm your password");
            return false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter email");
            return false;
        }

        if (txtFullName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter full name");
            return false;
        }
        if (!rdoMALE.isSelected() && !rdoFEMALE.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please select gender");
            return false;
        }
        if (txtaAddress.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter address");
            return false;
        }
        if (txtdob.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter date of birth");
            return false;
        } else {
            try {
                LocalDate.parse(txtdob.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid date");
                return false;
            }
        }
        if (txtPhoneNumber.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter phone number");
            return false;
        }
        //Kiểm tra định dạng 
        if (!Pattern.matches("^0\\d{9}$", txtPhoneNumber.getText())) {
            JOptionPane.showMessageDialog(null, "Phone number must be 10 digits and start with 0");
            return false;
        }

        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", txtEmail.getText())) {
            JOptionPane.showMessageDialog(null, "Invalid email format");
            return false;
        } else {
            return true;
        }
    }

    public void getRegister(JTextField txtUserName, JTextField txtPassword, JTextField txtCnfrimpass,
            JTextField txtEmail, JTextField txtFullName, JRadioButton rdoMALE,
            JRadioButton rdoFEMALE, JTextArea txtaAddress, JTextField txtdob, JTextField txtPhoneNumber) {
        if (checkNull(txtUserName, txtPassword, txtCnfrimpass,
                txtEmail, txtFullName, rdoMALE, rdoFEMALE,
                txtaAddress, txtdob, txtPhoneNumber)) {
            //Lấy giá trị từ From set thuộc tính cho đối tượng
            UserModel user = new UserModel();
            user.setUserName(txtUserName.getText().trim());
            String hasspass = bsv.hashPassword(txtPassword.getText());
            user.setPassword(hasspass);
            user.setEmail(txtEmail.getText().trim());
            user.setFullName(txtFullName.getText().trim());
            String gender = rdoMALE.isSelected() ? "Male" : "Female";
            user.setGender(gender);
            user.setAddress(txtaAddress.getText().trim());
            user.setDob(Date.valueOf(txtdob.getText().trim()));
            user.setPhoneNumber(txtPhoneNumber.getText().trim());
            int ret = JOptionPane.showConfirmDialog(null, "Are you sure", "Add", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                if (txtPassword.getText().equals(txtCnfrimpass.getText())) {
                    boolean rs = addUser(user);
                    if (rs == true) {
                        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Registration successful");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Confirmation password is incorrect");
                    return;
                }

            }
        }
    }
}

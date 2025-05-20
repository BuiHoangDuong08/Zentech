package service;

import dao.UserDAO;
import entity.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }
public void updateUser(JTextField txt_ID, JTextField txtfullname, JTextField txtusername,
                       JTextField txtaddress, JTextField txtdob, JTextField txtemail,
                       JRadioButton rdomale, JRadioButton rdofemale, JRadioButton rdonone,
                       JTextField txtphone, JPasswordField txtpassword) {
    try {
        User user = new User();
        
txt_ID.setEnabled(false);
        // Gán thông tin
        user.setFullName(txtfullname.getText().trim());
        user.setUserName(txtusername.getText().trim());
        user.setAddress(txtaddress.getText().trim());
        user.setEmail(txtemail.getText().trim());
        user.setPhoneNumber(txtphone.getText().trim());

        // Ngày sinh
        String dobText = txtdob.getText().trim();
        if (!dobText.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                sdf.setLenient(false);
                java.util.Date parsedDate = sdf.parse(dobText);
                user.setDob(new java.sql.Date(parsedDate.getTime()));
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date of birth. Please enter in DD-MM-YYYY format.");
                return;
            }
        } else {
            user.setDob(null);
        }

        // Giới tính
        String gender = "None";
        if (rdomale.isSelected()) gender = "Male";
        else if (rdofemale.isSelected()) gender = "Female";
        user.setGender(gender);

        // Mật khẩu
        String password = new String(txtpassword.getPassword()).trim();
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password cannot be blank.");
            return;
        }
        user.setPassword(password);

        // Xác nhận cập nhật
        int ret = JOptionPane.showConfirmDialog(null, "Are you sure you want to update information??", "Confirm update", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION) {
            boolean result = userDAO.updateUser(user);
            if (result) {
                JOptionPane.showMessageDialog(null, "Updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Update failed!");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error while updating: " + e.getMessage());
    }
}

}
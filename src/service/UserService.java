package service;

import dao.UserDAO;
import entity.SalesHistorymodel;
import entity.UserModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UserService implements UserDAO {

    public void loadUser(JTable table) {
        List<UserModel> list = getAllUsers();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Xoá dữ liệu cũ (nếu có)

        for (UserModel user : list) {
            model.addRow(new Object[]{
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getFullName(),
                user.getGender(),
                user.getAddress(),
                user.getDob(),
                user.getPhoneNumber()
            });
        }
    }

    public void updateUser(JTextField txt_ID, JTextField txtfullname, JTextField txtusername,
            JTextField txtaddress, JTextField txtdob, JTextField txtemail,
            JRadioButton rdomale, JRadioButton rdofemale, JRadioButton rdonone,
            JTextField txtphone, JPasswordField txtpassword) {
        try {
            UserModel user = new UserModel();

            // Lấy ID từ txt_ID để phục vụ cập nhật
            int userId;
            try {
                userId = Integer.parseInt(txt_ID.getText().trim());
                user.setId(userId);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid ID format.");
                return;
            }

            // Gán thông tin người dùng
            user.setFullName(txtfullname.getText().trim());
            user.setUserName(txtusername.getText().trim());
            user.setAddress(txtaddress.getText().trim());
            user.setEmail(txtemail.getText().trim());
            user.setPhoneNumber(txtphone.getText().trim());

            // Xử lý ngày sinh (YYYY-MM-DD)
            String dobText = txtdob.getText().trim();
            if (!dobText.isEmpty()) {
                try {
                    // Sử dụng định dạng YYYY-MM-DD
                    java.sql.Date dob = java.sql.Date.valueOf(dobText); // hợp lệ nếu đúng định dạng
                    user.setDob(dob);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Invalid date of birth. Please enter in YYYY-MM-DD format.");
                    return;
                }
            } else {
                user.setDob(null);
            }

            // Giới tính
            String gender = "None";
            if (rdomale.isSelected()) {
                gender = "Male";
            } else if (rdofemale.isSelected()) {
                gender = "Female";
            }
            user.setGender(gender);

            // Mật khẩu
            String password = new String(txtpassword.getPassword()).trim();
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Password cannot be blank.");
                return;
            }
            user.setPassword(password);

            // Xác nhận cập nhật
            int ret = JOptionPane.showConfirmDialog(null, "Are you sure you want to update information?", "Confirm update", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                boolean result = updateUser(user);
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

    public void showDetail(JTable table,
            JTextField fieldID,
            JTextField fieldUserName,
            JTextField fieldEmail,
            JTextField fieldFullName,
            JRadioButton radioMale,
            JRadioButton radioFemale,
            JRadioButton radioNone,
            JTextField fieldAddress,
            JTextField fieldDob,
            JTextField fieldPhoneNumber,
            JPasswordField fieldPassword) {

        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            // ID không cho sửa
            fieldID.setText(String.valueOf(table.getValueAt(selectedRow, 0)));

            // Các trường thông tin
            fieldUserName.setText(String.valueOf(table.getValueAt(selectedRow, 1)));
            fieldEmail.setText(String.valueOf(table.getValueAt(selectedRow, 2)));
            fieldFullName.setText(String.valueOf(table.getValueAt(selectedRow, 3)));

            // Xử lý giới tính
            String gender = String.valueOf(table.getValueAt(selectedRow, 4));
            if (gender.equalsIgnoreCase("Male")) {
                radioMale.setSelected(true);
            } else if (gender.equalsIgnoreCase("Female")) {
                radioFemale.setSelected(true);
            } else {
                radioNone.setSelected(true);
            }

            fieldAddress.setText(String.valueOf(table.getValueAt(selectedRow, 5)));
            fieldDob.setText(String.valueOf(table.getValueAt(selectedRow, 6)));
            fieldPhoneNumber.setText(String.valueOf(table.getValueAt(selectedRow, 7)));

            // Nếu bạn có mật khẩu trong bảng (ví dụ cột 8)
            // fieldPassword.setText(String.valueOf(table.getValueAt(selectedRow, 8)));
        }
    }

}

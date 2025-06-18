package service;

import dao.RoleDAO;
import dao.UserDAO;
import entity.UserModel;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import raven.toast.Notifications;

public class UserService implements UserDAO {

    private final RoleDAO roleDAO = new RoleDAO();

    private UserModel currentUser;

    public void setCurrentUser(UserModel user) {
        this.currentUser = user;
    }
    
    public String getSelectedUserRole(JTable table) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) return null;

    // Giả sử cột role là cột số 1
    return table.getValueAt(selectedRow, 1).toString().trim();
}
    
public void loadAllowedUpgradeRoles(JComboBox<String> cborole, String selectedUserRole) {
    cborole.removeAllItems();

    if ("Admin".equalsIgnoreCase(selectedUserRole)) {
        cborole.addItem("ADMIN");
        cborole.addItem("MANAGER");
        cborole.addItem("CASHIER");
    } else if ("Quản lý".equalsIgnoreCase(selectedUserRole)) {
        cborole.addItem("MANAGER");
        cborole.addItem("CASHIER");
    } else {
        cborole.addItem("CASHIER");
    }
}

    public String getCurrentUserRole() {
        return roleDAO.getRoleNameById(currentUser.getRoleId());
    }

    public void loadUser(JTable table) {
        List<UserModel> list = getAllUsers();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (UserModel user : list) {
            String roleName = roleDAO.getRoleNameById(user.getRoleId());
            model.addRow(new Object[]{
                user.getId(),
                roleName,
                user.getUserName(),
                user.getEmail(),
                user.getFullName(),
                user.getGender(),
                user.getAddress(),
                user.getDob(),
                user.getPhoneNumber(),});
        }
    }

    public void updateUser(JTextField txt_ID, JTextField txtfullname, JTextField txtusername,
            JTextField txtaddress, JTextField txtdob, JTextField txtemail,
            JRadioButton rdomale, JRadioButton rdofemale,
            JTextField txtphone, JComboBox<String> cborole) {

        try {
            UserModel user = new UserModel();

            int userId;
            try {
                userId = Integer.parseInt(txt_ID.getText().trim());
                user.setId(userId);
            } catch (NumberFormatException e) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Invalid ID format");
                return;
            }

            user.setFullName(txtfullname.getText().trim());
            user.setUserName(txtusername.getText().trim());
            user.setAddress(txtaddress.getText().trim());
            user.setEmail(txtemail.getText().trim());
            user.setPhoneNumber(txtphone.getText().trim());

            String dobText = txtdob.getText().trim();
            if (!dobText.isEmpty()) {
                try {
                    java.sql.Date dob = java.sql.Date.valueOf(dobText);
                    user.setDob(dob);
                } catch (IllegalArgumentException e) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Invalid date of birth. Use format YYYY-MM-DD");
                    return;
                }
            } else {
                user.setDob(null);
            }

            String gender = rdomale.isSelected() ? "Male" : rdofemale.isSelected() ? "Female" : "None";
            user.setGender(gender);
//
//            String password = new String(txtpassword.getPassword()).trim();
//            if (password.isEmpty()) {
//                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Password cannot be blank");
//                return;
//            }
//            user.setPassword(password);

            String roleName = cborole.getSelectedItem().toString();
            int roleId = roleDAO.getRoleIdByName(roleName);
            user.setRoleId(roleId);

            int ret = JOptionPane.showConfirmDialog(null, "Are you sure you want to update information?", "Confirm update", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                boolean result = updateUser(user);
                if (result) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Updated successfully!");
                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Update failed!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Error while updating");
        }
    }

public void showDetail(JTable table,
        JTextField fieldID,
        JTextField fieldUserName,
        JTextField fieldEmail,
        JTextField fieldFullName,
        JRadioButton radioMale,
        JRadioButton radioFemale,
        JTextField fieldAddress,
        JTextField fieldDob,
        JTextField fieldPhoneNumber,
        JComboBox<String> cborole) {

    int selectedRow = table.getSelectedRow();

    if (selectedRow != -1) {
        String id = String.valueOf(table.getValueAt(selectedRow, 0));
        String role = String.valueOf(table.getValueAt(selectedRow, 1));
        String username = String.valueOf(table.getValueAt(selectedRow, 2));
        String email = String.valueOf(table.getValueAt(selectedRow, 3));
        String fullName = String.valueOf(table.getValueAt(selectedRow, 4));
        String gender = String.valueOf(table.getValueAt(selectedRow, 5));
        String address = String.valueOf(table.getValueAt(selectedRow, 6));
        String dob = String.valueOf(table.getValueAt(selectedRow, 7));
        String phoneNumber = String.valueOf(table.getValueAt(selectedRow, 8));

        fieldID.setText(id);
        fieldUserName.setText(username);
        fieldEmail.setText(email);
        fieldFullName.setText(fullName);
        radioMale.setSelected("Male".equalsIgnoreCase(gender));
        radioFemale.setSelected("Female".equalsIgnoreCase(gender));
        fieldAddress.setText(address);
        fieldDob.setText(dob);
        fieldPhoneNumber.setText(phoneNumber);

    }
}

}
package service;

import dao.RoleDAO;
import dao.UserDAO;
import entity.UserModel;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
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
        if (selectedRow == -1) {
            return null;
        }
        return table.getValueAt(selectedRow, 1).toString().trim();
    }

    public void loadAllowedUpgradeRoles(JComboBox<String> cborole, String currentUserRole) {
        if ("ADMIN".equalsIgnoreCase(currentUserRole)) {
            cborole.addItem("ADMIN");
            cborole.addItem("MANAGER");
            cborole.addItem("CASHIER");
        } else if ("MANAGER".equalsIgnoreCase(currentUserRole)) {
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

    public boolean canEditRole(String currentUserRole, String selectedUserRole) {
        if ("ADMIN".equalsIgnoreCase(currentUserRole)) {
            return true;
        } else if ("MANAGER".equalsIgnoreCase(currentUserRole)) {
            return "CASHIER".equalsIgnoreCase(selectedUserRole);
        }
        return false;
    }

    public void updateUser(JTextField txt_ID, JTextField txtfullname, JTextField txtusername,
        JTextField txtaddress, JTextField txtdob, JTextField txtemail,
        JRadioButton rdomale, JRadioButton rdofemale,
        JTextField txtphone, JComboBox<String> cborole, JTable jTable1) {

        try {
            UserModel user = new UserModel();

            int userId = Integer.parseInt(txt_ID.getText().trim());
            user.setId(userId);

            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Please select a user");
                return;
            }

            String selectedUserRole = jTable1.getValueAt(selectedRow, 1).toString().trim();
            String currentUserRole = getCurrentUserRole();

            if (!"ADMIN".equalsIgnoreCase(currentUserRole) && "ADMIN".equalsIgnoreCase(selectedUserRole)) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "You are not allowed to modify Admin accounts!");
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
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Invalid date format (YYYY-MM-DD)");
                    return;
                }
            } else {
                user.setDob(null);
            }

            String gender = rdomale.isSelected() ? "Male" : rdofemale.isSelected() ? "Female" : "None";
            user.setGender(gender);

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this user?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            // Xử lý gán quyền
            if ("ADMIN".equalsIgnoreCase(currentUserRole)) {
                String selectedRoleName = cborole.getSelectedItem().toString();
                user.setRoleId(roleDAO.getRoleIdByName(selectedRoleName));
            } else if ("MANAGER".equalsIgnoreCase(currentUserRole)) {
                String selectedRoleName = cborole.getSelectedItem().toString();
                if ("ADMIN".equalsIgnoreCase(selectedRoleName)) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "You cannot assign ADMIN role!");
                    return;
                }
                user.setRoleId(roleDAO.getRoleIdByName(selectedRoleName));
            } else {
                user.setRoleId(roleDAO.getRoleIdByName(selectedUserRole));
            }

            boolean result = updateUser(user);
            if (result) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "User updated successfully!");
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Failed to update user!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "System error occurred!");
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
        if (selectedRow == -1) return;

        String id = String.valueOf(table.getValueAt(selectedRow, 0));
        String selectedUserRole = String.valueOf(table.getValueAt(selectedRow, 1));
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

        String currentUserRole = getCurrentUserRole();
        boolean allowEdit = false;
        boolean allowEditRole = canEditRole(currentUserRole, selectedUserRole);

        if ("ADMIN".equalsIgnoreCase(currentUserRole)) {
            allowEdit = true;
        } else if ("MANAGER".equalsIgnoreCase(currentUserRole) && "CASHIER".equalsIgnoreCase(selectedUserRole)) {
            allowEdit = true;
        }

        fieldUserName.setEnabled(allowEdit);
        fieldEmail.setEnabled(allowEdit);
        fieldFullName.setEnabled(allowEdit);
        radioMale.setEnabled(allowEdit);
        radioFemale.setEnabled(allowEdit);
        fieldAddress.setEnabled(allowEdit);
        fieldDob.setEnabled(allowEdit);
        fieldPhoneNumber.setEnabled(allowEdit);

        cborole.removeAllItems(); // luôn xoá trước

        if (allowEditRole) {
            loadAllowedUpgradeRoles(cborole, currentUserRole);
        } else {
            cborole.addItem(selectedUserRole);
        }

        for (int i = 0; i < cborole.getItemCount(); i++) {
            if (cborole.getItemAt(i).equalsIgnoreCase(selectedUserRole)) {
                cborole.setSelectedIndex(i);
                break;
            }
        }

        cborole.setEnabled(allowEditRole);
    }
}

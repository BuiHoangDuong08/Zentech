/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package zentech.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import dao.UserDAO;
import java.awt.Dimension;
import net.miginfocom.swing.MigLayout;
import service.ChangePassword_service;
import zentech.application.Application;

/**
 *
 * @author duong
 */
public class ChangePasswordForm1 extends javax.swing.JFrame {

    ChangePassword_service csv = new ChangePassword_service();
    UserDAO ud = new UserDAO() {
    };

    public ChangePasswordForm1() {
        initComponents();
        init();
        setSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);
    }

    private void init() {
        setLayout(new MigLayout("al center center"));
        txtUserName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "User name");
        txtoldpass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Old Password");
        txtNewpass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "New Password");
        txtCnfrimpass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Confirm Password");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin1 = new zentech.application.form.PanelLogin();
        jButton2 = new javax.swing.JButton();
        lbTitle = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        lbUser = new javax.swing.JLabel();
        txtoldpass = new javax.swing.JTextField();
        lbPass = new javax.swing.JLabel();
        txtNewpass = new javax.swing.JPasswordField();
        txtCnfrimpass = new javax.swing.JPasswordField();
        cmdChangePassword = new javax.swing.JButton();

        jButton2.setText("X");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Change Password");

        jLabel1.setText("User name");

        lbUser.setText("Old Password");

        lbPass.setText("New Password");

        txtCnfrimpass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCnfrimpassActionPerformed(evt);
            }
        });

        cmdChangePassword.setText("Change Password");
        cmdChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdChangePasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLogin1Layout = new javax.swing.GroupLayout(panelLogin1);
        panelLogin1.setLayout(panelLogin1Layout);
        panelLogin1Layout.setHorizontalGroup(
            panelLogin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogin1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addGap(11, 11, 11)
                .addGroup(panelLogin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtoldpass, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbPass, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNewpass, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCnfrimpass, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdChangePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        panelLogin1Layout.setVerticalGroup(
            panelLogin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogin1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLogin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addGap(15, 15, 15)
                .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(lbUser)
                .addGap(30, 30, 30)
                .addComponent(txtoldpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lbPass)
                .addGap(30, 30, 30)
                .addComponent(txtNewpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(txtCnfrimpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(cmdChangePassword)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(201, Short.MAX_VALUE)
                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
        Application.logout();
        txtCnfrimpass.setText("");
        txtNewpass.setText("");
        txtUserName.setText("");
        txtoldpass.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtCnfrimpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCnfrimpassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCnfrimpassActionPerformed

    private void cmdChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdChangePasswordActionPerformed
        // TODO add your handling code here:
        csv.getChangePassword(txtUserName, txtoldpass, txtNewpass, txtCnfrimpass);
    }//GEN-LAST:event_cmdChangePasswordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdChangePassword;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbPass;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUser;
    private zentech.application.form.PanelLogin panelLogin1;
    private javax.swing.JPasswordField txtCnfrimpass;
    private javax.swing.JPasswordField txtNewpass;
    private javax.swing.JTextField txtUserName;
    private javax.swing.JTextField txtoldpass;
    // End of variables declaration//GEN-END:variables
}

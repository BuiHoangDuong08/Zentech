package zentech.application;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import dao.ActivityDAO;
import entity.Activity;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import zentech.application.form.LoginForm;
import zentech.application.form.MainForm;
import raven.toast.Notifications;
import zentech.application.form.ChangePasswordForm;
import zentech.application.form.RegisterFrom;

public class Application extends javax.swing.JFrame {

    private static Application app;
    private final MainForm mainForm;
    private final LoginForm loginForm;
    private final ChangePasswordForm changePasswordForm;
    private final RegisterFrom registerFrom;
    private String currentUser;
    
    public static Application getAppInstance(){
        return app;
    }
    
    public void setCurrentUser(String user){
        this.currentUser = user;
    }
    
    public String getCurrentUser(){
        return this.currentUser;
    }

    public Application() {
        initComponents();
        setSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);
        mainForm = new MainForm();
        loginForm = new LoginForm();
        changePasswordForm = new ChangePasswordForm();
        registerFrom = new RegisterFrom();
        setContentPane(loginForm);
        getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
        Notifications.getInstance().setJFrame(this);
        
        this.addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent e){
                String user = getCurrentUser();
                if(user != null && !user.isEmpty()){
                    try{
                        ActivityDAO.insert(new Activity(user, "LOGOUT", LocalDateTime.now()));
                    }catch(SQLException ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public static void showForm(Component component) {
        component.applyComponentOrientation(app.getComponentOrientation());
        app.mainForm.showForm(component);
    }

    public static void changePassword() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.changePasswordForm);
        app.changePasswordForm.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.changePasswordForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void login() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.mainForm);
        app.mainForm.applyComponentOrientation(app.getComponentOrientation());
        setSelectedMenu(0, 0);
        app.mainForm.hideMenu();
        SwingUtilities.updateComponentTreeUI(app.mainForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void registerFrom() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.registerFrom);
        app.registerFrom.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.registerFrom);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void logout() {
        String user = app.getCurrentUser();
        if(user != null && !user.isEmpty()){
            try{
                ActivityDAO.insert(new Activity(user, "LOGOUT", LocalDateTime.now()));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        app.setCurrentUser(null);
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.loginForm);
        app.loginForm.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.loginForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void setSelectedMenu(int index, int subIndex) {
        app.mainForm.setSelectedMenu(index, subIndex);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("zentech.theme");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
            app = new Application();
            //  app.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            app.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

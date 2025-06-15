package zentech.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import entity.UserModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import zentech.application.Application;
import zentech.application.changepassword.ChangePassword;
import zentech.application.changepassword.ForgotPassword;
import zentech.application.form.other.AboutUs;
import zentech.application.form.other.ActivityLogForm;
import zentech.application.form.other.Inventory;
import zentech.application.form.other.ListIDcard;
import zentech.application.form.other.MenuSelection;
import zentech.application.form.other.SalesHistory;
import zentech.application.form.other.User;
import zentechx.menu.Menu;
import zentechx.menu.MenuAction;

public class MainForm extends JLayeredPane {

    UserModel usm;

    public MainForm(UserModel usm) {
        this.usm = usm;
        init(usm);
    }

    private void init(UserModel usm) {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new MainFormLayout());
        menu = new Menu(usm);
        panelBody = new JPanel(new BorderLayout());
        initMenuArrowIcon();
        menuButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.button.background;"
                + "arc:999;"
                + "focusWidth:0;"
                + "borderWidth:0");
        menuButton.addActionListener((ActionEvent e) -> {
            setMenuFull(!menu.isMenuFull());
        });
        initMenuEvent();
        setLayer(menuButton, JLayeredPane.POPUP_LAYER);
        add(menuButton);
        add(menu);
        add(panelBody);
    }

    @Override
    public void applyComponentOrientation(ComponentOrientation o) {
        super.applyComponentOrientation(o);
        initMenuArrowIcon();
    }

    private void initMenuArrowIcon() {
        if (menuButton == null) {
            menuButton = new JButton();
        }
        String icon = (getComponentOrientation().isLeftToRight()) ? "menu_left.svg" : "menu_right.svg";
        menuButton.setIcon(new FlatSVGIcon("zentech/icon/svg/" + icon, 0.8f));
    }

    private void initMenuEvent() {
        int id = this.usm.getRoleId();
        menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
            System.out.println(index);
            if (index == 0) {
                Application.showForm(new MenuSelection(this.usm));
            } else if (index == 1) {
                if (id == 3) {
                    ForgotPassword c = new ForgotPassword();
                    c.setVisible(true);
                } else {
                    Application.showForm(new ListIDcard());
                }
            } else if (index == 2) {
                if (id == 3) {
                    Application.logout();
                } else {
                    Application.showForm(new Inventory());
                }
            } else if (index == 3) {
                if (id == 3) {
                    Application.showForm(new AboutUs());
                } else {
                    Application.showForm(new User(this.usm));
                }
            } else if (index == 4) {
                Application.showForm(new SalesHistory());
            } else if (index == 5) {
                if (id == 2) {
                    ForgotPassword c = new ForgotPassword();
                    c.setVisible(true);
                } else {
                    Application.showForm(new ActivityLogForm());
                }
            } else if (index == 6) {
                if (id == 2) {
                    Application.logout();
                } else {
                    ForgotPassword c = new ForgotPassword();
                    c.setVisible(true);
                }
            } else if (index == 7) {
                if (id == 2) {
                    Application.showForm(new AboutUs());
                } else {
                    Application.logout();
                }
            } else if (index == 8) {
                Application.showForm(new AboutUs());
            } else {
                action.cancel();
            }
        });
    }

    public boolean message() {
        int id = this.usm.getRoleId();
        if (id == 2) {
            JOptionPane.showMessageDialog(null, "Bạn không có quyền sài chức năng");
            return false;
        } else {
            return true;
        }
    }

    private void setMenuFull(boolean full) {
        String icon;
        if (getComponentOrientation().isLeftToRight()) {
            icon = (full) ? "menu_left.svg" : "menu_right.svg";
        } else {
            icon = (full) ? "menu_right.svg" : "menu_left.svg";
        }
        menuButton.setIcon(new FlatSVGIcon("zentech/icon/svg/" + icon, 0.8f));
        menu.setMenuFull(full);
        revalidate();
    }

    public void hideMenu() {
        menu.hideMenuItem();
    }

    public void showForm(Component component) {
        panelBody.removeAll();
        panelBody.add(component);
        panelBody.repaint();
        panelBody.revalidate();
    }

    public void setSelectedMenu(int index, int subIndex) {
        menu.setSelectedMenu(index, subIndex);
    }

    private Menu menu;
    private JPanel panelBody;
    private JButton menuButton;

    private class MainFormLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(5, 5);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                boolean ltr = parent.getComponentOrientation().isLeftToRight();
                Insets insets = UIScale.scale(parent.getInsets());
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
                int menuX = ltr ? x : x + width - menuWidth;
                menu.setBounds(menuX, y, menuWidth, height);
                int menuButtonWidth = menuButton.getPreferredSize().width;
                int menuButtonHeight = menuButton.getPreferredSize().height;
                int menubX;
                if (ltr) {
                    menubX = (int) (x + menuWidth - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.3f)));
                } else {
                    menubX = (int) (menuX - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.7f)));
                }
                menuButton.setBounds(menubX, UIScale.scale(30), menuButtonWidth, menuButtonHeight);
                int gap = UIScale.scale(5);
                int bodyWidth = width - menuWidth - gap;
                int bodyHeight = height;
                int bodyx = ltr ? (x + menuWidth + gap) : x;
                int bodyy = y;
                panelBody.setBounds(bodyx, bodyy, bodyWidth, bodyHeight);
            }
        }
    }
}

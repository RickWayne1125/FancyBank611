package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login extends AbstractJPanel {
    private JPanel basePanel;
    private JButton submitButton;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public login() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                Boolean success = api.customerLogin(username, password);
                if (success) {
                    root.getRoot().next(ViewFactory.getMenuPage());
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}

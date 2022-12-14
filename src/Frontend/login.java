package Frontend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import API.Controller;
import Person.Customer.Customer;

public class login extends AbstractJPanel {
    private JPanel basePanel;
    private JButton submitButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signUpButton;

    public login() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();


                Customer customer = Controller.loginCustomer(username, password);

                if (customer != null) {
                    Frontend.getInstance().setUser(customer);
                    Frontend.getInstance().setUserType("customer");
                    Frontend.getInstance().next(ViewFactory.getMenuPage());
                } else {
                    utils.showNotice("Invalid username or password");
                }
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getCreateEditUserView("create", "customer"));
            }
        });
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}

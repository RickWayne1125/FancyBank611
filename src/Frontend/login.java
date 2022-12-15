package Frontend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import API.Controller;
import Person.Customer.Customer;
import Person.Person;

public class login extends AbstractJPanel {
    private JPanel basePanel;
    private JButton submitButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signUpButton;
    private JRadioButton isManagerLogin;

    public login() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                Person person = login(username, password);

                if (person != null) {
                    Frontend.getInstance().setUser(person);
                    if(isManagerLogin.isSelected()){
                        Frontend.getInstance().setUserType("manager");
                        Frontend.getInstance().next(ViewFactory.getManagerMenuPage());
                    } else {
                        Frontend.getInstance().setUserType("customer");
                        Frontend.getInstance().next(ViewFactory.getCustomerMenuPage());
                    }
                } else {
                    Frontend.getInstance().setUser(null);
                    Frontend.getInstance().setUserType(null);
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

    public Person login(String username, String password) {
        if(isManagerLogin.isSelected()){
            return Controller.loginManager(username, password);
        } else {
            return Controller.loginCustomer(username, password);
        }
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}

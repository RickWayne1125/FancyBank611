package Frontend;

import Person.Person;
import Utils.Helpers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewUserForm extends AbstractJPanel{
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField middleNameField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField emailField;
    private JTextField contactField;
    private JTextField addressField;
    private JLabel title;
    private JButton submitButton;
    private JButton cancelButton;
    private JPanel basePanel;

    public NewUserForm(String type, String userType) {
        if (type == "create"){
            title.setText("Create new "+userType);
        } else if (type == "edit"){
            title.setText("Edit Details");
            usernameField.setEditable(false);
            emailField.setEditable(false);
            this.prefillForm();
        }
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String firstName = firstNameField.getText();
                String middleName = middleNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String contact = contactField.getText();
                String address = addressField.getText();

                if (type.equals("create")){
                    if(create(userType, username, firstName, middleName, lastName, email, password, contact, address)){
                        utils.showNotice("User Created.");
                        Frontend.getInstance().back();
                    } else {
                        utils.showNotice("Invalid data.");
                    }
                } else if (type.equals("edit")){
                    if(edit(firstName, middleName, lastName, email, password, contact, address)){
                        utils.showNotice("User Edited.");
                    } else {
                        utils.showNotice("Invalid data.");
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
    }

    public void prefillForm(){
        Person person = Frontend.getInstance().getUser();
        usernameField.setText(person.getUsername());
        firstNameField.setText(person.getFirstName());
        middleNameField.setText(person.getMiddleName());
        lastNameField.setText(person.getLastName());
        emailField.setText(person.getEmail());
        passwordField.setText(person.getPassword());
        contactField.setText(person.getContact());
        addressField.setText(person.getAddress());
    }

    public boolean create(String userType, String username, String firstName, String middleName, String lastName, String email, String password, String contact, String address){
        return Helpers.createUser(userType, username, firstName, middleName, lastName, email, password, contact, address);
    }
    public boolean edit(String firstName, String middleName, String lastName, String email, String password, String contact, String address){
        return Helpers.editUser(firstName, middleName, lastName, email, password, contact, address, Frontend.getInstance().getUser());
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}

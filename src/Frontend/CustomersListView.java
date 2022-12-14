package Frontend;

import API.Controller;
import Person.Customer.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class CustomersListView extends AbstractJPanel {
    private JButton backButton;
    private JPanel basePanel;
    private JPanel customersListPanel;
    private JTextField searchField;

    private List<JPanel> customerPanels;
    private List<Customer> customers;

    public CustomersListView() {
        customersListPanel.setLayout(new BoxLayout(customersListPanel, BoxLayout.Y_AXIS));
        customerPanels = new ArrayList<>();
        this.showCustomers("");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String typed = searchField.getText();
                showCustomers(typed);
            }
        });
    }



    private void showCustomers(String search){
        customerPanels.clear();
        customers = Controller.getAllCustomer();

        for (Customer customer: customers){
            String name = customer.getFirstName()+customer.getMiddleName()+customer.getLastName();
            if(search.equals("") || name.toLowerCase().contains(search.toLowerCase())) {
                customerPanels.add((new CustomerView(customer)).getBasePanel());
            }
        }
        this.refresh();
    }
    private void refresh(){
        customersListPanel.removeAll();
        for (JPanel panel:customerPanels){
            customersListPanel.add(panel);
        }
        Frontend.getInstance().render();
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}

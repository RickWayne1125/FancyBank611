package Frontend;

import API.Controller;
import Person.Customer.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CustomerView extends AbstractJPanel{
    private JLabel name;
    private JPanel basePanel;
    private JButton viewAccountsButton;
    private JRadioButton hasCollateralRadioButton;

    public CustomerView(Customer customer){
        this.name.setText(customer.getFirstName()+" "+customer.getLastName());
        this.hasCollateralRadioButton.setSelected(customer.getHasCollateral());
        this.setVisible(true);
        hasCollateralRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                customer.setHasCollateral(hasCollateralRadioButton.isSelected());
                Controller.setHasCollateral(customer, hasCollateralRadioButton.isSelected());
            }
        });
        viewAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getCustomerDetailView(customer));
            }
        });
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}

package Frontend;

import Account.AccountType;
import Person.Customer.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDetailView extends AbstractJPanel{
    private JButton backButton;
    private JPanel basePanel;
    private JTabbedPane tabbedPane1;
    private JPanel savingsAccountPanel;
    private JPanel checkingAccountPanel;
    private Customer customer;

    public CustomerDetailView(Customer customer) {
        this.customer = customer;
        savingsAccountPanel.setLayout(new java.awt.BorderLayout());
        checkingAccountPanel.setLayout(new java.awt.BorderLayout());

        refresh();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
    }

    public void refresh(){
        savingsAccountPanel.removeAll();
        savingsAccountPanel.add(ViewFactory.getAccount(this.customer, AccountType.SAVING, true).getBasePanel());
        checkingAccountPanel.add(ViewFactory.getAccount(this.customer, AccountType.CHECKING, true).getBasePanel());
    }


    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}

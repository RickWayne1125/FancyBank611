package Frontend;

import Account.Account;
import Account.AccountType;
import Person.Customer.Customer;
import Utils.Helpers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDetailView extends AbstractJPanel{
    private JButton backButton;
    private JPanel basePanel;
    private JTabbedPane tabbedPane1;
    private JPanel savingsAccountPanel;
    private JPanel checkingAccountPanel;
    private JPanel loanAccountPanel;
    private Customer customer;

    public CustomerDetailView(Customer customer) {
        this.customer = customer;
        savingsAccountPanel.setLayout(new java.awt.BorderLayout());
        checkingAccountPanel.setLayout(new java.awt.BorderLayout());
        loanAccountPanel.setLayout(new java.awt.BorderLayout());

        refresh();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
    }

    public void refresh(){
        Account savingsAcc = Helpers.getAccount(AccountType.SAVING, customer.getAccounts());
        if(savingsAcc != null){
            savingsAccountPanel.removeAll();
            savingsAccountPanel.add(ViewFactory.getAccount(this.customer, AccountType.SAVING, savingsAcc, true, false).getBasePanel());
        }

        Account checkingAcc = Helpers.getAccount(AccountType.CHECKING, customer.getAccounts());
        if(checkingAcc != null){
            checkingAccountPanel.removeAll();
            checkingAccountPanel.add(ViewFactory.getAccount(this.customer, AccountType.CHECKING, checkingAcc, true, false).getBasePanel());
        }
        loanAccountPanel.add(ViewFactory.getLoansView(true, this.customer, true).getBasePanel());
    }


    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}

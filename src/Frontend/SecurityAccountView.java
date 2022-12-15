package Frontend;

import API.Controller;
import Account.AccountType;
import Account.Security.SecurityAccount;
import Person.Customer.Customer;
import Utils.Helpers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecurityAccountView extends AbstractJPanel{
    private JButton backButton;
    private JPanel basePanel;
    private JTable accountDetails;
    private JTabbedPane tabbedPane1;
    private JPanel allStocksPanel;
    private JPanel ownedStocksPanel;
    private SecurityAccount account;


    public SecurityAccountView() {
        Customer customer =  (Customer) Frontend.getInstance().getUser();
        this.account = (SecurityAccount) Helpers.getAccount(AccountType.SECURITY, customer.getAccounts());
        if (this.account == null) {
            this.account = (SecurityAccount) Helpers.createNewAccount(AccountType.SECURITY);
            Controller.openAccount(customer, account);
            utils.showNotice("New "+Helpers.getAccountTypeString(AccountType.SECURITY)+" account created!");
        }


        this.refresh();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
    }

    public void refresh(){}

    public void setSecurityAccountDetails(){
        String[] columns = new String[] {
                "", ""
        };
        //actual data for the table in a 2d array
        Object[][] data = new Object[][] {
                {"Account Number", this.account.getAccountNumber() },
                {"Routing Number", this.account.getRoutingNumber() },
                {"Swift Code", this.account.getSwiftCode() },
                {"Current Balance", this.account.getCurrentBalance()},
                {"Un-realized profit", this.account.getUnrealized()},
                {"Realized profit", this.account.getRealized()}
            };

        accountDetails.setModel(utils.getTableModel(data, columns));
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}

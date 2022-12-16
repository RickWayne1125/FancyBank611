package Frontend;

import API.Controller;
import Account.Account;
import Account.AccountType;
import Money.Currency;
import Money.Money;
import Person.Customer.Customer;
import Utils.Helpers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class MoneyTransferView extends AbstractJPanel{
    private JButton backButton;
    private JPanel basePanel;
    private JComboBox customerAccountsList;
    private JTabbedPane recipientPane;
    private JComboBox recipientAccountsList;
    private JTextField amountField;
    private JComboBox currencyField;
    private JButton transferButton;
    private JTextField recipientAccountNumberField;
    Customer customer;
    List<Account> customerAccounts;
    Account selectedCustomerAccount;

    List<Account> allCustomerAccounts;
    Account selectedCustomerSecondAccount;
    private List<Currency> currencies;
    private Currency seletedCurrency;

    public MoneyTransferView() {
        this.currencies = Controller.getAllCurrency();
        this.seletedCurrency = this.currencies.get(0);
        this.customer = (Customer) Frontend.getInstance().getUser();
        loadCurrenciesDropdown();
        loadCustomerAccounts();
        refresh();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
        customerAccountsList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getSource() == customerAccountsList){
                    selectedCustomerAccount = customerAccounts.get(customerAccountsList.getSelectedIndex());
                }
            }
        });
        recipientAccountsList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getSource() == recipientAccountsList){
                    selectedCustomerSecondAccount = allCustomerAccounts.get(recipientAccountsList.getSelectedIndex());
                }
            }
        });
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amount = amountField.getText();
                try{
                    int amt = Integer.parseInt(amount);
                    if(recipientPane.getSelectedIndex()==0){
                        Helpers.transfer(selectedCustomerAccount, selectedCustomerSecondAccount, new Money(amt, currencies.get(currencyField.getSelectedIndex())));
                    } else if (recipientPane.getSelectedIndex()==1){
                        Account account = fetchAccount();
                        Helpers.transfer(selectedCustomerAccount, account, new Money(amt, currencies.get(currencyField.getSelectedIndex())));
                    }

                }catch (Exception ex){
                    utils.showNotice("ERROR");
                }
            }
        });
    }

    public void refresh(){
    }
    public void loadCurrenciesDropdown(){
        this.currencyField.removeAllItems();
        for(Currency curr:this.currencies){
            this.currencyField.addItem(curr.toString());
        }
    }

    public Account fetchAccount(){
        String acnum = recipientAccountNumberField.getText();
        if(acnum!=null){
            try{
                int num = Integer.parseInt(acnum);
                return Controller.getAccountByAccountNumber(num);
            }catch (Exception e){

            }
        }
        utils.showNotice("Invalid Account Number");
        return null;
    }
    public void loadCustomerAccounts(){
        List<Account> accounts = this.customer.getAccounts();
        List<Account> filteredAccounts = new ArrayList<>();
        for(int i = 0; accounts!=null && i<accounts.size();i++){
            if(!accounts.get(i).getType().equals(AccountType.LOAN)){
                filteredAccounts.add((accounts.get(i)));
            }
        }
        this.allCustomerAccounts = accounts;
        this.customerAccounts = filteredAccounts;
        if(filteredAccounts.size()>0) {
            this.selectedCustomerAccount = filteredAccounts.get(0);
        }
        if(accounts!=null && accounts.size()>0){
            this.selectedCustomerSecondAccount = accounts.get(0);
        }

        customerAccountsList.removeAll();
        for(int i = 0;i < customerAccounts.size(); i ++){
            Account acc = customerAccounts.get(i);
            customerAccountsList.addItem(acc.getType() + " " + acc.getAccountNumber());
        }

        recipientAccountsList.removeAll();
        for(int i = 0;i < allCustomerAccounts.size(); i ++){
            Account acc = allCustomerAccounts.get(i);
            recipientAccountsList.addItem(acc.getType() + " " + acc.getAccountNumber());
        }
    }


    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}

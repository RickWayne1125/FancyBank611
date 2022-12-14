package Frontend;

import API.Controller;
import Account.Account;
import Account.AccountType;
import Money.Currency;
import Money.Money;
import Person.Customer.Customer;
import Transact.Transaction;
import Transact.TransactionStatus;
import Transact.TransactionType;
import Utils.Helpers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class AccountView extends AbstractJPanel{
    private JLabel accountLabel;
    private JPanel basePanel;
    private JButton backButton;
    private JComboBox currencyType;
    private JTable accountDetails;
    private JTable transactionsView;
    private JTextField depositField;
    private JButton depositButton;
    private JTextField withdrawField;
    private JButton withdrawButton;

    private Account account;
    private Customer customer;
    private List<Currency> currencies;
    private Currency seletedCurrency;

    public AccountView(AccountType accountType){
        accountLabel.setText(accountType.name());


        this.currencies = Controller.getAllCurrency();
        this.seletedCurrency = this.currencies.get(0);

        this.loadCurrenciesDropdown();
        this.customer =  (Customer) Frontend.getInstance().getUser();
        this.account = Helpers.getAccount(accountType, customer.getAccounts());
        if (this.account == null) {
            this.account = Helpers.createNewAccount(accountType);
            Controller.openAccount(this.customer, this.account);
            utils.showNotice("New "+Helpers.getAccountTypeString(accountType)+" account created!");
        }


        this.refresh();

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });

        currencyType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() == currencyType) {
                    seletedCurrency = currencies.get(currencyType.getSelectedIndex());
                    refresh();
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = depositField.getText();
                try {
                    double t = Double.parseDouble(text);
                    boolean success = Controller.deposit(account, new Money(t, seletedCurrency));
                    if (success) {
                        utils.showNotice(t + seletedCurrency.getCurrencyName() + " Deposited");
                        refresh();
                    } else {
                        utils.showNotice("Deposit Failed. Please contact support.");
                    }
                } catch (Exception ex){
                    utils.showNotice("Invalid value");
                    depositField.setText("");
                }
            }
        });
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = withdrawField.getText();
                try {
                    double t = Double.parseDouble(text);
                    boolean success = Controller.withdraw(account, new Money(t, seletedCurrency));
                    if (success) {
                        utils.showNotice(t + seletedCurrency.getCurrencyName() + " Withdrawn");
                        refresh();
                    } else {
                        utils.showNotice("Withdraw Failed.");
                    }
                } catch (Exception ex){
                    utils.showNotice("Invalid value");
                    depositField.setText("");
                }
            }
        });
    }

    public void refresh(){
        this.account = Controller.refreshAccount(this.account);
        List<Transaction> transactions = Controller.getTransactionHistory(this.account);
        List<Transaction> currencyTransactions = Helpers.filterTransactionsByCurrency(transactions, this.seletedCurrency);


        this.loadAccountDetails();
        System.out.println(transactions);
        System.out.println(currencyTransactions);
        this.loadTransactionDetails(currencyTransactions);
    }

    public void loadCurrenciesDropdown(){
        this.currencyType.removeAllItems();
        for(Currency curr:this.currencies){
            this.currencyType.addItem(curr.toString());
        }
    }
    public void loadAccountDetails(){
        String[] columns = new String[] {
                "", ""
        };
        //actual data for the table in a 2d array
        Object[][] data = new Object[][] {
                {"Account Number", this.account.getAccountNumber() },
                {"Routing Number", this.account.getRoutingNumber() },
                {"Swift Code", this.account.getSwiftCode() },
                {"Interest Rate", this.account.getInterestRate() + "%" },
                {"Current Balance", this.account.getCurrentBalance()}
        };
        accountDetails.setModel(utils.getTableModel(data, columns));
    }

    public void loadTransactionDetails(List<Transaction> transactions){
        transactionsView.removeAll();
        String[] columns = new String[] {
                "id", "status", "date", "from", "to", "amount", "balance"
        };
        Object[][] data = new Object[transactions.size()][5];
        double balance = 0;
        for (int i = 0; i<transactions.size(); i++){
            Transaction transaction = transactions.get(i);
            String from = ""+transaction.getFrom().getAccountNumber();
            String to = ""+transaction.getTo().getAccountNumber();
            double money = transaction.getMoney().getAmount();
            if (transaction.getTransactionType().equals(TransactionType.DEPOSIT)){
                from = null;
            }
            if (transaction.getTransactionType().equals(TransactionType.WITHDRAW)){
                to = null;
                money = money*-1;
            }
            if(transaction.getTransactionStatus().equals(TransactionStatus.SUCCESS)) {
                balance += money;
            }
            data[transactions.size()-i-1] = new Object[]{transaction.getId(), transaction.getTransactionStatus(),transaction.getDate().toString(), from, to, money, balance };
        }
        transactionsView.setModel(utils.getTableModel(data, columns));
        transactionsView.revalidate();
    }


    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}

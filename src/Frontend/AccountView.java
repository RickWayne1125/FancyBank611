package Frontend;

import API.Controller;
import Account.Account;
import Account.AccountType;
import Account.Loan.Loan;
import Money.Currency;
import Money.Money;
import Person.Customer.Customer;
import Transact.Transaction;
import Transact.TransactionStatus;
import Transact.TransactionType;
import Utils.Helpers;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class AccountView extends AbstractJPanel {
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
    private JPanel customerActionsPanel;
    private JButton closeAccountButton;

    private Account account;
    private Customer customer;
    private List<Currency> currencies;
    private Currency seletedCurrency;

    public AccountView(Customer customer, AccountType accountType, Account account, boolean managerView, boolean loanAccountsView) {

        accountLabel.setText(accountType.name());
        this.currencies = Controller.getAllCurrency();
        this.seletedCurrency = this.currencies.get(0);

        this.loadCurrenciesDropdown();
        this.customer = customer;
        if (managerView) {
            backButton.setVisible(false);
            customerActionsPanel.setVisible(false);
            closeAccountButton.setVisible(false);
        }
        if (loanAccountsView) {
            currencyType.setVisible(false);
            withdrawField.setVisible(false);
            withdrawButton.setVisible(false);
            closeAccountButton.setVisible(false);
        }
        if (account == null) {
            utils.showNotice("ERROR! Please contact support");
        } else {
            this.account = account;
            this.refresh();
        }

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
                    boolean success;
                    if (loanAccountsView) {
                        success = Controller.payLoanByCash((Loan) account, new Money(t, seletedCurrency));
                    } else {
                        success = Controller.deposit(account, new Money(t, seletedCurrency));
                    }
                    if (success) {
                        utils.showNotice(t + seletedCurrency.getCurrencyName() + " Deposited");
                        refresh();
                    } else {
                        utils.showNotice("Deposit Failed. Please contact support.");
                    }
                } catch (Exception ex) {
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
                } catch (Exception ex) {
                    utils.showNotice("Invalid value");
                    depositField.setText("");
                }
            }
        });
        closeAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Controller.closeAccount(customer, account)){
                    utils.showNotice("Account has been closed.");
                    Frontend.getInstance().back();
                } else {
                    utils.showNotice("ERROR. Please maintain a min balance of 10 to close the account");
                }
            }
        });
    }

    public void refresh() {
        this.account = Controller.refreshAccount(this.account);
        List<Transaction> transactions = Controller.getTransactionHistory(this.account);
        List<Transaction> currencyTransactions = Helpers.filterTransactionsByCurrency(transactions, this.seletedCurrency);


        this.loadAccountDetails();
        this.loadTransactionDetails(currencyTransactions);
    }

    public void loadCurrenciesDropdown() {
        this.currencyType.removeAllItems();
        for (Currency curr : this.currencies) {
            this.currencyType.addItem(curr.toString());
        }
    }

    public void loadAccountDetails() {
        String[] columns = new String[]{
                "", ""
        };
        //actual data for the table in a 2d array
        Object[][] data = new Object[][]{
                {"Account Number", this.account.getAccountNumber()},
                {"Routing Number", this.account.getRoutingNumber()},
                {"Swift Code", this.account.getSwiftCode()},
                {"Interest Rate", this.account.getInterestRate() + "%"},
                {"Current Balance", Helpers.getBalance(this.account.getCurrentBalance(), seletedCurrency)}
        };
        accountDetails.setModel(utils.getTableModel(data, columns));
    }

    public void loadTransactionDetails(List<Transaction> transactions) {
        transactionsView.removeAll();
        String[] columns = new String[]{
                "id", "type", "status", "date", "from", "to", "amount", "balance"
        };
        Object[][] data = new Object[transactions.size()][7];
        double balance = 0;
        for (int i = 0; transactions!=null && i<transactions.size(); i++){
            Transaction transaction = transactions.get(i);
            double money = transaction.getMoney().getAmount();
            String from = "";
            if(transaction.getFrom()!=null){
                from = ""+transaction.getFrom().getAccountNumber();
            }
            String to = "";
            if(transaction.getTo()!=null){
                to = ""+transaction.getTo().getAccountNumber();
            }
            if (transaction.getTransactionType().equals(TransactionType.DEPOSIT)) {
                from = null;
            }
            if (transaction.getTransactionType().equals(TransactionType.WITHDRAW)) {
                to = null;
                money*=-1;
            }
            if (transaction.getTransactionType().equals(TransactionType.SERVICE_FEE)) {
                money*=-1;
            }

            if (transaction.getTransactionType().equals(TransactionType.TRANSFER) && from.equals(""+account.getAccountNumber())) {
                money*=-1;
            }

            if (transaction.getTransactionStatus().equals(TransactionStatus.SUCCESS)) {
                balance += money;
            }
            data[transactions.size() - i - 1] = new Object[]{transaction.getId(), transaction.getTransactionType(),transaction.getTransactionStatus(), transaction.getDate().toString(), from, to, money, balance};
        }
        transactionsView.setModel(utils.getTableModel(data, columns));
        transactionsView.revalidate();
    }


    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        basePanel = new JPanel();
        basePanel.setLayout(new GridLayoutManager(4, 6, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        basePanel.add(panel1, new GridConstraints(0, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        currencyType = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("USD");
        defaultComboBoxModel1.addElement("INR");
        currencyType.setModel(defaultComboBoxModel1);
        panel1.add(currencyType, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        accountLabel = new JLabel();
        accountLabel.setText("xyz");
        panel1.add(accountLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Account");
        panel1.add(label1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setText("Back");
        panel1.add(backButton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        basePanel.add(panel2, new GridConstraints(1, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(null, "Account Details", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        accountDetails = new JTable();
        panel2.add(accountDetails, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        basePanel.add(scrollPane1, new GridConstraints(3, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        transactionsView = new JTable();
        scrollPane1.setViewportView(transactionsView);
        customerActionsPanel = new JPanel();
        customerActionsPanel.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        basePanel.add(customerActionsPanel, new GridConstraints(2, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        depositField = new JTextField();
        customerActionsPanel.add(depositField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        depositButton = new JButton();
        depositButton.setText("Deposit");
        customerActionsPanel.add(depositButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        withdrawField = new JTextField();
        withdrawField.setText("");
        customerActionsPanel.add(withdrawField, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        withdrawButton = new JButton();
        withdrawButton.setText("Withdraw");
        customerActionsPanel.add(withdrawButton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        customerActionsPanel.add(spacer3, new GridConstraints(0, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return basePanel;
    }

}

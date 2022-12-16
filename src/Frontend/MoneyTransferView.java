package Frontend;

import API.Controller;
import Account.Account;
import Account.AccountType;
import Money.Currency;
import Money.Money;
import Person.Customer.Customer;
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
import java.util.ArrayList;
import java.util.List;

public class MoneyTransferView extends AbstractJPanel {
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
                if (e.getSource() == customerAccountsList) {
                    selectedCustomerAccount = customerAccounts.get(customerAccountsList.getSelectedIndex());
                }
            }
        });
        recipientAccountsList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() == recipientAccountsList) {
                    selectedCustomerSecondAccount = allCustomerAccounts.get(recipientAccountsList.getSelectedIndex());
                }
            }
        });
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amount = amountField.getText();
                try {
                    int amt = Integer.parseInt(amount);
                    if (recipientPane.getSelectedIndex() == 0) {
                        Helpers.transfer(selectedCustomerAccount, selectedCustomerSecondAccount, new Money(amt, currencies.get(currencyField.getSelectedIndex())));
                    } else if (recipientPane.getSelectedIndex() == 1) {
                        Account account = fetchAccount();
                        Helpers.transfer(selectedCustomerAccount, account, new Money(amt, currencies.get(currencyField.getSelectedIndex())));
                    }

                } catch (Exception ex) {
                    utils.showNotice("ERROR");
                }
            }
        });
    }

    public void refresh() {
    }

    public void loadCurrenciesDropdown() {
        this.currencyField.removeAllItems();
        for (Currency curr : this.currencies) {
            this.currencyField.addItem(curr.toString());
        }
    }

    public Account fetchAccount() {
        String acnum = recipientAccountNumberField.getText();
        if (acnum != null) {
            try {
                int num = Integer.parseInt(acnum);
                return Controller.getAccountByAccountNumber(num);
            }catch (Exception ignored){
            }
        }
        utils.showNotice("Invalid Account Number");
        return null;
    }

    public void loadCustomerAccounts() {
        List<Account> accounts = this.customer.getAccounts();
        List<Account> filteredAccounts = new ArrayList<>();
        for (int i = 0; accounts != null && i < accounts.size(); i++) {
            if (!accounts.get(i).getType().equals(AccountType.LOAN)) {
                filteredAccounts.add((accounts.get(i)));
            }
        }
        this.allCustomerAccounts = accounts;
        this.customerAccounts = filteredAccounts;
        if (filteredAccounts.size() > 0) {
            this.selectedCustomerAccount = filteredAccounts.get(0);
        }
        if (accounts != null && accounts.size() > 0) {
            this.selectedCustomerSecondAccount = accounts.get(0);
        }

        customerAccountsList.removeAll();
        for (int i = 0; i < customerAccounts.size(); i++) {
            Account acc = customerAccounts.get(i);
            customerAccountsList.addItem(acc.getType() + " " + acc.getAccountNumber());
        }

        recipientAccountsList.removeAll();
        for (int i = 0; i < allCustomerAccounts.size(); i++) {
            Account acc = allCustomerAccounts.get(i);
            recipientAccountsList.addItem(acc.getType() + " " + acc.getAccountNumber());
        }
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
        basePanel.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        backButton = new JButton();
        backButton.setText("Back");
        basePanel.add(backButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        basePanel.add(spacer1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        basePanel.add(panel1, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        customerAccountsList = new JComboBox();
        customerAccountsList.setForeground(new Color(-14472005));
        panel1.add(customerAccountsList, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("From account");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        panel1.add(separator1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        basePanel.add(panel2, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(null, "Recipient", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        recipientPane = new JTabbedPane();
        panel2.add(recipientPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        recipientPane.addTab("Self", panel3);
        recipientAccountsList = new JComboBox();
        recipientAccountsList.setForeground(new Color(-7263045));
        panel3.add(recipientAccountsList, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        recipientPane.addTab("Others", panel4);
        recipientAccountNumberField = new JTextField();
        recipientAccountNumberField.setForeground(new Color(-7986501));
        panel4.add(recipientAccountNumberField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        basePanel.add(panel5, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(null, "Amount", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        amountField = new JTextField();
        panel5.add(amountField, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel5.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        currencyField = new JComboBox();
        panel5.add(currencyField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        transferButton = new JButton();
        transferButton.setText("Transfer");
        panel5.add(transferButton, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        basePanel.add(spacer3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return basePanel;
    }

}

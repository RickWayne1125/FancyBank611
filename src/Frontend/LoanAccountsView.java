package Frontend;

import API.Controller;
import Account.Loan.Loan;
import Money.Currency;
import Money.Money;
import Person.Customer.Customer;
import Utils.Helpers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.List;

public class LoanAccountsView extends AbstractJPanel{
    private JButton backButton;
    private JPanel basePanel;
    private JTextField amountField;
    private JButton requestButton;
    private JTabbedPane tabbedPane1;
    private JTable table1;
    private JTable table2;
    private JComboBox interestField;
    private JComboBox dueDateField;
    private JComboBox currencyField;
    private List<Currency> currencies;
    private Currency seletedCurrency;
    public LoanAccountsView() {
        this.currencies = Controller.getAllCurrency();
        this.seletedCurrency = this.currencies.get(0);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amt = amountField.getText();
                try {
                    double amount = Double.parseDouble(amt);
                    Currency currency = currencies.get(currencyField.getSelectedIndex());
                    double interest = (interestField.getSelectedIndex()+1)*2.5;
                    int years = (dueDateField.getSelectedIndex()+1)*5;
                    Date start = new Date();
                    Date end = new Date();
                    end.setYear(start.getYear()+years);
                    Loan loan = Helpers.createLoanAccount(new Money(amount, currency), interest, start, end);
                    Controller.openAccount((Customer) Frontend.getInstance().getUser(), loan);

                    utils.showNotice("Loan request submitted");

                    amountField.setText("");
                    refresh();
                } catch (Exception ex){
                    utils.showNotice("Invalid data");
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
    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

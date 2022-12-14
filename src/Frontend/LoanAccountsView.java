package Frontend;

import API.Controller;
import Account.Loan.Loan;
import Money.Currency;
import Money.Money;
import Person.Customer.Customer;
import Transact.Transaction;
import Transact.TransactionType;
import Utils.Helpers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoanAccountsView extends AbstractJPanel{
    private JButton backButton;
    private JPanel basePanel;
    private JTextField amountField;
    private JButton requestButton;
    private JTabbedPane tabbedPane1;
    private JTable approvedLoansTable;
    private JTable pendingLoansTable;
    private JComboBox interestField;
    private JComboBox dueDateField;
    private JComboBox currencyField;
    private List<Currency> currencies;
    private Currency seletedCurrency;
    public LoanAccountsView() {
        this.currencies = Controller.getAllCurrency();
        this.seletedCurrency = this.currencies.get(0);

        loadCurrenciesDropdown();

        refresh();
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
        List<Loan> loans = Controller.getLoansByCustomer((Customer) Frontend.getInstance().getUser());
        System.out.println("asfsdfsdfdasf");
        System.out.println(loans);
        List<Loan> approvedLoans = new ArrayList<>();
        List<Loan> pendingLoans = new ArrayList<>();

        for(Loan loan:loans){
            if(loan.isApproved()){
                approvedLoans.add(loan);
            } else {
                pendingLoans.add(loan);
            }
        }

        loadLoansTable(approvedLoans, approvedLoansTable);
        loadLoansTable(pendingLoans, pendingLoansTable);

    }

    public void loadLoansTable(List<Loan> loans, JTable table){
        table.removeAll();
        String[] columns = new String[] {
                "account", "start", "end", "amount", "interest"
        };
        Object[][] data = new Object[loans.size()][5];
        for (int i = 0; i<loans.size(); i++){
            Loan loan = loans.get(i);
            data[loans.size()-i-1] = new Object[]{loan.getAccountNumber(), loan.getStartDate().toString(), loan.getDueDate().toString(), loan.getCurrentBalance(), loan.getInterestRate() };
        }
        table.setModel(utils.getTableModel(data, columns));
        table.revalidate();
        Frontend.getInstance().render();
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
}

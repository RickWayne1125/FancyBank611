package Frontend;

import API.Controller;
import Account.AccountType;
import Account.Loan.Loan;
import Person.Customer.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoanView extends AbstractJPanel{
    private JPanel basePanel;
    private JButton approveButton;
    private JTable loanDetail;
    private JButton viewButton;
    private Loan loan;

    public LoanView(Loan loan, Boolean showApproveButton, LoansView parent, Boolean showPayButton) {
        this.loan = loan;
        if(!showApproveButton){
            approveButton.setVisible(false);
        }
        if(!showPayButton){
            viewButton.setVisible(false);
        }
        refresh();
        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.approveLoan(loan);
                parent.refresh();
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().next(ViewFactory.getAccount((Customer) Frontend.getInstance().getUser(), AccountType.LOAN, loan, false, showPayButton));
            }
        });
    }

    public void refresh(){
        loadAccountDetails();
    }

    public void loadAccountDetails(){
        loanDetail.removeAll();
        String[] columns = new String[] {
                "", "", "", ""
        };

        String name = this.loan.getUsername();
        //actual data for the table in a 2d array
        Object[][] data = new Object[][] {
                {"Name", name, "Account Number", this.loan.getAccountNumber()},
                {"Amount", this.loan.getCurrentBalance(), "Interest", this.loan.getInterestRate()+" %" },
                {"Applied on", this.loan.getStartDate().toString(), "Due by", this.loan.getDueDate().toString()  }
        };
        loanDetail.setModel(utils.getTableModel(data, columns));
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}

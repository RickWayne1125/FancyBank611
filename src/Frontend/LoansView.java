package Frontend;

import API.Controller;
import Account.Loan.Loan;
import Person.Customer.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LoansView extends AbstractJPanel{
    private JPanel basePanel;
    private JButton backButton;
    private JTabbedPane tabbedPane1;
    private JPanel approvedLoans;
    private JPanel pendingLoans;
    private Customer customer;
    private Boolean managerView;

    public LoansView(Boolean hideBackButton, Customer customer, Boolean managerView) {
        this.customer = customer;
        approvedLoans.setLayout(new BoxLayout(approvedLoans, BoxLayout.Y_AXIS));
        pendingLoans.setLayout(new BoxLayout(pendingLoans, BoxLayout.Y_AXIS));
        if(hideBackButton){
            backButton.setVisible(false);
        }
        this.managerView = managerView;

        refresh();

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frontend.getInstance().back();
            }
        });
    }

    public void refresh(){
        List<Loan> approvedLoans;
        List<Loan> pendingLoans;
        if(this.customer != null){
            approvedLoans = new ArrayList<>();
            pendingLoans = new ArrayList<>();
            List<Loan> allLoans = Controller.getLoansByCustomer(this.customer);
            for(int i = 0; allLoans!= null && i <allLoans.size(); i++){
                Loan loan = allLoans.get(i);
                if(loan.isApproved()){
                    approvedLoans.add(loan);
                } else {
                    pendingLoans.add(loan);
                }
            }
        } else {
            approvedLoans = Controller.getApprovedLoanList();
            pendingLoans = Controller.getUnapprovedLoanList();
        }
        loadLoanPanel(approvedLoans, this.approvedLoans, false, !this.managerView);
        loadLoanPanel(pendingLoans, this.pendingLoans, this.managerView, false);
    }

    public void loadLoanPanel(List<Loan> loans, JPanel panel, Boolean showApproveButton, Boolean showPayButton){
        panel.removeAll();
        if(loans!=null){
            for(Loan loan:loans){
                panel.add((new LoanView(loan,showApproveButton,this, showPayButton)).getBasePanel());
            }
        }
        panel.revalidate();
    }

    @Override
    public JPanel getBasePanel() {
        return basePanel;
    }
}

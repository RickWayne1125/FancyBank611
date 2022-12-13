package Account.Loan;

import Account.Account;
import Account.AccountType;

import java.util.Date;

public class Loan extends Account {
    private Date dueDate;
    private Date startDate;
    private boolean isApproved;

    public Loan(Integer accountNumber, String routingNumber, String swiftCode, double interestRate, Date startDate, Date dueDate) {
        super(accountNumber, routingNumber, swiftCode);
        super.setType(AccountType.LOAN);
        this.setInterestRate(interestRate);
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.isApproved = false;
    }

    public Loan(String routingNumber, String swiftCode, double interestRate, Date startDate, Date dueDate) {
        super(routingNumber, swiftCode);
        super.setType(AccountType.LOAN);
        this.setInterestRate(interestRate);
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.isApproved = false;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}

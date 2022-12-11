package Account.Loan;

import Account.Account;
import Account.AccountType;

import java.util.Date;

public class Loan extends Account {
    private Date dueDate;

    public Loan(int accountNumber, String routingNumber, String swiftCode, double interestRate, Date dueDate) {
        super(accountNumber, routingNumber, swiftCode);
        super.setType(AccountType.LOAN);
        this.setInterestRate(interestRate);
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}

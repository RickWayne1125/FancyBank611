package Account.Loan;

import Account.Account;
import Money.Money;

import java.util.Date;
import java.util.List;

public class Loan extends Account {
    private double interestRate;
    private Date dueDate;
    public Loan(int accountNumber,String routingNumber, String swiftCode, double interestRate,Date dueDate){
        super(accountNumber,routingNumber,swiftCode);
        this.interestRate=interestRate;
        this.dueDate=dueDate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}

package Account.Loan;

import Money.Money;

public class LoanService {
    private static LoanDAO loanDAO = new LoanDAO();
    public static void createLoan(Loan loan) {
        loanDAO.create(loan);
    }

    public static void payLoan(Loan loan, Money money) {
        // TODO: Implement
        // remember to convert money to loan currency
    }
}

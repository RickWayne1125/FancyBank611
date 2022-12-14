package Account.Loan;

import Account.Account;
import Account.AccountService;
import Account.AccountType;
import Money.Money;
import Money.MoneyService;
import Person.Customer.Customer;
import Person.Customer.CustomerDAO;
import Transact.Transaction;
import Transact.TransactionService;
import Transact.TransactionStatus;
import Transact.TransactionType;

import java.util.ArrayList;
import java.util.List;

public class LoanService {
    private static LoanDAO loanDAO = new LoanDAO();

    public static Loan getLoan(Integer accountNumber) {
        return loanDAO.readByAccountNumber(accountNumber);
    }

    public static List<Loan> getUnapprovedLoans() {
        return loanDAO.readUnapprovedLoans();
    }

    public static boolean payLoanByCash(Loan loan, Money money) {
        // start transaction
        Transaction transaction = TransactionService.create(loan, loan, money, TransactionType.PAY_LOAN);
        // check money list
        for (Money money1 : loan.getCurrentBalance()) {
            if (money1.getCurrency().getCurrencyName().equals(money.getCurrency().getCurrencyName())) {
                if (MoneyService.update(money1, -money.getAmount())) {
                    loanDAO.update(loan);
                    // update transaction status
                    TransactionService.setTransactionStatus(transaction, TransactionStatus.SUCCESS);
                    return true;
                }
                TransactionService.setTransactionStatus(transaction, TransactionStatus.FAILED);
                return false;
            }
        }
        TransactionService.setTransactionStatus(transaction, TransactionStatus.FAILED);
        return false;
    }

    public static boolean payLoanByTransfer(Loan loanAccount, Account fromAccount, Money money) {
        // start transaction
        Transaction transaction = TransactionService.create(fromAccount, loanAccount, money, TransactionType.PAY_LOAN);
        // if the fromAccount is a loan account
        if (fromAccount.getType() == AccountType.LOAN) {
            TransactionService.setTransactionStatus(transaction, TransactionStatus.FAILED);
            return false;
        }
        // check money list
        for (Money money1 : loanAccount.getCurrentBalance()) {
            if (money1.getCurrency().getCurrencyName().equals(money.getCurrency().getCurrencyName())) {
                if (MoneyService.update(money1, -money.getAmount())) {
                    loanDAO.update(loanAccount);
                    // update transaction status
                    TransactionService.setTransactionStatus(transaction, TransactionStatus.SUCCESS);
                    return true;
                }
                TransactionService.setTransactionStatus(transaction, TransactionStatus.FAILED);
                return false;
            }
        }
        TransactionService.setTransactionStatus(transaction, TransactionStatus.FAILED);
        return false;
    }

    public static boolean requestLoan(Customer customer, Loan loan) {
        try {
            // check is customer is eligible for loan (has collateral)
            if (!customer.getHasCollateral()) {
                return false;
            }
            return AccountService.openAccount(customer, loan);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean approveLoan(Loan loan) {
        try {
            loan.setApproved(true);
            loanDAO.update(loan);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Loan> getApprovedLoans() {
        return loanDAO.readApprovedLoans();
    }

    public boolean setHasCollateral(Customer customer, boolean hasCollateral) {
        CustomerDAO customerDAO = new CustomerDAO();
        try {
            customer.setHasCollateral(hasCollateral);
            customerDAO.update(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Loan> getLoansByCustomer(Customer customer) {
        List<Loan> loans = new ArrayList<>();
        for (Account account : customer.getAccounts()) {
            if (account.getType() == AccountType.LOAN) {
                loans.add(loanDAO.readByAccountNumber(account.getAccountNumber()));
            }
        }
        return loans;
    }
}

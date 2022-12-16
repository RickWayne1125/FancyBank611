package Account;

import Money.Money;
import Money.MoneyService;
import Money.CurrencyDAO;
import Person.Customer.Customer;
import Transact.Transaction;
import Transact.TransactionService;
import Transact.TransactionStatus;
import Transact.TransactionType;
import Utils.Config;
import Utils.IO;

public class AccountService {
    private static AccountDAO accountDAO = new AccountDAO();

    public static boolean openAccount(Customer customer, Account account) {
        // This function is only used to create a Checking or Saving or Security account
        // For Loan account, use the function in LoanService
        if (account.getType().equals(AccountType.LOAN)) {
            IO.displayMessage("AccountService: openAccount: Invalid account type", Utils.MessageType.ERROR);
            return false;
        }
        // Check if the account id is unique
        if (accountDAO.readByAccountNumber(account.getAccountNumber()) != null) {
            return false;
        }
        account.setUsername(customer.getUsername());
        accountDAO.create(account);
        // deposit and charge the service fee
        Money serviceFee = new Money(Config.DEFAULT_OPEN_SERVICE_FEE, new CurrencyDAO().read("USD"));
        deposit(account, serviceFee);
        Money serviceFeeCharge = new Money(Config.DEFAULT_OPEN_SERVICE_FEE, new CurrencyDAO().read("USD"));
        transfer(account, AccountService.getAccountByAccountNumber(Config.BANK_ACCOUNT_NUMBER), serviceFeeCharge);
        customer.getAccounts().add(account);
        return true;
    }

    public static boolean closeAccount(Customer customer, Account account) {
        // check if the account has enough money to pay the fee
        if (account.getBalanceByCurrency("USD") >= Config.DEFAULT_CLOSE_SERVICE_FEE) {
            // all the money in the account will be transferred to the bank
            for (Money money : account.getCurrentBalance()) {
                Account bankAccount = getAccountByAccountNumber(Config.BANK_ACCOUNT_NUMBER);
                AccountService.transfer(account, bankAccount, money);
            }
            customer.getAccounts().remove(account);
            accountDAO.delete(account);
            return true;
        }
        return false;
    }

    public static boolean deposit(Account account, Money money) {
        // start transaction
        Transaction transaction = TransactionService.create(account, account, money, TransactionType.DEPOSIT);
        // if the account is a loan account
        if (account.getType() == AccountType.LOAN) {
            TransactionService.setTransactionStatus(transaction, TransactionStatus.FAILED);
            return false;
        }
        if (money.getAmount() < 0) {
            TransactionService.setTransactionStatus(transaction, TransactionStatus.FAILED);
            return false;
        }
        // check money list in account
        for (Money m : account.getCurrentBalance()) {
            if (m.getCurrency().getCurrencyName().equals(money.getCurrency().getCurrencyName())) {
                MoneyService.update(m, money.getAmount());
                // update transaction status
                TransactionService.setTransactionStatus(transaction, TransactionStatus.SUCCESS);
                return true;
            }
        }
        // if not found, add new money to account
        money.setAccountNumber(account.getAccountNumber());
        MoneyService.create(money);
        account.getCurrentBalance().add(money);
        accountDAO.update(account);
        // update transaction status
        TransactionService.setTransactionStatus(transaction, TransactionStatus.SUCCESS);
        return true;
    }

    public static boolean withdraw(Account account, Money money) {
        // start transaction
        Transaction transaction = TransactionService.create(account, account, money, TransactionType.WITHDRAW);
        // if the account is a loan account
        if (account.getType() == AccountType.LOAN) {
            TransactionService.setTransactionStatus(transaction, TransactionStatus.FAILED);
            return false;
        }
        if (money.getAmount() < 0) {
            TransactionService.setTransactionStatus(transaction, TransactionStatus.FAILED);
            return false;
        }
        // check money list in account
        for (Money m : account.getCurrentBalance()) {
            if (m.getCurrency().getCurrencyName().equals(money.getCurrency().getCurrencyName())) {
                if (MoneyService.update(m, -money.getAmount())) {
                    accountDAO.update(account);
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

    public static boolean transfer(Account fromAccount, Account toAccount, Money money) {
        // start transaction
        Transaction transaction;
        if (toAccount.getAccountNumber() == Config.BANK_ACCOUNT_NUMBER) {
            IO.displayMessage("AccountService: transfer: Transfer to bank", Utils.MessageType.INFO);
            transaction = TransactionService.create(fromAccount, toAccount, money, TransactionType.SERVICE_FEE);
        } else {
            transaction = TransactionService.create(fromAccount, toAccount, money, TransactionType.TRANSFER);
        }
        // if any of the accounts is a loan account
        if (fromAccount.getType() == AccountType.LOAN || toAccount.getType() == AccountType.LOAN) {
            TransactionService.setTransactionStatus(transaction, TransactionStatus.FAILED);
            return false;
        }
        // check money list in account
        for (Money m : fromAccount.getCurrentBalance()) {
            if (m.getCurrency().getCurrencyName().equals(money.getCurrency().getCurrencyName())) {
                double rate = toAccount.getAccountNumber() == Config.BANK_ACCOUNT_NUMBER ? 1 : 1 + Config.DEFAULT_TRANSACT_SERVICE_FEE_RATE;
                if (MoneyService.update(m, -money.getAmount() * rate)) {
                    // if rate > 1, which means the bank will charge a service fee
                    if (toAccount.getAccountNumber() != Config.BANK_ACCOUNT_NUMBER) {
                        Account bankAccount = getAccountByAccountNumber(Config.BANK_ACCOUNT_NUMBER);
                        Money serviceFee = new Money(money.getAmount() * Config.DEFAULT_TRANSACT_SERVICE_FEE_RATE, money.getCurrency());
                        transfer(fromAccount, bankAccount, serviceFee);
                    }
                    accountDAO.update(fromAccount);
                    // check money list in account
                    for (Money m2 : toAccount.getCurrentBalance()) {
                        if (m2.getCurrency().getCurrencyName().equals(money.getCurrency().getCurrencyName())) {
                            MoneyService.update(m2, money.getAmount());
                            accountDAO.update(toAccount);
                            // update transaction status
                            TransactionService.setTransactionStatus(transaction, TransactionStatus.SUCCESS);
                            return true;
                        }
                    }
                    // if not found, add new money to account
                    money.setAccountNumber(toAccount.getAccountNumber());
                    MoneyService.create(money);
                    toAccount.getCurrentBalance().add(money);
                    accountDAO.update(toAccount);
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

    public static boolean convertCurrency(Account account, String originalCurrency, String targetCurrency) {
        // check money list in account
        for (Money m : account.getCurrentBalance()) {
            if (m.getCurrency().getCurrencyName().equals(originalCurrency)) {
                if (MoneyService.convert(m, targetCurrency)) {
                    accountDAO.update(account);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static Account refreshAccount(Account account) {
        return accountDAO.readByAccountNumber(account.getAccountNumber());
    }

    public static boolean buyCurrency(Account account, String originalCurrency, Money moneyOfNewCurrency) throws CloneNotSupportedException {
        // calculate the amount of original currency needed
        Money moneyOfOriginalCurrency = moneyOfNewCurrency.clone();
        MoneyService.convert(moneyOfOriginalCurrency, originalCurrency);    // convert to original currency
        for (Money m : account.getCurrentBalance()) {
            if (m.getCurrency().getCurrencyName().equals(originalCurrency)) {
                if (MoneyService.update(m, -moneyOfOriginalCurrency.getAmount())) {
                    deposit(account, moneyOfNewCurrency);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static Account getAccountByAccountNumber(int accountNumber) {
        return accountDAO.readByAccountNumber(accountNumber);
    }

    public static void updateInterest(Account account, long days) {
        double interestRate = account.getInterestRate();
        for (Money m : account.getCurrentBalance()) {
            MoneyService.update(m, m.getAmount() * interestRate * days / 365);
        }
        accountDAO.update(account);
    }
}

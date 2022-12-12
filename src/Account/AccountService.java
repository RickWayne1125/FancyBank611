package Account;

import DataBase.DataBase;
import Money.Money;
import Money.MoneyService;
import Money.CurrencyDAO;
import Person.Customer.Customer;
import Person.Customer.CustomerService;
import Transact.Transaction;
import Transact.TransactionService;
import Transact.TransactionStatus;
import Transact.TransactionType;
import Utils.Config;
import Utils.IO;

import java.util.Scanner;

public class AccountService {
    private static AccountDAO accountDAO = new AccountDAO();

    public static void openAccount(Customer customer, Account account) {
        account.setUsername(customer.getUsername());
        accountDAO.create(account);
    }

    public static void closeAccount(Account account) {
        accountDAO.delete(account);
    }

    public static void deposit(Account account, Money money) {
        // start transaction
        Transaction transaction = TransactionService.create(account, account, money, TransactionType.DEPOSIT);
        // check money list in account
        for (Money m : account.getCurrentBalance()) {
            if (m.getCurrency().getCurrencyName().equals(money.getCurrency().getCurrencyName())) {
                MoneyService.update(m, money.getAmount());
                // update transaction status
                TransactionService.setTransactionStatus(transaction, TransactionStatus.SUCCESS);
                return;
            }
        }
        // if not found, add new money to account
        money.setAccountNumber(account.getAccountNumber());
        MoneyService.create(money);
        account.getCurrentBalance().add(money);
        accountDAO.update(account);
        // update transaction status
        TransactionService.setTransactionStatus(transaction, TransactionStatus.SUCCESS);
    }

    public static boolean withdraw(Account account, Money money) {
        // start transaction
        Transaction transaction = TransactionService.create(account, account, money, TransactionType.WITHDRAW);
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
        Transaction transaction = TransactionService.create(fromAccount, toAccount, money, TransactionType.TRANSFER);
        // check money list in account
        for (Money m : fromAccount.getCurrentBalance()) {
            if (m.getCurrency().getCurrencyName().equals(money.getCurrency().getCurrencyName())) {
                if (MoneyService.update(m, -money.getAmount())) {
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

    public static void main(String[] args) {
        DataBase.clearTables();
        DataBase.generateTestData();
        // Test
        Customer customer = new Customer("rick", "Rick", "test", "test",
                "test", "test", "test", "test");
        CustomerService.registerCustomer(customer);
        // Test open account
        IO.displayMessage("Test open account", Utils.MessageType.INFO);
        Checking checking = new Checking(123456789, "checking1", "checking1");
        checking.setInterestRate(Config.DEFAULT_INTEREST_RATE);
        AccountService.openAccount(customer, checking);
        System.out.println(getAccountByAccountNumber(123456789));
        // Test deposit
        IO.displayMessage("Test deposit", Utils.MessageType.INFO);
        CurrencyDAO currencyDAO = new CurrencyDAO();
        Money money = new Money(100, currencyDAO.read("USD"));
        AccountService.deposit(checking, money);
        System.out.println(getAccountByAccountNumber(123456789).getCurrentBalance());
        System.out.println(TransactionService.getTransactionsByAccount(checking));
        // Test withdraw
        IO.displayMessage("Test withdraw", Utils.MessageType.INFO);
        AccountService.withdraw(checking, money);
        System.out.println(getAccountByAccountNumber(123456789).getCurrentBalance());
        System.out.println(TransactionService.getTransactionsByAccount(checking));
        // Test transfer
        money = new Money(100, currencyDAO.read("USD"));
        IO.displayMessage("Test transfer", Utils.MessageType.INFO);
        AccountService.deposit(checking, money);
        System.out.println(getAccountByAccountNumber(123456789).getCurrentBalance());
        Saving saving = new Saving(987654321, "saving1", "saving1");
        saving.setInterestRate(Config.DEFAULT_INTEREST_RATE);
        AccountService.openAccount(customer, saving);
        System.out.println(getAccountByAccountNumber(987654321));
        AccountService.transfer(checking, saving, money);
        System.out.println(getAccountByAccountNumber(123456789).getCurrentBalance());
        System.out.println(getAccountByAccountNumber(987654321).getCurrentBalance());
        System.out.println(TransactionService.getTransactionsByAccount(checking));
        System.out.println(TransactionService.getTransactionsByAccount(saving));
    }
}

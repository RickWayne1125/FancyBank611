package Utils;

import API.Controller;
import Account.Account;
import Account.AccountService;
import Account.Checking;
import Account.Loan.Loan;
import Account.Loan.LoanService;
import Account.Saving;
import DataBase.DataBase;
import Money.CurrencyDAO;
import Money.Money;
import Person.Customer.Customer;
import Person.Customer.CustomerService;
import Person.Manager.Manager;
import Transact.TransactionService;

import DataBase.DataBase;

import static Account.AccountService.getAccountByAccountNumber;

public class Tests {
    public static void unitTest1(){
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
        IO.displayMessage("Test transfer", Utils.MessageType.INFO);
        Saving saving = new Saving(987654321, "saving1", "saving1");
        saving.setInterestRate(Config.DEFAULT_INTEREST_RATE);
        AccountService.openAccount(customer, saving);
    }

    public static void unitTest2(){
        Customer customer = Controller.loginCustomer("rick", "test");
        Checking checking = null;
        Saving saving = null;
        for (Account account : customer.getAccounts()) {
            System.out.println(account.getAccountNumber() + account.getUsername());
            if (account instanceof Checking) {
                checking = (Checking) account;
                System.out.println(checking.getInterestRate());
            } else if (account instanceof Saving) {
                saving = (Saving) account;
                System.out.println(saving.getInterestRate());
            }
        }
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
        AccountService.transfer(checking, saving, money);
        System.out.println(getAccountByAccountNumber(123456789).getCurrentBalance());
        System.out.println(getAccountByAccountNumber(987654321).getCurrentBalance());
        System.out.println(TransactionService.getTransactionsByAccount(checking));
        System.out.println(TransactionService.getTransactionsByAccount(saving));
    }

    public static void customerLoginAsManager(){
        Manager manager = Controller.loginManager("rick", "test");
        IO.displayMessage("Test manager login", Utils.MessageType.INFO);
        System.out.println(manager);
    }

    public static void main(String[] args) {
        //unitTest1();
        //unitTest2();
        customerLoginAsManager();
    }
}

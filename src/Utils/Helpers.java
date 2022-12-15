package Utils;

import API.Controller;
import Account.Account;
import Account.Loan.Loan;
import Account.Saving;
import Account.Checking;
import Account.AccountType;
import Account.Security.SecurityAccount;
import BoughtStock.BoughtStock;
import Frontend.Frontend;
import Money.Currency;
import Money.Money;
import Person.Customer.Customer;
import Person.Manager.Manager;
import Person.Person;
import Stock.Stock;
import Transact.Transaction;
import Frontend.utils;
import Frontend.ViewFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Helpers {
    public static Account createNewAccount(AccountType accountType) {

        int accountNumber = (int) ((Math.random() * (9999999 - 1111111)) + 1111111);
        String routingNumber = "1234ABCD";
        String swiftCode = "ABCDPEQ";

        if (accountType.equals(AccountType.SAVING)) {
            return new Saving(accountNumber, routingNumber, swiftCode);
        }
        if (accountType.equals(AccountType.CHECKING)) {
            return new Checking(accountNumber, routingNumber, swiftCode);
        }
        if (accountType.equals(AccountType.SECURITY)) {
            return new SecurityAccount(accountNumber, routingNumber, swiftCode);
        }
        return null;
    }


    public static Loan createLoanAccount(Money money, Double interest, Date start, Date end) {
        int accountNumber = (int) ((Math.random() * (9999999 - 1111111)) + 1111111);
        String routingNumber = "1234ABCD";
        String swiftCode = "ABCDPEQ";

        Loan loan = new Loan(accountNumber, routingNumber, swiftCode, interest, start, end);
        List<Money> balance = new ArrayList<>();
        balance.add(money);
        loan.setCurrentBalance(balance);
        return loan;
    }

    public static String getAccountTypeString(AccountType accountType) {
        if (accountType.equals(AccountType.SAVING)) {
            return "Savings";
        }
        if (accountType.equals(AccountType.CHECKING)) {
            return "Checking";
        }
        if (accountType.equals(AccountType.SECURITY)) {
            return "Security";
        }
        return null;
    }

    public static Account getAccount(AccountType type, List<Account> accounts) {
        for (Account account : accounts) {
            if (account.getType().equals(type)) {
                return account;
            }
        }
        return null;
    }

    public static List<Transaction> filterTransactionsByCurrency(List<Transaction> transactions, Currency currency) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getMoney().getCurrency().getCurrencyName().equals(currency.getCurrencyName())) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

    public static boolean createUser(String userType, String username, String firstName, String middleName, String lastName, String email, String password, String contact, String address) {
        if (userType.equals("customer")) {
            return Controller.registerCustomer(new Customer(username, firstName, middleName, lastName, email, password, contact, address));
        } else if (userType.equals("manager")) {
            return Controller.registerManager(new Manager(username, firstName, middleName, lastName, email, password, contact, address));
        }
        return false;
    }

    public static boolean editUser(String firstName, String middleName, String lastName, String email, String password, String contact, String address, Person person) {
        try {
            person.setFirstName(firstName);
            person.setMiddleName(middleName);
            person.setLastName(lastName);
            person.setEmail(email);
            person.setPassword(password);
            person.setContact(contact);
            person.setAddress(address);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void securityAccountView() {
        Customer customer = (Customer) Frontend.getInstance().getUser();
        SecurityAccount account = (SecurityAccount) Helpers.getAccount(AccountType.SECURITY, customer.getAccounts());
        System.out.println("asd");
        System.out.println(account);
        if (account == null) {
            String amount = javax.swing.JOptionPane.showInputDialog("Enter initial amount");
            try {
                int amt = Integer.parseInt(amount);
                if (amt < 1000) {
                    utils.showNotice("Minimum 1000 USD required.");

                } else {
                    Account savingsAccount = Helpers.getAccount(AccountType.SAVING, customer.getAccounts());
                    if (savingsAccount == null) {
                        utils.showNotice("You need to open a savings account first");
                    } else {
                        account = (SecurityAccount) Helpers.createNewAccount(AccountType.SECURITY);
                        if (!Controller.openStock(customer, savingsAccount, account, amt)) {
                            utils.showNotice("Minimum 5000 required in savings to create a securities account");
                        } else {
                            utils.showNotice("New " + Helpers.getAccountTypeString(AccountType.SECURITY) + " account created!");
                            Frontend.getInstance().next(ViewFactory.getSecurityAccount(account));
                        }
                    }
                }
            } catch (Exception e) {
                utils.showNotice("Please enter a valid amount");
            }
        } else {
            Frontend.getInstance().next(ViewFactory.getSecurityAccount(account));
        }
    }

    public static boolean sellStock(SecurityAccount secAcc, BoughtStock stock) {
        try {
            String count = javax.swing.JOptionPane.showInputDialog("Enter units");
            int cnt = Integer.parseInt(count);
            if (!Controller.sellStock(secAcc, Controller.getAccountByAccountNumber(1), stock, cnt)) {
                utils.showNotice("Failed");
            } else {
                return true;
            }
        } catch (Exception e) {
            utils.showNotice("Failed");
        }
        return false;
    }

    public static boolean buyStock(SecurityAccount secAcc, Stock stock) {
        try {
            String count = javax.swing.JOptionPane.showInputDialog("Enter units");
            int cnt = Integer.parseInt(count);
            if (!Controller.buyStock(secAcc, Controller.getAccountByAccountNumber(1), stock, cnt)) {
                utils.showNotice("Failed");
            } else {
                return true;
            }
        } catch (Exception e) {
            utils.showNotice("Failed");
        }
        return false;
    }

    public static void openAccountView(Customer customer, AccountType accountType, Boolean managerView){
        Account account = Helpers.getAccount(accountType, customer.getAccounts());
        if (account == null) {
            if(managerView){
                utils.showNotice("User doesn't have a "+accountType+" account yet.");
            } else {
                account = Helpers.createNewAccount(accountType);
                Controller.openAccount(customer, account);
                utils.showNotice("New " + Helpers.getAccountTypeString(accountType) + " account created!");
                Frontend.getInstance().next(ViewFactory.getAccount(customer, accountType, account, false, false));
            }
        } else {
            Frontend.getInstance().next(ViewFactory.getAccount(customer, accountType, account, managerView, false));
        }
    }

    public static void editStockPrice(Stock stock){
        try {
            String count = javax.swing.JOptionPane.showInputDialog("Enter new value");
            int cnt = Integer.parseInt(count);
            Controller.updateStock(stock.getStockId(), cnt);
        } catch (Exception e) {
            utils.showNotice("Failed");
        }
    }
}

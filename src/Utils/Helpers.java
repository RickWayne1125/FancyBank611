package Utils;

import API.Controller;
import Account.Account;
import Account.Loan.Loan;
import Account.Saving;
import Account.Checking;
import Account.AccountType;
import Money.Currency;
import Money.Money;
import Person.Customer.Customer;
import Person.Manager.Manager;
import Person.Person;
import Transact.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Helpers {
    public static Account createNewAccount(AccountType accountType){

        int accountNumber =  (int) ((Math.random() * (9999999 - 1111111)) + 1111111);
        String routingNumber = "1234ABCD";
        String swiftCode = "ABCDPEQ";

        if (accountType.equals(AccountType.SAVING)) {
            return new Saving(accountNumber, routingNumber, swiftCode);
        }
        if (accountType.equals(AccountType.CHECKING)) {
            return new Checking(accountNumber, routingNumber, swiftCode);
        }
        return null;
    };

    public static Loan createLoanAccount(Money money, Double interest, Date start, Date end){
        int accountNumber =  (int) ((Math.random() * (9999999 - 1111111)) + 1111111);
        String routingNumber = "1234ABCD";
        String swiftCode = "ABCDPEQ";

        Loan loan = new Loan(accountNumber, routingNumber, swiftCode, interest, start, end);
        List<Money> balance = new ArrayList<>();
        balance.add(money);
        loan.setCurrentBalance(balance);
        return loan;
    }

    public static String getAccountTypeString(AccountType accountType){
        if (accountType.equals(AccountType.SAVING)) {
            return "Savings";
        }
        if (accountType.equals(AccountType.CHECKING)) {
            return "Checking";
        }
        return null;
    }

    public static Account getAccount(AccountType type, List<Account> accounts){
        for (Account account:accounts){
            if (account.getType().equals(type)){
                return account;
            }
        }
        return null;
    }

    public static List<Transaction> filterTransactionsByCurrency(List<Transaction> transactions, Currency currency) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction:transactions){
            if(transaction.getMoney().getCurrency().getCurrencyName().equals(currency.getCurrencyName())){
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

    public static boolean createUser(String userType, String username, String firstName, String middleName, String lastName, String email, String password, String contact, String address){
        if(userType.equals("customer")) {
            return Controller.registerCustomer(new Customer(username, firstName, middleName, lastName, email, password, contact, address));
        } else if(userType.equals("manager")){
            return Controller.registerManager(new Manager(username, firstName, middleName, lastName, email, password, contact, address));
        }
        return false;
    }

    public static boolean editUser(String firstName, String middleName, String lastName, String email, String password, String contact, String address, Person person){
        try {
            person.setFirstName(firstName);
            person.setMiddleName(middleName);
            person.setLastName(lastName);
            person.setEmail(email);
            person.setPassword(password);
            person.setContact(contact);
            person.setAddress(address);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}

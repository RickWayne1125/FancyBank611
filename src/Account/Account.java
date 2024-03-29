package Account;

import Money.Money;
import Transact.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {
    private int accountNumber;
    private String routingNumber;
    private String swiftCode;
    private AccountType type;
    private List<Money> currentBalance;
    private List<Transaction> transactionHistory;
    private double interestRate;
    private String username;

    public Account(int accountNumber, String routingNumber, String swiftCode) {
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.swiftCode = swiftCode;
        this.currentBalance = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
    }

    public Account(String routingNumber, String swiftCode) {
        // generate unique account number using timestamp and random number
        this.accountNumber = (int) (new Date().getTime() + (int) (Math.random() * 1000000000));
        this.routingNumber = routingNumber;
        this.swiftCode = swiftCode;
        this.currentBalance = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public AccountType getType() {
        return type;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setCurrentBalance(List<Money> currentBalance) {
        if (currentBalance == null) {
            this.currentBalance = new ArrayList<>();
            return;
        }
        this.currentBalance = currentBalance;
        for (Money money : currentBalance) {
            money.setAccountNumber(this.accountNumber);
        }
    }

    public List<Money> getCurrentBalance() {
        return currentBalance;
    }

    public void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(Transaction transaction) {
        this.transactionHistory.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        this.transactionHistory.remove(transaction);
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "AccountNo: " + accountNumber + ", AccountType: " + type + ", RoutingNo: " + routingNumber + ", SwiftCode: " + swiftCode;
    }

    public double getBalanceByCurrency(String currency) {
        for (Money money : currentBalance) {
            if (money.getCurrency().getCurrencyName().equals(currency)) {
                return money.getAmount();
            }
        }
        return 0;
    }
}

package Transact;

import Account.Account;
import Money.Money;

import java.util.Date;

public class Transaction {
    private String id;
    private Date date;
    private Account from;
    private Account to;
    private Money money;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;

    public Transaction(Date date, Account from, Account to, Money money, TransactionType transactionType) {
        // Use timestamp and from account number to generate unique id
        this.id = date.getTime() + "/" + from.getAccountNumber();
        this.date = date;
        this.from = from;
        this.to = to;
        this.money = money;
        this.transactionType = transactionType;
        this.transactionStatus = TransactionStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String toString() {
        return this.transactionType + " " + this.transactionStatus + " " + this.money + " from " + this.from.getAccountNumber() + " to "
                + this.to.getAccountNumber() + " on " + this.date;
    }
}

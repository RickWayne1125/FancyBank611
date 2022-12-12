package Transact;

import Account.Account;
import Money.Money;

import java.util.Date;

enum TransactionType {
    REGULAR_TRANSACTION,
    BILL_PAY,
    SERVICE_FEE
}

enum TransactionStatus {
    SUCCESS,
    FAILED,
    PENDING
}

public class Transaction {
    private int id;
    private Date date;
    private Account from;
    private Account to;
    private Money amount;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;

    public Transaction(int id, Date date, Account from, Account to, Money amount, TransactionType transactionType,
                       TransactionStatus transacttionStatus) {
        this.id = id;
        this.date = date;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionStatus = transacttionStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
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

    public String toString() {
        return this.transactionType + " " + this.transactionStatus + " " + this.amount + " from " + this.from + " to "
                + this.to + " on " + this.date;
    }
}

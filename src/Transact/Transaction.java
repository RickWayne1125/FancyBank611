package Transact;

import Account.Account;
import Money.Money;

import java.util.Date;

enum TransactionType{
    REGULAR_TRANSACTION,
    BILL_PAY,
    SERVICE_FEE
}

enum TransacttionStatus{
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
    private TransacttionStatus transacttionStatus;

    public Transaction(int id, Date date, Account from, Account to, Money amount, TransactionType transactionType, TransacttionStatus transacttionStatus) {
        this.id = id;
        this.date = date;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transacttionStatus = transacttionStatus;
    }
    //todo
    // not sure what to return to frontend
    public void printTransaction(){

    }
}

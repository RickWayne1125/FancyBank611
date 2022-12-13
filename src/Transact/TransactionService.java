package Transact;

import Account.Account;
import Money.Money;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class TransactionService {
    private static final TransactionDAO transactionDAO = new TransactionDAO();

    public static Transaction create(Account from, Account to, Money money, TransactionType transactionType) {
        Transaction transaction = new Transaction(new Date(), from, to, money, transactionType);
        transactionDAO.create(transaction);
        return transaction;
    }

    public static List<Transaction> getTransactionsByAccount(Account account) {
        return transactionDAO.readByAccount(account);
    }

    public static void setTransactionStatus(Transaction transaction, TransactionStatus transactionStatus) {
        transaction.setTransactionStatus(transactionStatus);
        transactionDAO.update(transaction);
    }



    public static void main(String[] args) throws ParseException {
        // Test

        //System.out.println(transactionDAO.readByTransactionId("1670900174744/123456789").getDate());
        System.out.println(transactionDAO.convertFormatEnd("12-12-2022"));
    }
}

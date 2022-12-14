package Transact;

import Account.Account;
import Account.AccountDAO;
import DataBase.DataBase;
import Money.Money;
import Money.CurrencyDAO;
import Utils.DAO;
import Utils.IO;
import Utils.MessageType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TransactionDAO implements DAO<Transaction> {
    private static DataBase dataBase = new DataBase();

    @Override
    public void create(Transaction transaction) {
        String sql = "INSERT INTO TransactionTable (transaction_id, transaction_status, from_account, to_account, transaction_type, " +
                "amount, currency, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        dataBase.execute(sql, new String[]{transaction.getId(), String.valueOf(transaction.getTransactionStatus()),
                String.valueOf(transaction.getFrom().getAccountNumber()), String.valueOf(transaction.getTo().getAccountNumber()),
                String.valueOf(transaction.getTransactionType()), String.valueOf(transaction.getMoney().getAmount()),
                transaction.getMoney().getCurrency().getCurrencyName(), transaction.getDate().toString()});
    }

    public Transaction readByTransactionId(String transactionId) {
        String sql = "SELECT * FROM TransactionTable WHERE transaction_id = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{transactionId});
        if (results.size() == 0) {
            return null;
        }
        if (results.size() > 1) {
            throw new RuntimeException("More than one row returned");
        }
        Map<String, String> result = results.get(0);
        Account from = new AccountDAO().readByAccountNumber(Integer.parseInt(result.get("from_account")));
        Account to = new AccountDAO().readByAccountNumber(Integer.parseInt(result.get("to_account")));
        Money money = new Money(Double.parseDouble(result.get("amount")), new CurrencyDAO().read(result.get("currency")));
        TransactionType transactionType = TransactionType.valueOf(result.get("transaction_type"));
        Date date = new Date(result.get("date"));
        Transaction transaction= new Transaction(date, from, to, money, transactionType);
        transaction.setTransactionStatus(TransactionStatus.valueOf(result.get("transaction_status")));
        transaction.setId(result.get("transaction_id"));
        return transaction;
    }

    public List<Transaction> readByAccount(Account account) {
        String sql = "SELECT * FROM TransactionTable WHERE from_account = ? OR to_account = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{String.valueOf(account.getAccountNumber()),
                String.valueOf(account.getAccountNumber())});
        List<Transaction> transactions = new ArrayList<>();
        if (results.size() == 0) {
            return transactions;
        }
        for (Map<String, String> result : results) {
            Account from = new AccountDAO().readByAccountNumber(Integer.parseInt(result.get("from_account")));
            Account to = new AccountDAO().readByAccountNumber(Integer.parseInt(result.get("to_account")));
            Money money = new Money(Double.parseDouble(result.get("amount")), new CurrencyDAO().read(result.get("currency")));
            TransactionType transactionType = TransactionType.valueOf(result.get("transaction_type"));
            Date date = new Date(result.get("date"));
            Transaction transaction= new Transaction(date, from, to, money, transactionType);
            transaction.setTransactionStatus(TransactionStatus.valueOf(result.get("transaction_status")));
            transaction.setId(result.get("transaction_id"));
            transactions.add(transaction);
        }
        return transactions;
    }

    public String convertFormat(String str) throws ParseException {
        String input = str;
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");


        Date date = inputFormat.parse(input);
        String output = outputFormat.format(date);
        return output;
    }

    public String convertFormatEnd(String str) throws ParseException {
        String input = str;
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");


        Date date = inputFormat.parse(input);
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        String output = outputFormat.format(date);
        return output;
    }

    public List<Transaction> readByDay(String day) throws ParseException {
        String dayStart= "" ;
        String dayEnd ="";
        try{
            dayStart = convertFormat(day);
            dayEnd = convertFormatEnd(day);
        }catch (ParseException e) {
            IO.displayMessage("Error parsing input date string: " + e.getMessage(), MessageType.ERROR);
        }
        String sql = "SELECT * FROM TransactionTable WHERE date >= ? AND date <= ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{dayStart, dayEnd});
        List<Transaction> transactions = new ArrayList<>();
        if (results.size() == 0) {
            return transactions;
        }
        for (Map<String, String> result : results) {
            Account from = new AccountDAO().readByAccountNumber(Integer.parseInt(result.get("from_account")));
            Account to = new AccountDAO().readByAccountNumber(Integer.parseInt(result.get("to_account")));
            Money money = new Money(Double.parseDouble(result.get("amount")), new CurrencyDAO().read(result.get("currency")));
            TransactionType transactionType = TransactionType.valueOf(result.get("transaction_type"));
            Date date = new Date(result.get("date"));
            Transaction transaction= new Transaction(date, from, to, money, transactionType);
            transaction.setTransactionStatus(TransactionStatus.valueOf(result.get("transaction_status")));
            transaction.setId(result.get("transaction_id"));
            transactions.add(transaction);
        }
        return transactions;
    }


    @Override
    public void update(Transaction transaction) {
        String sql = "UPDATE TransactionTable SET from_account = ?, to_account = ?, transaction_type = ?, amount = ?, currency = ?, date = ?, transaction_status = ? WHERE transaction_id = ?";
        dataBase.execute(sql, new String[]{String.valueOf(transaction.getFrom().getAccountNumber()), String.valueOf(transaction.getTo().getAccountNumber()),
                String.valueOf(transaction.getTransactionType()), String.valueOf(transaction.getMoney().getAmount()), transaction.getMoney().getCurrency().getCurrencyName(),
                transaction.getDate().toString(), String.valueOf(transaction.getTransactionStatus()), transaction.getId()});
    }

    @Override
    public void delete(Transaction transaction) {
        String sql = "DELETE FROM TransactionTable WHERE transaction_id = ?";
        dataBase.execute(sql, new String[]{transaction.getId()});
    }
}

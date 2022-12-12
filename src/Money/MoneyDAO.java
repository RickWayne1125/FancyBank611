package Money;

import DataBase.DataBase;
import Utils.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoneyDAO implements DAO<Money> {
    static DataBase dataBase = new DataBase();

    @Override
    public void create(Money money) {
        String sql = "INSERT INTO Money (amount,currency) VALUES ("
                + money.getAmount() + ",'" + money.getCurrency().getCurrencyName() + "')";
        dataBase.execute(sql);
    }


    public Money read(Integer accountNumber, String currencyName) {
        String sql = "SELECT * FROM Money WHERE account_no = ? AND currency = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{String.valueOf(accountNumber), currencyName});
        // if result does not contain any row, return null
        if (results.size() == 0) {
            return null;
        }
        // if result contains more than one row, throw exception
        if (results.size() > 1) {
            throw new RuntimeException("More than one row returned");
        }
        // if result contains one row, return the currency
        Map<String, String> row = results.get(0);
        Money money = new Money(Double.parseDouble(row.get("amount")), new CurrencyDAO().read(row.get("currency")));
        money.setAccountNumber(Integer.parseInt(row.get("account_no")));
        return money;
    }

    public List<Money> readByAccount(Integer accountNumber) {
        String sql = "SELECT * FROM Money WHERE account_no = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{String.valueOf(accountNumber)});
        // if result does not contain any row, return null
        if (results.size() == 0) {
            return null;
        } else {
            List<Money> moneys = new ArrayList<>();
            for (Map<String, String> row : results) {
                Money money = new Money(Double.parseDouble(row.get("amount")), new CurrencyDAO().read(row.get("currency")));
                money.setAccountNumber(Integer.parseInt(row.get("account_no")));
                moneys.add(money);
            }
            return moneys;
        }
    }

    @Override
    public void delete(Money money) {
        String sql = "DELETE FROM Money WHERE account_no = ? AND currency = ?";
        dataBase.execute(sql, new String[]{String.valueOf(money.getAccountNumber()), money.getCurrency().getCurrencyName()});
    }

    @Override
    public void update(Money money) {
        String sql = "UPDATE Money SET amount = ? WHERE account_no = ? AND currency = ?";
        dataBase.execute(sql, new String[]{String.valueOf(money.getAmount()), String.valueOf(money.getAccountNumber()), money.getCurrency().getCurrencyName()});
    }

}

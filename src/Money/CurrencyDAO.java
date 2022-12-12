package Money;

import DataBase.DataBase;
import Utils.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrencyDAO implements DAO<Currency> {
    static DataBase dataBase = new DataBase();

    @Override
    public void create(Currency currency) {
        String sql = "INSERT INTO Currency (name,rate,symbol) VALUES (?, ?, ?)";
        dataBase.execute(sql, new String[]{currency.getCurrencyName(), String.valueOf(currency.getExchangeRate()), currency.getSymbol()});
    }

    @Override
    public void update(Currency currency) {
        String sql = "UPDATE Currency SET rate = ?, symbol = ? WHERE name = ?";
        dataBase.execute(sql, new String[]{String.valueOf(currency.getExchangeRate()), currency.getSymbol(), currency.getCurrencyName()});
    }

    @Override
    public void delete(Currency currency) {
        String sql = "DELETE FROM Currency WHERE name = ?";
        dataBase.execute(sql, new String[]{currency.getCurrencyName()});
    }

    public Currency read(String currencyName) {
        String sql = "SELECT * FROM Currency WHERE name = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{currencyName});
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
        return new Currency(row.get("name"), row.get("symbol"), Double.parseDouble(row.get("rate")));
    }

    public List<Currency> readAll() {
        List<Currency> currencies = new ArrayList<>();
        String sql = "SELECT * FROM Currency";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{});
        if (results.size() == 0) {
            return null;
        } else {
            for (Map<String, String> row : results) {
                currencies.add(new Currency(row.get("name"), row.get("symbol"), Double.parseDouble(row.get("rate"))));
            }
            return currencies;
        }
    }

    public static void main(String[] args) {
        // Test readAll()
        CurrencyDAO currencyDAO = new CurrencyDAO();
        List<Currency> currencies = currencyDAO.readAll();
        for (Currency currency : currencies) {
            System.out.println(currency);
        }
        // Test read()
        Currency currency = currencyDAO.read("USD");
        System.out.println(currency);
        // Test delete()
        currencyDAO.delete(currency);
        System.out.println(currencyDAO.readAll());
        // Test create()
        currencyDAO.create(new Currency("USD", "$", 1.0));
        System.out.println(currencyDAO.readAll());
        System.out.println(currencyDAO.read("USD").getExchangeRate());
        // Test update()
        currencyDAO.update(new Currency("USD", "$", 7.0));
        System.out.println(currencyDAO.readAll());
        System.out.println(currencyDAO.read("USD").getExchangeRate());
    }
}

package Stock;

import DataBase.DataBase;
import Money.Money;
import Money.Currency;
import Money.*;
import Utils.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StockDao implements DAO<Stock> {
    private static DataBase dataBase = new DataBase();

    private static CurrencyDAO currencyDAO = new CurrencyDAO();

    @Override
    public void create(Stock stock) {
        String sql = "INSERT into Stock (stock_name, current_price) values (?, ?)";
        dataBase.execute(sql,new String[]{stock.getStockName(),String.valueOf((double)(stock.getCurrentPrice().getAmount()))});
    }

    @Override
    public void update(Stock stock) {
        String sql = "UPDATE Stock SET current_price = ? WHERE stock_name = ?";
        dataBase.execute(sql,new String[]{String.valueOf((double)(stock.getCurrentPrice().getAmount())),stock.getStockName()});
    }

    @Override
    public void delete(Stock stock) {
        String sql = "DELETE FROM Stock WHERE stock_name = ?";
        dataBase.execute(sql,new String[]{stock.getStockName()});
    }

    public boolean checkStockExist(String name){
        String sql = "SELECT * FROM Stock WHERE stock_name = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{name});
        if (results.size() == 0) {
            return false;
        }
        // if result contains more than one row, throw exception
        if (results.size() > 1) {
            throw new RuntimeException("More than one row returned");
        }
        return true;
    }

    public Stock read(String name){
        String sql = "SELECT * FROM Stock WHERE stock_name = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{name});
        if (results.size() == 0) {
            return null;
        }
        // if result contains more than one row, throw exception
        if (results.size() > 1) {
            throw new RuntimeException("More than one row returned");
        }
        Map<String, String> row = results.get(0);
        Money money = new Money(Double.parseDouble(row.get("current_price")),currencyDAO.read("USD"));
        return new Stock(Integer.parseInt(row.get("stock_id")),row.get("stock_name"),money);
    }

    public Stock readByID(int id){
        String sql = "SELECT * FROM Stock WHERE stock_id = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{String.valueOf(id)});
        if (results.size() == 0) {
            return null;
        }
        // if result contains more than one row, throw exception
        if (results.size() > 1) {
            throw new RuntimeException("More than one row returned");
        }
        Map<String, String> row = results.get(0);
        Money money = new Money(Double.parseDouble(row.get("current_price")),currencyDAO.read("USD"));
        return new Stock(Integer.parseInt(row.get("stock_id")),row.get("stock_name"),money);
    }

    public List<Stock> readAll(){
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM Stock";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{});
        if (results.size() == 0) {
            return null;
        }
        for(Map<String,String> row:results){
            Money money = new Money(Double.parseDouble(row.get("current_price")),currencyDAO.read("USD"));
            stocks.add(new Stock(Integer.parseInt(row.get("stock_id")),row.get("stock_name"),money));
        }
        return stocks;
    }
}

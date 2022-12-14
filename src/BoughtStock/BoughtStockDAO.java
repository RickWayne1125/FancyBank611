package BoughtStock;

import Account.Security.SecurityAccount;
import DataBase.DataBase;
import Utils.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoughtStockDAO implements DAO<BoughtStock> {
    private static DataBase dataBase = new DataBase();

    @Override
    public void create(BoughtStock boughtStock) {
        String sql = "INSERT INTO BoughtStock (account_no, stock_id, stock_unit, stock_price) VALUES (?,?,?,?)";
        dataBase.execute(sql,new String[]{String.valueOf(boughtStock.getAccountNo()),String.valueOf(boughtStock.getStockId()),String.valueOf(boughtStock.getStockUnit()),String.valueOf(boughtStock.getStockPrice())});
    }

    @Override
    public void update(BoughtStock boughtStock) {
        String sql = "UPDATE BoughtStock SET stock_unit = ?, stock_price = ? WHERE account_no = ? AND stock_id = ?";
        dataBase.execute(sql,new String[]{String.valueOf(boughtStock.getStockUnit()),String.valueOf(boughtStock.getStockPrice()),String.valueOf(boughtStock.getAccountNo()),String.valueOf(boughtStock.getStockId())});
    }

    @Override
    public void delete(BoughtStock boughtStock) {
        String sql = "DELETE FROM BoughtStock WHERE account_no = ? AND stock_id = ?";
        dataBase.execute(sql,new String[]{String.valueOf(boughtStock.getAccountNo()),String.valueOf(boughtStock.getStockId())});
    }

    public List<BoughtStock> readByAccount(SecurityAccount securityAccount){
        List<BoughtStock> boughtStocks = new ArrayList<>();
        String sql = "SELECT * FROM BoughtStock WHERE account_no = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{String.valueOf(securityAccount.getAccountNumber())});
        if (results.size() == 0) {
            return null;
        }
        for(Map<String,String> row:results){
            BoughtStock tmp = new BoughtStock(Integer.parseInt(row.get("account_no")),Integer.parseInt(row.get("stock_id")),Integer.parseInt(row.get("stock_unit")),Double.parseDouble(row.get("stock_price")));
            boughtStocks.add(tmp);
        }
        return boughtStocks;
    }
}

package Stock;

import Money.Money;
import Money.Currency;

import java.util.List;


public class StockService {
    private static StockDao stockDao= new StockDao();

    public static boolean addNewStock(String name, int price){
        Money money = new Money(price,new Currency("USD","$",1));
        Stock stock = new Stock(1,name,money);
        stockDao.create(stock);
        return true;
    }

    public static boolean updateStock(String name, int price){
        Money money = new Money(price,new Currency("USD","$",1));
        Stock stock = new Stock(1,name,money);
        if(!stockDao.checkStockExist(name)){
            return false;
        }
        stockDao.update(stock);
        return true;
    }

    public static boolean deleteStock(String name){
        Money money = new Money(0,new Currency("USD","$",1));
        Stock stock = new Stock(1,name,money);
        if(!stockDao.checkStockExist(name)){
            return false;
        }
        stockDao.delete(stock);
        return true;
    }

    public List<Stock> getAllStock(){
        return stockDao.readAll();
    }

    public static void main(String[] args){
        //addNewStock("google",5);
        //updateStock("google",3);
        System.out.println(stockDao.readAll());
    }
}

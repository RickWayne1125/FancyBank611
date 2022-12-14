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

    public static boolean updateStock(int id, int price){
        Money money = new Money(price,new Currency("USD","$",1));
        Stock stock =getStockByID(id);
        if(stock == null){
            return false;
        }
        stock.setCurrentPrice(money);
        stockDao.update(stock);
        return true;
    }

    public static boolean deleteStock(int id){
        Stock stock = getStockByID(id);
        if(stock == null){
            return false;
        }
        stockDao.delete(stock);
        return true;
    }

    public static List<Stock> getAllStock(){
        return stockDao.readAll();
    }

    public static Stock getStockByID(int id){
        return stockDao.readByID(id);
    }

    public double getCurrentStockPriceByID(int id){
        return getStockByID(id).getCurrentPrice().getAmount();
    }

    public static void main(String[] args){
        //addNewStock("google",5);
        //updateStock("google",3);
        System.out.println(stockDao.readAll());
    }
}

package BoughtStock;

import Account.Security.SecurityAccount;
import Stock.StockDao;
import Stock.StockService;

import java.util.List;

public class BoughtStockService {
    private static BoughtStockDAO boughtStockDAO = new BoughtStockDAO();
    private static StockService stockService = new StockService();

    public static List<BoughtStock> viewBoughtStock(SecurityAccount securityAccount){
        return boughtStockDAO.readByAccount(securityAccount);
    }

    public double getUnrealized(SecurityAccount securityAccount){
        List<BoughtStock> boughtStocks = boughtStockDAO.readByAccount(securityAccount);
        double totalValue = 0;
        for(BoughtStock boughtStock:boughtStocks){
            totalValue+= boughtStock.getStockUnit()*stockService.getCurrentStockPriceByID(boughtStock.getStockId());
        }
        return totalValue;
    }

    public static void main(String[] args){
        SecurityAccount securityAccount = new SecurityAccount(1,"bank","bank");
        System.out.println(viewBoughtStock(securityAccount).get(0).getAccountNo());
    }
}

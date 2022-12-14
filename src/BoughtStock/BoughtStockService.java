package BoughtStock;

import Account.Security.SecurityAccount;
import Stock.StockDao;

import java.util.List;

public class BoughtStockService {
    private static BoughtStockDAO boughtStockDAO = new BoughtStockDAO();

    public static List<BoughtStock> viewBoughtStock(SecurityAccount securityAccount){
        return boughtStockDAO.readByAccount(securityAccount);
    }

    public static void main(String[] args){
        SecurityAccount securityAccount = new SecurityAccount(1,"bank","bank");
        System.out.println(viewBoughtStock(securityAccount).get(0).getAccountNo());
    }
}

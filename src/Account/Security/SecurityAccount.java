package Account.Security;

import Account.Account;
import Money.Money;
import Stock.Stock;

import java.util.List;
import java.util.Map;

public class SecurityAccount extends Account {
    private Map<Stock, Integer> stocks;
    private Money realized;
    private Money unrealized;

    public SecurityAccount(int accountNumber,String routingNumber, String swiftCode){
        super(accountNumber,routingNumber,swiftCode);
    }


    // everything in stock market will need USD
    public boolean buyStockByUnit(Stock stock, int unit){
        double requireAmount = stock.getCurrentPrice().getAmount()*unit;
        //double currentBalance = super.getCurrentBalance();
        return true;
    }

    public boolean sellStockByUnit(Stock stock, int unit){
        return true;
    }



}

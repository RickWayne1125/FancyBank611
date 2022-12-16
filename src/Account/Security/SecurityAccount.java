package Account.Security;

import Account.Account;
import Money.Money;
import Stock.Stock;
import Account.AccountType;

import java.util.HashMap;
import java.util.Map;

public class SecurityAccount extends Account {
    private Map<Stock, Integer> stocks; // stock
    private Money realized;
    private Money unrealized;
    private Money totalPaid;    // total paid for all stocks, this is used to calculate the unrealized gain/loss
                                // unrealized = current value - total paid

    public SecurityAccount(int accountNumber,String routingNumber, String swiftCode){
        super(accountNumber,routingNumber,swiftCode);
        super.setType(AccountType.SECURITY);
        setInterestRate(0.0);
        stocks = new HashMap<Stock, Integer>();
    }

    public SecurityAccount(String routingNumber, String swiftCode){
        super(routingNumber,swiftCode);
        super.setType(AccountType.SECURITY);
        setInterestRate(0.0);
        stocks = new HashMap<Stock, Integer>();
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

    public void setRealized(Money realized) {
        this.realized = realized;
    }

    public Money getRealized() {
        return realized;
    }

    public void setUnrealized(Money unrealized) {
        this.unrealized = unrealized;
    }

    public Money getUnrealized() {
        return unrealized;
    }

    public void setTotalPaid(Money totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Money getTotalPaid() {
        return totalPaid;
    }

    public void setStocks(Map<Stock, Integer> stocks) {
        this.stocks = stocks;
    }

    public Map<Stock, Integer> getStocks() {
        return stocks;
    }
}

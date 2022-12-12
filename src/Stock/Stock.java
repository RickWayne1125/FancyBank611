package Stock;

import Money.Money;

public class Stock {
    private int stockId;
    private String stockName;
    private Money currentPrice;

    public Stock(int stockId,String stockName, Money currentPrice){
        this.stockId =stockId;
        this.stockName=stockName;
        this.currentPrice =currentPrice;
    }

    public int getStockId() {
        return stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public Money getCurrentPrice() {
        return currentPrice;
    }
}

package BoughtStock;

public class BoughtStock {
    private int accountNo;
    private int stockId;
    private int stockUnit;
    private double stockPrice;

    public BoughtStock(int accountNo, int stockId, int stockUnit, double stockPrice) {
        this.accountNo = accountNo;
        this.stockId = stockId;
        this.stockUnit = stockUnit;
        this.stockPrice = stockPrice;
    }

    public int getAccountNo() {
        return this.accountNo;
    }

    public int getStockId() {
        return this.stockId;
    }

    public int getStockUnit() {
        return this.stockUnit;
    }

    public double getStockPrice() {
        return stockPrice;
    }
}


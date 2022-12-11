package Money;

public class Currency {
    private String currencyName;
    private String symbol;
    private double exchangeRate;    // Exchange rate to USD
    public Currency(String currencyName,String symbol,double exchangeRate){
        this.currencyName =currencyName;
        this.symbol=symbol;
        this.exchangeRate = exchangeRate;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}

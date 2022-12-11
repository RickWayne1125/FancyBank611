package Money;

public class Money {
    private double amount;
    private Currency currency;
    public Money(double amount,Currency currency){
        this.amount =amount;
        this.currency=currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void convert(Currency currency){
        double oldRate = this.currency.getExchangeRate();
        double newRate = this.currency.getExchangeRate();
        double exchangeRate = oldRate/newRate;
        this.amount = this.amount/exchangeRate;
    }
}

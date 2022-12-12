package Money;

public class Money {
    private double amount;
    private Currency currency;
    private Integer accountNumber;

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
        this.accountNumber = null;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void convert(Currency currency) {
        double oldRate = this.currency.getExchangeRate();
        double newRate = currency.getExchangeRate();
        double exchangeRate = oldRate / newRate;
        this.amount = this.amount / exchangeRate;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String toString() {
        return this.currency.getSymbol() + " " + this.amount;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public Money clone() throws CloneNotSupportedException {
        return new Money(this.amount, this.currency);
    }
}

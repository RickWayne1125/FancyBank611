package Money;

public class MoneyService {
    private static final MoneyDAO moneyDAO = new MoneyDAO();
    private static CurrencyDAO currencyDAO = new CurrencyDAO();

    public static boolean update(Money money, Double amount) {
        if (amount < 0) {
            return false;
        }
        money.setAmount(money.getAmount() + amount);
        moneyDAO.update(money);
        return true;
    }

    public static boolean convert(Money money, String currencyName) {
        Currency currency = currencyDAO.read(currencyName);
        if (currency == null) {
            return false;
        }
        money.convert(currency);
        moneyDAO.update(money);
        return true;
    }
}

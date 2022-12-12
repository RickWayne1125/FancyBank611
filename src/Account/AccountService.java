package Account;

import Money.Money;
import Money.MoneyService;

public class AccountService {
    private static AccountDAO accountDAO = new AccountDAO();

    public static void deposit(Account account, Money money) {
        // check money list in account
        for (Money m : account.getCurrentBalance()) {
            if (m.getCurrency().equals(money.getCurrency())) {
                MoneyService.update(m, money.getAmount());
                return;
            }
        }
        // if not found, add new money to account
        MoneyService.create(money);
        account.getCurrentBalance().add(money);
        accountDAO.update(account);
    }

    public static boolean withdraw(Account account, Money money) {
        // check money list in account
        for (Money m : account.getCurrentBalance()) {
            if (m.getCurrency().equals(money.getCurrency())) {
                if (MoneyService.update(m, -money.getAmount())) {
                    accountDAO.update(account);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static boolean transfer(Account fromAccount, Account toAccount, Money money) {
        if (withdraw(fromAccount, money)) {
            deposit(toAccount, money);
            return true;
        }
        return false;
    }

    public static boolean convertCurrency(Account account, String originalCurrency, String targetCurrency) {
        // check money list in account
        for (Money m : account.getCurrentBalance()) {
            if (m.getCurrency().getCurrencyName().equals(originalCurrency)) {
                if (MoneyService.convert(m, targetCurrency)) {
                    accountDAO.update(account);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static Account refreshAccount(Account account) {
        return accountDAO.readByAccountNumber(account.getAccountNumber());
    }

    public static boolean buyCurrency(Account account, String originalCurrency, Money moneyOfNewCurrency) throws CloneNotSupportedException {
        // calculate the amount of original currency needed
        Money moneyOfOriginalCurrency = moneyOfNewCurrency.clone();
        MoneyService.convert(moneyOfOriginalCurrency, originalCurrency);    // convert to original currency
        for (Money m : account.getCurrentBalance()) {
            if (m.getCurrency().getCurrencyName().equals(originalCurrency)) {
                if (MoneyService.update(m, -moneyOfOriginalCurrency.getAmount())) {
                    deposit(account, moneyOfNewCurrency);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}

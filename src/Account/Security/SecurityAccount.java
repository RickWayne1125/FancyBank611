package Account.Security;

import Account.Account;
import Money.Money;
import Stock.Stock;

import java.util.Map;

public class SecurityAccount extends Account {
    private Map<Stock, Integer> stocks;
    private Money realized;
    private Money unrealized;
}

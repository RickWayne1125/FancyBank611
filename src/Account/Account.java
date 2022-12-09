package Account;

import Money.Money;

import java.util.List;

public abstract class Account {
    private int accountNumber;
    private String routingNumber;
    private String swiftCode;
    private List<Money> currentBalance;
}

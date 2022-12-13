package Account;

import Utils.Config;

public class Checking extends Account {
    public Checking(int accountNumber, String routingNumber, String swiftCode) {
        super(accountNumber, routingNumber, swiftCode);
        super.setType(AccountType.CHECKING);
        setInterestRate(Config.DEFAULT_CHECKING_INTEREST_RATE);
    }

    public Checking(String routingNumber, String swiftCode) {
        super(routingNumber, swiftCode);
        super.setType(AccountType.CHECKING);
        setInterestRate(Config.DEFAULT_CHECKING_INTEREST_RATE);
    }
}

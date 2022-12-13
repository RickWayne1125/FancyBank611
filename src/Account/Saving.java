package Account;

import Utils.Config;

public class Saving extends Account {
    public Saving(int accountNumber, String routingNumber, String swiftCode) {
        super(accountNumber, routingNumber, swiftCode);
        super.setType(AccountType.SAVING);
        setInterestRate(Config.DEFAULT_SAVING_INTEREST_RATE);
    }

    public Saving(String routingNumber, String swiftCode) {
        super(routingNumber, swiftCode);
        super.setType(AccountType.SAVING);
        setInterestRate(Config.DEFAULT_SAVING_INTEREST_RATE);
    }

}

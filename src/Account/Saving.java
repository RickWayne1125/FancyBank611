package Account;

public class Saving extends Account {
    public Saving(int accountNumber, String routingNumber, String swiftCode) {
        super(accountNumber, routingNumber, swiftCode);
        super.setType(AccountType.SAVING);
    }

    public Saving(String routingNumber, String swiftCode) {
        super(routingNumber, swiftCode);
        super.setType(AccountType.SAVING);
    }

}

package Account;

public class Checking extends Account {
    public Checking(int accountNumber, String routingNumber, String swiftCode) {
        super(accountNumber, routingNumber, swiftCode);
        super.setType(AccountType.CHECKING);
    }

    public Checking(String routingNumber, String swiftCode) {
        super(routingNumber, swiftCode);
        super.setType(AccountType.CHECKING);
    }
}

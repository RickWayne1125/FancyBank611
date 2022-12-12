package Account;

public class Saving extends Account {
    public Saving(int accountNumber, String routingNumber, String swiftCode) {
        super(accountNumber, routingNumber, swiftCode);
        super.setType(AccountType.SAVING);
    }

}

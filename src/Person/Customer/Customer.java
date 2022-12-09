package Person.Customer;
import Account.Account;
import Person.Person;

import java.util.List;

public class Customer extends Person{
    private boolean hasCollateral;
    private List<Account> accounts;
    public Customer(String firstName, String middleName, String lastName, String email, String password, String contact,
                    String address, boolean hasCollateral) {
        super(firstName, middleName, lastName, email, password, contact, address);
        this.hasCollateral = hasCollateral;
    }

    public Customer(int userId, String firstName, String middleName, String lastName, String email, String password, String contact,
                    String address, boolean hasCollateral) {
        super(userId, firstName, middleName, lastName, email, password, contact, address);
        this.hasCollateral = hasCollateral;
    }
}

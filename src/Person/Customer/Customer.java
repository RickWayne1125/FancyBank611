package Person.Customer;

import Account.Account;
import Person.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends Person {
    //    private boolean hasCollateral;
    private List<Account> accounts;


    public Customer(String username, String firstName, String middleName, String lastName, String email, String password, String contact,
                    String address) {
        super(username, firstName, middleName, lastName, email, password, contact, address);
        this.accounts = new ArrayList<>();
//        this.hasCollateral = hasCollateral;
    }

//    public void setHasCollateral(boolean hasCollateral) {
//        this.hasCollateral = hasCollateral;
//    }
//
//    public boolean getHasCollateral() {
//        return hasCollateral;
//    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}

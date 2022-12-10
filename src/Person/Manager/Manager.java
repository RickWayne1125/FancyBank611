package Person.Manager;

import Person.Customer.Customer;
import Person.Person;
import Stock.Stock;

import java.util.List;

public class Manager extends Person {
    private List<Stock> stocks;
    private List<Customer> customers;

    public Manager(String firstName, String middleName, String lastName, String email, String password, String contact,
                   String address, List<Stock> stocks, List<Customer> customers) {
        super(firstName, middleName, lastName, email, password, contact, address);
        this.stocks = stocks;
        this.customers = customers;
    }

    public Manager(int userId, String firstName, String middleName, String lastName, String email, String password, String contact,
                   String address, List<Stock> stocks, List<Customer> customers) {
        super(userId, firstName, middleName, lastName, email, password, contact, address);
        this.stocks = stocks;
        this.customers = customers;
    }
}

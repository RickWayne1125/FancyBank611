package Person.Manager;

import Person.Customer.Customer;
import Person.Person;
import Stock.Stock;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Person {
    private List<Stock> stocks;
    private List<Customer> customers;

    public Manager(String username, String firstName, String middleName, String lastName, String email, String password, String contact,
                   String address) {
        super(username, firstName, middleName, lastName, email, password, contact, address);
        this.stocks = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}

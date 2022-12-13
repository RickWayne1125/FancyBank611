package Person.Manager;

import Account.Account;
import DataBase.DataBase;
import Money.CurrencyDAO;
import Person.Customer.Customer;
import Person.Customer.CustomerDAO;
import Transact.Transaction;
import Utils.DAO;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

public class ManagerDAO implements DAO<Manager> {
    private static DataBase dataBase = new DataBase();

    public boolean checkIfManagerExists(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{username});
        if (results.size() == 0) {
            return false;
        }
        if (results.size() > 1) {
            throw new RuntimeException("More than one row returned");
        }
        return true;
    }

    @Override
    public void create(Manager manager) {
        String sql = "INSERT INTO User (username, first_name, middle_name, last_name, email, contact, password, address, " +
                "is_customer, last_login) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        dataBase.execute(sql, new String[]{manager.getUsername(), manager.getFirstName(), manager.getMiddleName(),
                manager.getLastName(), manager.getEmail(), manager.getContact(), manager.getPassword(), manager.getAddress(),
                "0", manager.getLastLogin().toString()});
    }

    @Override
    public void update(Manager manager) {

    }

    @Override
    public void delete(Manager manager) {

    }

    public Manager read(String username){
        String sql = "SELECT * FROM User WHERE username = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{username});
        if (results.size() == 0) {
            return null;
        }
        if (results.size() > 1) {
            throw new RuntimeException("More than one row returned");
        }
        Map<String, String> row = results.get(0);
        Manager manager = new Manager(row.get("username"), row.get("first_name"), row.get("middle_name"),
                row.get("last_name"), row.get("email"), row.get("password"), row.get("contact"), row.get("address"));

        return manager;
    }

    // get a single customer
    public Customer getCustomer(String username){
        Customer customer = new CustomerDAO().read(username);
        return customer;
    }

    // get all customers
    public List<Customer> getAllCustomer(){
        String sql = "SELECT * FROM User WHERE is_customer = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{String.valueOf(1)});
        List<Customer> customers = new ArrayList<>();
        if (results.size() == 0) {
            return customers;
        }
        for (Map<String, String> result : results) {
            Customer customer = new CustomerDAO().read(result.get("username"));
            customers.add(customer);
        }
        return customers;
    }
}

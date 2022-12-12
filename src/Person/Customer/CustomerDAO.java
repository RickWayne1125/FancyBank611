package Person.Customer;

import Account.Account;
import Account.AccountDAO;
import DataBase.DataBase;
import Person.Person;
import Utils.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerDAO implements DAO<Customer> {
    private static DataBase dataBase = new DataBase();

    public boolean checkIfCustomerExists(String username) {
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
    public void create(Customer customer) {
        String sql = "INSERT INTO User (username, first_name, middle_name, last_name, email, contact, password, address, " +
                "is_customer, last_login) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        dataBase.execute(sql, new String[]{customer.getUsername(), customer.getFirstName(), customer.getMiddleName(),
                customer.getLastName(), customer.getEmail(), customer.getContact(), customer.getPassword(), customer.getAddress(),
                "1", customer.getLastLogin().toString()});
        // TODO: Update all accounts of customer (including modifying Loan table, BoughtStock table, etc.)
    }

    @Override
    public void delete(Customer customer) {
        // TODO: Update all accounts of customer (including modifying Loan table, BoughtStock table, etc.)
    }

    @Override
    public void update(Customer customer) {
        // TODO: Update all accounts of customer (including modifying Loan table, BoughtStock table, etc.)
    }

    public Customer read(String username) {
        // Load basic information from User table
        String sql = "SELECT * FROM User WHERE username = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{username});
        if (results.size() == 0) {
            return null;
        }
        if (results.size() > 1) {
            throw new RuntimeException("More than one row returned");
        }
        Map<String, String> row = results.get(0);
        Customer customer = new Customer(row.get("username"), row.get("first_name"), row.get("middle_name"),
                row.get("last_name"), row.get("email"), row.get("password"), row.get("contact"), row.get("address"));
        // Read all accounts of customer
        List<Account> accounts = new ArrayList<>();
        sql = "SELECT * FROM Account WHERE username = ?";
        results = dataBase.query(sql, new String[]{username});
        AccountDAO accountDAO = new AccountDAO();
        if (results == null) {  // No accounts
            return customer;
        }
        for (Map<String, String> result : results) {
            accounts.add(accountDAO.readByAccountNumber(Integer.parseInt(result.get("account_number"))));
        }
        customer.setAccounts(accounts);
        return customer;
    }
}

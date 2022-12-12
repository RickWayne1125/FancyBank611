package Person.Customer;

import Utils.IO;
import Utils.MessageType;

import java.util.Date;

public class CustomerService {
    private static CustomerDAO customerDAO = new CustomerDAO();

    public static void createCustomer(Customer customer) {
        customerDAO.create(customer);
    }

    public static Customer loginCustomer(String username, String password) {
        IO.displayMessage("CustomerService: loginCustomer", MessageType.INFO);
        Customer customer = customerDAO.read(username);
        if (customer != null && customer.getPassword().equals(password)) {
            // TODO: Update all the interests then update the last login time
            Date date = new Date();
            customer.setLastLogin(date);
            customerDAO.update(customer);
            return customer;
        }
        return null;
    }

    public static boolean registerCustomer(Customer customer) {
        if (customerDAO.checkIfCustomerExists(customer.getUsername())) {
            return false;
        }
        customer.setLastLogin(new Date());
        customerDAO.create(customer);
        return true;
    }

    public static void main(String[] args) {
        // Test
        // Create a customer
        Customer customer = new Customer("rick", "Rick", "test", "test",
                "test", "test", "test", "test");
        // Register the customer
        System.out.println(registerCustomer(customer));
        // Login the customer
        Customer customer1 = loginCustomer("rick", "test");
        System.out.println(customer1);
    }
}

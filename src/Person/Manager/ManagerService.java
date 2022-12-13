package Person.Manager;

import Person.Customer.Customer;
import Person.Customer.CustomerDAO;
import Transact.Transaction;
import Transact.TransactionDAO;
import Utils.IO;
import Utils.MessageType;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class ManagerService {
    private static CustomerDAO customerDAO = new CustomerDAO();
    private static ManagerDAO managerDAO = new ManagerDAO();

    // get daily transaction report
    public static List<Transaction> readDailyReport(String day) throws ParseException {
        // todo: finish transact part first
        List<Transaction> transactions = new TransactionDAO().readByDay(day);
        return transactions;
    }

    public static boolean registerManager(Manager manager) {
        if (managerDAO.checkIfManagerExists(manager.getUsername())) {
            return false;
        }
        manager.setLastLogin(new Date());
        managerDAO.create(manager);
        return true;
    }

    // login
    public static Manager loginManager(String username, String password){
        IO.displayMessage("ManagerService: loginManger", MessageType.INFO);
        Manager manager = managerDAO.read(username);
        if (manager != null && manager.getPassword().equals(password)) {
            Date date = new Date();
            manager.setLastLogin(date);
            return manager;
        }
        else{
            //todo: manager not exist or wrong passwork
        }
        return null;
    }

    public static Customer viewCustomerByName(String username){
        return managerDAO.getCustomer(username);
    }

    public static List<Customer> viewAllCustomer(){
        return managerDAO.getAllCustomer();
    }

    public static void main(String[] args) throws ParseException {
        // Test
        // Create a customer
        Manager manager = new Manager("mira", "Mira", "test", "test",
                "test", "test", "test", "test");
        // Register the customer
        System.out.println(registerManager(manager));
        // Login
        Manager manager1 = loginManager("mira","test");
        //System.out.println(manager1);
        //System.out.println(viewAllCustomer());
        System.out.println(readDailyReport("12-13-2022"));
    }
}

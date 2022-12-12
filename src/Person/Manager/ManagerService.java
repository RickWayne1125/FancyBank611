package Person.Manager;

import Person.Customer.Customer;
import Person.Customer.CustomerDAO;
import Utils.IO;
import Utils.MessageType;

import java.util.Date;

public class ManagerService {
    private static CustomerDAO customerDAO = new CustomerDAO();
    private static ManagerDAO managerDAO = new ManagerDAO();

    // get daily transaction report
    public boolean readDailyReport(){
        // todo: finish transact part first
        return true;
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
            // TODO: Update all the interests then update the last login time
            Date date = new Date();
            manager.setLastLogin(date);
            return manager;
        }
        else{
            //todo: manager not exist or wrong passwork
        }
        return null;
    }

    public static void main(String[] args) {
        // Test
        // Create a customer
        Manager manager = new Manager("mira", "Mira", "test", "test",
                "test", "test", "test", "test");
        // Register the customer
        System.out.println(registerManager(manager));
        // Login
        Manager manager1 = loginManager("mira","test");
        System.out.println(manager1);
    }
}

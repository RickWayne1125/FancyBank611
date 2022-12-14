package Person.Customer;

import Account.AccountService;
import Account.Checking;
import Account.Loan.Loan;
import Account.Loan.LoanService;
import Account.Saving;
import Utils.Config;
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
        // check if the user is a customer
        if (customer != null && customer.getPassword().equals(password)) {
            // TODO: Update all the interests then update the last login time
            Date date = new Date();
            customer.setLastLogin(date);
            customerDAO.update(customer);
            return customer;
        }
        return null;
    }

    public static void setHasCollateral(Customer customer, boolean hasCollateral) {
        customer.setHasCollateral(hasCollateral);
        customerDAO.update(customer);
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
        // Open an account
        AccountService accountService = new AccountService();
        Checking checking = new Checking(123456789, "checking1", "checking1");
        checking.setInterestRate(Config.DEFAULT_INTEREST_RATE);
        checking.setUsername(customer1.getUsername());
        accountService.openAccount(customer1, checking);
        System.out.println(accountService.getAccountByAccountNumber(123456789));
        Saving saving = new Saving(987654321, "saving1", "saving1");
        saving.setInterestRate(Config.DEFAULT_INTEREST_RATE);
        saving.setUsername(customer1.getUsername());
        accountService.openAccount(customer1, saving);
        System.out.println(accountService.getAccountByAccountNumber(987654321));
        Date startDate = new Date();    // Current date
        Date endDate = new Date();      // Date after 1 year
        endDate.setYear(endDate.getYear() + 1);
        Loan loan = new Loan("loan1", "loan1", Config.DEFAULT_INTEREST_RATE, startDate, endDate);
        loan.setInterestRate(Config.DEFAULT_INTEREST_RATE);
        loan.setUsername(customer1.getUsername());
        LoanService loanService = new LoanService();
        setHasCollateral(customer1, true);
        loanService.requestLoan(customer1, loan);
        System.out.println(loanService.getLoan(loan.getAccountNumber()));
        // approve the loan
        loanService.approveLoan(loan);
        System.out.println(loanService.getLoan(loan.getAccountNumber()));
    }
}

import Account.Account;
import Account.Loan.Loan;
import Account.Security.SecurityAccount;
import Money.Money;
import Money.Currency;
import Person.Customer.Customer;
import Person.Customer.CustomerService;
import Person.Manager.Manager;
import Person.Manager.ManagerService;
import Stock.Stock;
import Transact.Transaction;
import Account.AccountType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    /* Services */
    private static CustomerService customerService = new CustomerService();
    private static ManagerService managerService = new ManagerService();

    /* General Functions */
    // Get All Currency
    public static List<Currency> getAllCurrency() {
        // TODO: implement this function
        // This method will check the database and return all the updated currency
        return null;
    }

    // Get All Stocks
    public static List<Stock> getAllStock() {
        // TODO: implement this function
        // This method will check the database and return all the updated stock
        return null;
    }

    // Get All Currency Map
    public static Map<String, Currency> getAllCurrencyMap() {
        List<Currency> currencyList = getAllCurrency();
        Map<String, Currency> currencyMap = new HashMap<>();
        for (Currency currency : currencyList) {
            currencyMap.put(currency.getCurrencyName(), currency);
        }
        return currencyMap;
    }

    // Get All Stock Map
    public static Map<String, Stock> getAllStockMap() {
        List<Stock> stockList = getAllStock();
        Map<String, Stock> stockMap = new HashMap<>();
        for (Stock stock : stockList) {
            stockMap.put(stock.getStockName(), stock);
        }
        return stockMap;
    }

    /* Customer Functions */
    // Customer Login
    public Customer loginCustomer(String username, String password) {
        // If the username and password are correct, return the customer object, otherwise return null
        return customerService.loginCustomer(username, password);
    }

    // Customer Register
    public boolean registerCustomer(Customer customer) {
        // TODO: implement this method
        // If the customer is successfully registered, return true, otherwise return false
        return false;
    }

    // Get Account Details (This can be accessed by using the customer object)

    // Refresh Account
    public Account refreshAccount(Account account) {
        // TODO: implement this method
        // This method will check the database and return the updated account object
        return null;
    }

    // Deposit
    public boolean deposit(Account account, Money money) {
        // TODO: implement this method
        // If the deposit is successful, return true, otherwise return false
        return false;
    }

    // Withdraw
    public boolean withdraw(Account account, Money money) {
        // TODO: implement this method
        // If the withdraw is successful, return true, otherwise return false
        return false;
    }

    // Transfer
    public boolean transfer(Account fromAccount, Account toAccount, Money money) {
        // TODO: implement this method
        // If the transfer is successful, return true, otherwise return false
        return false;
    }

    // Get Transaction History (This can be accessed by using the getTransactionHistory() in account object)
    // However, this method still needs to be implemented when the transaction history needs to be updated
    public List<Transaction> getTransactionHistory(Account account) {
        // TODO: implement this method
        // This method will check the database and update the transaction history in the account object
        return null;
    }

    // Open Account
    public boolean openAccount(Customer customer, Account account, AccountType accountType) {
        // TODO: implement this method
        // If the account is successfully opened, return true, otherwise return false
        // The account type need to be specified
        return false;
    }

    // Get Bought Stock Details (This can be accessed by using the SecurityAccount object)

    // Buy Stock
    public boolean buyStock(SecurityAccount account, Stock stock, int unit) {
        // TODO: implement this method
        // If the stock is successfully bought, return true, otherwise return false
        return false;
    }

    // Sell Stock
    public boolean sellStock(SecurityAccount account, Stock stock, int unit) {
        // TODO: implement this method
        // If the stock is successfully sold, return true, otherwise return false
        return false;
    }

    // Add Loan
    public boolean addLoan(Customer customer, Money money) {
        // TODO: implement this method
        // This method will add a new loan account to the customer
        // If the loan is successfully added, return true, otherwise return false
        return false;
    }

    // Pay Loan
    public boolean payLoan(Loan account, Money money) {
        // TODO: implement this method
        // If the loan is successfully paid, return true, otherwise return false
        return false;
    }

    // Convert Currency
    public boolean convertCurrency(Account account, String fromCurrency, String toCurrency, double amount) {
        // TODO: implement this method
        // If the currency is successfully converted, return true, otherwise return false
        return false;
    }

    /* Manager Functions */
    // ManagerLogin
    public Manager loginManager(String username, String password) {
        // TODO: implement this method
        // If the username and password are correct, return the manager object, otherwise return null
        return null;
    }

    // Get Customer By Username
    public Customer getCustomerByUsername(String username) {
        // TODO: implement this method
        // Return the customer object
        return null;
    }

    // Add Stock
    public boolean addStock(Stock stock) {
        // TODO: implement this method
        // If the stock is successfully added, return true, otherwise return false
        return false;
    }

    // Delete Stock
    public boolean deleteStock(Stock stock) {
        // TODO: implement this method
        // If the stock is successfully deleted, return true, otherwise return false
        return false;
    }

    // Update Stock
    public boolean updateStock(Stock stock) {
        // TODO: implement this method
        // If the stock is successfully updated, return true, otherwise return false
        return false;
    }
}

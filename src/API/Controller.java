package API;

import Account.Account;
import Account.AccountService;
import Account.Loan.Loan;
import Account.Loan.LoanService;
import Account.Security.SecurityAccount;
import Account.Security.SecurityService;
import BoughtStock.BoughtStock;
import BoughtStock.BoughtStockService;
import Money.Money;
import Money.MoneyService;
import Money.Currency;
import Person.Customer.Customer;
import Person.Customer.CustomerService;
import Person.Manager.Manager;
import Person.Manager.ManagerService;
import Stock.Stock;
import Stock.StockService;
import Transact.Transaction;
import Account.AccountType;
import Transact.TransactionService;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    /* Services */
    private static CustomerService customerService = new CustomerService();
    private static ManagerService managerService = new ManagerService();
    private static AccountService accountService = new AccountService();
    private static TransactionService transactionService = new TransactionService();
    private static LoanService loanService = new LoanService();
    private static MoneyService moneyService = new MoneyService();

    private static StockService stockService = new StockService();

    private static SecurityService securityService = new SecurityService();
    private static BoughtStockService boughtStockService = new BoughtStockService();

    /* General Functions */
    // Get All Currency
    public static List<Currency> getAllCurrency() {
        // This method will check the database and return all the updated currency
        return moneyService.getAllCurrency();
    }

    // Get All Stocks
    public static List<Stock> getAllStock() {
        // This method will check the database and return all the updated stock
        return stockService.getAllStock();
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
        // If the customer is successfully registered, return true, otherwise return false
        return customerService.registerCustomer(customer);
    }

    // Get Account Details (This can be accessed by using the customer object)

    // Refresh Account
    public Account refreshAccount(Account account) {
        // This method will check the database and return the updated account object
        return accountService.refreshAccount(account);
    }

    // Deposit
    public boolean deposit(Account account, Money money) {
        // If the deposit is successful, return true, otherwise return false
        return accountService.deposit(account, money);
    }

    // Withdraw
    public boolean withdraw(Account account, Money money) {
        // If the withdraw is successful, return true, otherwise return false
        return accountService.withdraw(account, money);
    }

    // Transfer
    public boolean transfer(Account fromAccount, Account toAccount, Money money) {
        // If the transfer is successful, return true, otherwise return false
        return accountService.transfer(fromAccount, toAccount, money);
    }

    // Get Transaction History (This can be accessed by using the getTransactionHistory() in account object)
    // However, this method still needs to be implemented when the transaction history needs to be updated
    public List<Transaction> getTransactionHistory(Account account) {
        // This method will check the database and update the transaction history in the account object
        return transactionService.getTransactionsByAccount(account);
    }

    // Open Account
    public boolean openAccount(Customer customer, Account account) {
        // If the account is successfully opened, return true, otherwise return false
        // The account type need to be specified when creating the account object
        return accountService.openAccount(customer, account);
    }

    // open stock account, must indicate the saving and security account, and the initial money at least 1000.0
    // initially, there will be no deals in the security account, set all to be zero
    public static boolean openStock(Account account, SecurityAccount securityAccount,double initAmount){
        return securityService.openStock(account,securityAccount,initAmount);
    }

    // Get Bought Stock Details (This can be accessed by using the SecurityAccount object)

    // Buy Stock
    // must indicate the money goes to which bankAccount
    public boolean buyStock(SecurityAccount account, Account bankAccount ,Stock stock, int unit) {
        // If the stock is successfully bought, return true, otherwise return false
        return securityService.buyStock(account,bankAccount,stock,unit);
    }

    // Sell Stock
    public boolean sellStock(SecurityAccount securityAccount,Account bankAccount, BoughtStock boughtStock, int unit) {
        // If the stock is successfully sold, return true, otherwise return false
        return securityService.sellStock(securityAccount,bankAccount,boughtStock,unit);
    }

    // get bought stock
    public static List<BoughtStock> viewBoughtStock(SecurityAccount securityAccount){
        return boughtStockService.viewBoughtStock(securityAccount);
    }

    // Add Loan
    public boolean requestLoan(Customer customer, Loan loan) {
        // This method will add a new loan account to the customer
        // If the loan is successfully added, return true, otherwise return false
        return loanService.requestLoan(customer, loan);
    }

    // Pay Loan
    public boolean payLoanByCash(Loan account, Money money) {
        // If the loan is successfully paid, return true, otherwise return false
        return loanService.payLoanByCash(account, money);
    }

    public boolean payLoanByTransfer(Loan account, Account fromAccount, Money money) {
        // If the loan is successfully paid, return true, otherwise return false
        return loanService.payLoanByTransfer(account, fromAccount, money);
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
        // If the username and password are correct, return the manager object, otherwise return null
        return managerService.loginManager(username,password);
    }

    // manager sign up
    public boolean registerManager(Manager manager){
        return managerService.registerManager(manager);
    }

    // Get Customer By Username
    public Customer getCustomerByUsername(String username) {
        // Return the customer object
        return managerService.viewCustomerByName(username);
    }

    public List<Customer> getAllCustomer(){
        return managerService.viewAllCustomer();
    }

    //day must be in the format of mm-dd-yyyy
    public List<Transaction> getTransactionByDate(String day) throws ParseException {
        return managerService.readDailyReport(day);
    }

    // Add Stock
//    public boolean addStock(Stock stock) {
//        // If the stock is successfully added, return true, otherwise return false
//        return false;
//    }

    public boolean addStock(String name, int price){
        return StockService.addNewStock(name,price);
    }

    // Delete Stock
//    public boolean deleteStock(Stock stock) {
//        // If the stock is successfully deleted, return true, otherwise return false
//        return false;
//    }

    public boolean deleteStock(String name){
        return StockService.deleteStock(name);
    }

    // Update Stock
//    public boolean updateStock(Stock stock) {
//        // If the stock is successfully updated, return true, otherwise return false
//        return false;
//    }
    public boolean updateStock(String name, int price){
        return StockService.updateStock(name,price);
    }

    // Get Unapproved Loan List
    public List<Loan> getUnapprovedLoanList() {
        return loanService.getUnapprovedLoans();
    }

    // Approve Loan
    public boolean approveLoan(Loan loan) {
        return loanService.approveLoan(loan);
    }
}
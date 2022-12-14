package Account.Security;

import Account.Account;
import Account.AccountDAO;
import Account.AccountType;
import Account.AccountService;
import BoughtStock.BoughtStock;
import BoughtStock.BoughtStockDAO;
import Money.*;
import Person.Customer.Customer;
import Stock.*;
import Money.Currency;

import java.util.ArrayList;
import java.util.List;

public class SecurityService {
    private static SecurityDAO securityDAO = new SecurityDAO();
    private static AccountDAO accountDAO = new AccountDAO();
    private static AccountService accountService = new AccountService();
    private static StockDao stockDao = new StockDao();
    private static BoughtStockDAO boughtStockDAO = new BoughtStockDAO();
    private static CurrencyDAO currencyDAO = new CurrencyDAO();

    public static boolean openStock(Account account, SecurityAccount securityAccount,double initAmount){
        // make sure customer has more than 5000 in saving account.
        double savingAmount=0;
        // get all USD in amount
        if(account.getType().equals(AccountType.SAVING)){
            List<Money> moneyList = account.getCurrentBalance();
            for(Money money:moneyList){
                if (money.getCurrency().getCurrencyName().equalsIgnoreCase("USD")){
                    savingAmount = money.getAmount();
                }
            }
        }
        // if qualified
        if(savingAmount>=5000.0){
            System.out.println("security account created");
            // create account in account database
            accountDAO.create(securityAccount);
            // create account in security database
            securityDAO.create(securityAccount);
            // transfer money from saving to security
            accountService.transfer(account,securityAccount,new Money(initAmount,new Currency("USD","$",1)));
            return true;
        }
        else{
            System.out.println("account has less than 5000 usd");
            return false;
        }
    }

    public static boolean buyStock(SecurityAccount securityAccount,Account bankAccount, Stock stock, int quantity) {
        // update security status
        // get USD amount
        double remainAmount = 0;
        List<Money> moneyList = securityAccount.getCurrentBalance();
        for(Money money:moneyList){
            if (money.getCurrency().getCurrencyName().equalsIgnoreCase("USD")){
                remainAmount = money.getAmount();
            }
        }
        // get require amount
        // mark them into table boughtStock
        double requireAmount = stock.getCurrentPrice().getAmount()*quantity;
        if(remainAmount>=requireAmount){
            // take money away
            accountService.transfer(securityAccount,bankAccount,new Money(requireAmount,currencyDAO.read("USD")));
            // update security account
            Money newTotalPaid = new Money(requireAmount+securityAccount.getTotalPaid().getAmount(),currencyDAO.read("USD"));
            securityAccount.setTotalPaid(newTotalPaid);
            securityDAO.update(securityAccount);
            // mark in table BoughtStock
            boughtStockDAO.create(new BoughtStock(securityAccount.getAccountNumber(), stock.getStockId(),quantity,stock.getCurrentPrice().getAmount()));
            System.out.println("success bought");
            return true;
        }
        else{
            System.out.println("not enough money");
            return false;
        }
    }

    public static boolean sellStock(SecurityAccount securityAccount,Account bankAccount, BoughtStock boughtStock, int quantity) {
        Stock stock = stockDao.readByID(boughtStock.getStockId());
        double sellingAmount = stock.getCurrentPrice().getAmount()*quantity;
        int leftHand = boughtStock.getStockUnit() - quantity;
        if(leftHand<0){
            System.out.println("not enough hands");
            return false;
        }
        // get money
        accountService.transfer(bankAccount,securityAccount,new Money(sellingAmount,currencyDAO.read("USD")));
        // update security account
        Money newRealized = new Money(sellingAmount+securityAccount.getRealized().getAmount(),currencyDAO.read("USD"));
        securityAccount.setRealized(newRealized);
        securityDAO.update(securityAccount);
        // remove from table BoughtStock
        if(leftHand == 0){
            boughtStockDAO.delete(boughtStock);
        }
        // minus sold hands
        else{
            boughtStockDAO.update(new BoughtStock(securityAccount.getAccountNumber(),boughtStock.getStockId(),leftHand,boughtStock.getStockPrice()));
        }
        System.out.println("success sold");
        return true;
    }

    public double getRealizedByAccountNumber(int accountNumber){
        return securityDAO.readByAccountNumber(accountNumber).getRealized().getAmount();
    }

    public static void main(String[] args) {
        SecurityAccount securityAccount1= new SecurityAccount(123,"te","te");
        securityAccount1.setRealized(new Money(0,currencyDAO.read("USD")));
        securityAccount1.setTotalPaid(new Money(0,currencyDAO.read("USD")));


        accountService.deposit(securityAccount1,new Money(10000, currencyDAO.read("USD")));
        Stock stock = stockDao.read("google");
        Account bank = accountDAO.readByAccountNumber(1);
        // test open
        openStock(bank,securityAccount1,5000);
        // test buy
        buyStock(securityAccount1,bank,stock,10);
        // test sell
        List<BoughtStock> boughtStocks = boughtStockDAO.readByAccount(securityAccount1);
        sellStock(securityAccount1,bank,boughtStocks.get(0),5);
    }
}

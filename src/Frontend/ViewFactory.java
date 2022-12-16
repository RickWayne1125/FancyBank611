package Frontend;

import Account.Account;
import Account.AccountType;
import Account.Security.SecurityAccount;
import Person.Customer.Customer;

public class ViewFactory {
    public static AbstractJPanel getLoginPage(){
        return new login();
    }

    public static AbstractJPanel getCustomerMenuPage(){
        return new CustomerMenuView();
    }
    public static AbstractJPanel getManagerMenuPage(){
        return new ManagerMenuView();
    }

    public static AbstractJPanel getAccount(Customer customer, AccountType accountType, Account account, Boolean managerView, Boolean loanAccountView){
        return new AccountView(customer, accountType, account, managerView, loanAccountView);
    }

    public static AbstractJPanel getCustomersListView() {return new CustomersListView();}

    public static AbstractJPanel getCreateEditUserView(String action, String userType) {
        /*
        *   action is either "edit" or "create
        * */
        return new NewUserForm(action, userType);
    }

    public static AbstractJPanel getLoanView(){
        return new LoanAccountsView();
    }

    public static AbstractJPanel getManagerStockView(){
        return new ManagerStocksView();
    }
    public static AbstractJPanel getCustomerDetailView(Customer customer){
        return new CustomerDetailView(customer);
    }
    public static AbstractJPanel getTransactionsView(){
        return new TransactionsView();
    }

    public static AbstractJPanel getLoansView(Boolean hideBackButton, Customer customer, Boolean managerView){
        return new LoansView(hideBackButton, customer, managerView);
    }

    public static AbstractJPanel getSecurityAccount(SecurityAccount account){
        return new SecurityAccountView(account);
    }

    public static AbstractJPanel getTransfersPage(){
        return new MoneyTransferView();
    }
}

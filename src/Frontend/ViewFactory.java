package Frontend;

import Account.AccountType;

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

    public static AbstractJPanel getSavingsAccount(){
        return new AccountView(AccountType.SAVING);
    }

    public static AbstractJPanel getCheckingAccount(){
        return new AccountView(AccountType.CHECKING);
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

}

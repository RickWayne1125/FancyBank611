package Frontend;

import Account.AccountType;

public class ViewFactory {
    public static AbstractJPanel getLoginPage(){
        return new login();
    }

    public static AbstractJPanel getMenuPage(){
        return new Menu();
    }

    public static AbstractJPanel getSavingsAccount(){
        return new AccountView(AccountType.SAVING);
    }

    public static AbstractJPanel getCheckingAccount(){
        return new AccountView(AccountType.CHECKING);
    }
    public static AbstractJPanel getCustomersListView() {return new Customers();}

    public static AbstractJPanel getCreateEditUserView(String action, String userType) {
        /*
        *   action is either "edit" or "create
        * */
        return new NewUserForm(action, userType);
    }

}

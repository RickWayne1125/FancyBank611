package UI;

public class ViewFactory {
    public static AbstractJPanel getLoginPage(){
        return new login();
    }

    public static AbstractJPanel getMenuPage(){
        return new Menu();
    }

    public static AbstractJPanel getSavingsAccount(){
        return new Account("Savings");
    }

    public static AbstractJPanel getCheckingAccount(){
        return new Account("Checking");
    }

}

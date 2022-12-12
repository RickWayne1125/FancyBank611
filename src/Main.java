import Frontend.ViewFactory;
import Frontend.Frontend;

public class Main {
    public static void main(String[] args) {
        Frontend.getInstance(ViewFactory.getLoginPage());
    }
}
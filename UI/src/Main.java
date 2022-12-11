import UI.ViewFactory;
import UI.login;
import UI.root;

public class Main {
    public static void main(String[] args) {
        root.getRoot(ViewFactory.getLoginPage());
    }
}
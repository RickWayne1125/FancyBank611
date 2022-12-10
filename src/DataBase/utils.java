package DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class utils {
    static DataBase db = new DataBase();

    public static boolean logIn(int userID, String pwd) {
        String sql = "SELECT * FROM User WHERE user_id = " + userID;
        try {
            ResultSet rs = db.query(sql);
            if (rs == null) {
                return false;
            }
            return rs.getString("password").equals(pwd);
        } catch (SQLException e) {
            IO.displayMessage(e.getMessage(), MessageType.ERROR);
            return false;
        }
    }

    public static boolean signUp(String firstName, String middleName, String lastName, String email, String password,
                                 String contact, String address, boolean isCustomer) {
        if (firstName == null || lastName == null || password == null) {
            return false;
        }
        int isCustomerInt = isCustomer ? 1 : 0;
        String sql = "INSERT INTO User (first_name, middle_name, last_name, email, password, contact, address, is_customer) VALUES ('"
                + firstName + "', '" + middleName + "', '" + lastName + "', '" + email + "', '" + password + "', '" + contact + "', '"
                + address + "', " + isCustomerInt + ")";
        IO.displayMessage(sql, MessageType.INFO);
        try {
            db.execute(sql);
            return true;
        } catch (Exception e) {
            IO.displayMessage(e.getMessage(), MessageType.ERROR);
            return false;
        }
    }

    public static void main(String[] args) {
        // Test signUp
        IO.displayMessage(utils.signUp("Rick", "", "Wayne", "rickwayne991125@gmail.com",
                "123456", "1234567890", "1234", true)
                + "", MessageType.INFO);
        // Test logIn
        IO.displayMessage(utils.logIn(1, "123456") + "", MessageType.INFO);
    }
}

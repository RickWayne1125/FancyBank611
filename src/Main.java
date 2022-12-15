import DataBase.DataBase;
import Frontend.ViewFactory;
import Frontend.Frontend;
import Utils.Config;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Check if the database exists
        String dbPath = Config.DB_URL.split(":")[2];
        if (!new File(dbPath).exists()) {
            System.out.println("Database not found. Initializing database...");
            // Initialize database
            DataBase.clearDatabase();
            DataBase.createTables();
            DataBase.generateTestData();
        }
        Frontend.getInstance(ViewFactory.getLoginPage());
    }
}
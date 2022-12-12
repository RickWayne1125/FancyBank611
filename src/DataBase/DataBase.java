package DataBase;

import Utils.IO;
import Utils.MessageType;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {
    private static Connection connection;

    public DataBase() {
        // Establish connection to database
        connection = connect();
        // Create tables if they don't exist
//        createTables();
    }

    private void createTables() {
        // Create User table
        String sql = "CREATE TABLE IF NOT EXISTS User (\n"
                + "    username text PRIMARY KEY,\n"
                + "    first_name text NOT NULL,\n"
                + "    middle_name text,\n"
                + "    last_name text NOT NULL,\n"
                + "    email text,\n"
                + "    contact text,\n"
                + "    password text NOT NULL,\n"
                + "    address text,\n"
                + "    is_customer integer,\n"   // 0 for false, 1 for true
                + "    last_login text\n"   // This is used to store the last login time
                + ");";
        execute(sql);
        IO.displayMessage("User table created", MessageType.INFO);
        // Create Currency table
        sql = "CREATE TABLE IF NOT EXISTS Currency (\n"
                + "    name text PRIMARY KEY,\n"
                + "    symbol text NOT NULL,\n"
                + "    rate real NOT NULL\n"
                + ");";
        execute(sql);
        IO.displayMessage("Currency table created", MessageType.INFO);
        // Create Account table
        sql = "CREATE TABLE IF NOT EXISTS Account (\n"
                + "    account_no integer PRIMARY KEY autoincrement,\n"
                + "    account_type text NOT NULL,\n"
                + "    username text NOT NULL,\n"
                + "    routing_no integer NOT NULL,\n"
                + "    swift_code text,\n"
                + "    interest_rate real,\n"
                + "    FOREIGN KEY (username) REFERENCES User(username)\n"
                + ");";
        execute(sql);
        IO.displayMessage("Account table created", MessageType.INFO);
        // Create Transaction table
        sql = "CREATE TABLE IF NOT EXISTS TransactionTable (\n"
                + "    transaction_id integer PRIMARY KEY autoincrement,\n"
                + "    from_account integer NOT NULL,\n"
                + "    to_account integer NOT NULL,\n"
                + "    transaction_type text NOT NULL,\n"
                + "    amount real NOT NULL,\n"
                + "    currency text NOT NULL,\n"
                + "    date text NOT NULL,\n"
                + "    FOREIGN KEY (from_account) REFERENCES Account(account_no),\n"
                + "    FOREIGN KEY (to_account) REFERENCES Account(account_no),\n"
                + "    FOREIGN KEY (currency) REFERENCES Currency(name)\n"
                + ");";
        execute(sql);
        IO.displayMessage("Transaction table created", MessageType.INFO);
        // Create Money table
        sql = "CREATE TABLE IF NOT EXISTS Money (\n"
                + "    account_no integer NOT NULL,\n"
                + "    currency text NOT NULL,\n"
                + "    amount real NOT NULL,\n"
                + "    PRIMARY KEY (account_no, currency),\n"
                + "    FOREIGN KEY (account_no) REFERENCES Account(account_no),\n"
                + "    FOREIGN KEY (currency) REFERENCES Currency(name)\n"
                + ");";
        execute(sql);
        IO.displayMessage("Money table created", MessageType.INFO);
        // Create Loan table
        sql = "CREATE TABLE IF NOT EXISTS Loan (\n"
                + "    account_no integer PRIMARY KEY,\n"
                + "    start_date text NOT NULL,\n"
                + "    end_date text NOT NULL,\n"
                + "    FOREIGN KEY (account_no) REFERENCES Account(account_no)\n"
                + ");";
        execute(sql);
        IO.displayMessage("Loan table created", MessageType.INFO);
        // Create Stock table
        sql = "CREATE TABLE IF NOT EXISTS Stock (\n"
                + "    stock_id integer PRIMARY KEY autoincrement,\n"
                + "    stock_name text NOT NULL,\n"
                + "    current_price real NOT NULL\n"
                + ");";
        execute(sql);
        IO.displayMessage("Stock table created", MessageType.INFO);
        // Create BoughtStock table
        sql = "CREATE TABLE IF NOT EXISTS BoughtStock (\n"
                + "    account_no integer NOT NULL,\n"
                + "    stock_id integer NOT NULL,\n"
                + "    stock_unit integer NOT NULL,\n"
                + "    PRIMARY KEY (account_no, stock_id),\n"
                + "    FOREIGN KEY (account_no) REFERENCES Account(account_no),\n"
                + "    FOREIGN KEY (stock_id) REFERENCES Stock(stock_id)\n"
                + ");";
        execute(sql);
        IO.displayMessage("BoughtStock table created", MessageType.INFO);
        // Create SecurityAccount table
        sql = "CREATE TABLE IF NOT EXISTS SecurityAccount (\n"
                + "    account_no integer PRIMARY KEY,\n"
                + "    realized real,\n"
                + "    total_paid real,\n"
                + "    FOREIGN KEY (account_no) REFERENCES Account(account_no)\n"
                + ");";
        execute(sql);
        // Create Bank table
        sql = "CREATE TABLE IF NOT EXISTS Bank (\n"
                + "    bank_id integer PRIMARY KEY autoincrement,\n"
                + "    bank_name text NOT NULL,\n"
                + "    branch text NOT NULL,\n"
                + "    is_open integer NOT NULL\n"  // 0 for false, 1 for true
                + ");";
        execute(sql);
        IO.displayMessage("Bank table created", MessageType.INFO);
    }


    public static void execute(String sql) {
        try {
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void execute(String sql, String[] values) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i + 1, values[i]);
            }
            IO.displayMessage("Executing: " + preparedStatement.toString(), MessageType.INFO);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Map<String, String>> query(String sql, String[] args) {
        List<Map<String, String>> results = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setString(i + 1, args[i]);
            }
            IO.displayMessage("Executing: " + statement.toString(), MessageType.INFO);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row.put(resultSet.getMetaData().getColumnName(i), resultSet.getString(i));
                }
                results.add(row);
            }
            return results;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void clearTable(String tableName) {
        String sql = "DELETE FROM " + tableName;
        IO.displayMessage("Clearing table " + tableName, MessageType.WARNING);
        execute(sql);
        IO.displayMessage("Table " + tableName + " cleared", MessageType.INFO);
    }

    public void clearAllTables() {
        clearTable("User");
        clearTable("Currency");
        clearTable("Account");
        clearTable("TransactionTable");
        clearTable("Money");
        clearTable("Loan");
        clearTable("Stock");
        clearTable("BoughtStock");
        clearTable("Bank");
    }

    public void clearDatabase() {
        // Drop all tables
        String sql = "DROP TABLE IF EXISTS User";
        execute(sql);
        sql = "DROP TABLE IF EXISTS Currency";
        execute(sql);
        sql = "DROP TABLE IF EXISTS Account";
        execute(sql);
        sql = "DROP TABLE IF EXISTS TransactionTable";
        execute(sql);
        sql = "DROP TABLE IF EXISTS Money";
        execute(sql);
        sql = "DROP TABLE IF EXISTS Loan";
        execute(sql);
        sql = "DROP TABLE IF EXISTS Stock";
        execute(sql);
        sql = "DROP TABLE IF EXISTS BoughtStock";
        execute(sql);
        sql = "DROP TABLE IF EXISTS Bank";
        execute(sql);
        IO.displayMessage("All tables dropped", MessageType.INFO);
    }

//    private List<Map<String,String>> query(String sql) {
//        try {
//            return connection.createStatement().executeQuery(sql).getResultSet();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }

    public static Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:fancybank.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void generateTestData() {
        // Create User Data
        String sql = "INSERT INTO User (username, password, first_name, middle_name, last_name, email, contact, address, " +
                "is_customer, last_login) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "admin");
            statement.setString(2, "admin");
            statement.setString(3, "Ricky");
            statement.setString(4, "");
            statement.setString(5, "Wayne");
            statement.setString(6, "");
            statement.setString(7, "");
            statement.setString(8, "");
            statement.setInt(9, 0);
            statement.setString(10, "");
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        IO.displayMessage("Admin user created", MessageType.INFO);
        // Create Currency Data
        sql = "INSERT INTO Currency (name, symbol, rate) VALUES(?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "USD");
            statement.setString(2, "$");
            statement.setDouble(3, 1.0);
            statement.executeUpdate();
            statement.setString(1, "EUR");
            statement.setString(2, "€");
            statement.setDouble(3, 0.9);
            statement.executeUpdate();
            statement.setString(1, "GBP");
            statement.setString(2, "£");
            statement.setDouble(3, 0.8);
            statement.executeUpdate();
            statement.setString(1, "JPY");
            statement.setString(2, "¥");
            statement.setDouble(3, 110.0);
            statement.executeUpdate();
            statement.setString(1, "CNY");
            statement.setString(2, "¥");
            statement.setDouble(3, 7.0);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        IO.displayMessage("Currency data created", MessageType.INFO);
    }

    public static void main(String[] args) {
        DataBase db = new DataBase();
        db.clearDatabase();
        db.createTables();
        db.generateTestData();
    }
}

package DataBase;

import java.sql.*;

public class DataBase {
    private static Connection connection;

    public DataBase() {
        // Establish connection to database
        connection = connect();
        // Create tables if they don't exist
        this.createTables();
    }

    private void createTables() {
        // Create User table
        String sql = "CREATE TABLE IF NOT EXISTS User (\n"
                + "    user_id integer PRIMARY KEY autoincrement,\n"
                + "    first_name text NOT NULL,\n"
                + "    middle_name text,\n"
                + "    last_name text NOT NULL,\n"
                + "    email text,\n"
                + "    contact text,\n"
                + "    password text NOT NULL,\n"
                + "    address text,\n"
                + "    is_customer integer\n"   // 0 for false, 1 for true
                + ");";
        this.execute(sql);
        IO.displayMessage("User table created", MessageType.INFO);
        // Create Currency table
        sql = "CREATE TABLE IF NOT EXISTS Currency (\n"
                + "    name text PRIMARY KEY,\n"
                + "    symbol text NOT NULL,\n"
                + "    rate real NOT NULL\n"
                + ");";
        this.execute(sql);
        IO.displayMessage("Currency table created", MessageType.INFO);
        // Create Account table
        sql = "CREATE TABLE IF NOT EXISTS Account (\n"
                + "    account_no integer PRIMARY KEY autoincrement,\n"
                + "    user_id integer NOT NULL,\n"
                + "    routing_no integer NOT NULL,\n"
                + "    swift_code text,\n"
                + "    account_type text NOT NULL,\n"
                + "    interest_rate real,\n"
                + "    FOREIGN KEY (user_id) REFERENCES User(user_id)\n"
                + ");";
        this.execute(sql);
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
        this.execute(sql);
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
        this.execute(sql);
        IO.displayMessage("Money table created", MessageType.INFO);
        // Create Loan table
        sql = "CREATE TABLE IF NOT EXISTS Loan (\n"
                + "    account_no integer PRIMARY KEY,\n"
                + "    start_date text NOT NULL,\n"
                + "    end_date text NOT NULL,\n"
                + "    FOREIGN KEY (account_no) REFERENCES Account(account_no)\n"
                + ");";
        this.execute(sql);
        IO.displayMessage("Loan table created", MessageType.INFO);
        // Create Stock table
        sql = "CREATE TABLE IF NOT EXISTS Stock (\n"
                + "    stock_id integer PRIMARY KEY autoincrement,\n"
                + "    stock_name text NOT NULL,\n"
                + "    current_price real NOT NULL\n"
                + ");";
        this.execute(sql);
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
        this.execute(sql);
        IO.displayMessage("BoughtStock table created", MessageType.INFO);
        // Create Bank table
        sql = "CREATE TABLE IF NOT EXISTS Bank (\n"
                + "    bank_id integer PRIMARY KEY autoincrement,\n"
                + "    bank_name text NOT NULL,\n"
                + "    branch text NOT NULL,\n"
                + "    is_open integer NOT NULL\n"  // 0 for false, 1 for true
                + ");";
        this.execute(sql);
        IO.displayMessage("Bank table created", MessageType.INFO);
    }


    public void execute(String sql) {
        try {
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet query(String sql) {
        try {
            return connection.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void clearTable(String tableName) {
        String sql = "DELETE FROM " + tableName;
        IO.displayMessage("Clearing table " + tableName, MessageType.WARNING);
        this.execute(sql);
        IO.displayMessage("Table " + tableName + " cleared", MessageType.INFO);
    }

    public void clearAllTables() {
        this.clearTable("User");
        this.clearTable("Currency");
        this.clearTable("Account");
        this.clearTable("TransactionTable");
        this.clearTable("Money");
        this.clearTable("Loan");
        this.clearTable("Stock");
        this.clearTable("BoughtStock");
        this.clearTable("Bank");
    }

//    private List<Map<String,String>> query(String sql) {
//        try {
//            return this.connection.createStatement().executeQuery(sql).getResultSet();
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

    public static void main(String[] args) {
        DataBase db = new DataBase();
        db.clearAllTables();
        db.createTables();
    }
}

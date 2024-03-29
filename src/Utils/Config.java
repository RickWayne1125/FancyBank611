package Utils;

public class Config {
    public static final String DB_URL = "jdbc:sqlite:fancybank.db";
    public static final String DB_DRIVER = "org.sqlite.JDBC";
    public static final Integer BANK_ACCOUNT_NUMBER = 1;
    public static final double SERVICE_FEE = 5.00;
    public static final double DEFAULT_INTEREST_RATE = 0.01;
    public static final double DEFAULT_LOAN_INTEREST_RATE = 0.05;
    public static final double DEFAULT_CHECKING_INTEREST_RATE = 0.02;
    public static final double DEFAULT_SAVING_INTEREST_RATE = 0.03;
    public static final String BANK_ROUTING_NUMBER = "123456789";
    public static final String BANK_SWIFT_CODE = "123456789";
    public static final long ONE_DAY = 86400000L;
    public static final long INTEREST_FREQUENCY = ONE_DAY;
    public static boolean DEBUG = true;
    public static double DEFAULT_TRANSACT_SERVICE_FEE_RATE = 0.01;
    public static double DEFAULT_OPEN_SERVICE_FEE = 10.00;  // open account fee (USD)
    public static double DEFAULT_CLOSE_SERVICE_FEE = 10.00;  // loan service fee (USD)
}

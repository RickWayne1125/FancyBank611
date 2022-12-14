package Account;

import Account.Loan.Loan;
import Account.Loan.LoanDAO;
import Account.Security.SecurityAccount;
import Account.Security.SecurityDAO;
import DataBase.DataBase;
import Money.Money;
import Money.MoneyDAO;
import Utils.DAO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class AccountDAO implements DAO<Account> {

    private static DataBase dataBase = new DataBase();

    @Override
    public void create(Account account) {
        String sql = "INSERT INTO Account (account_no,account_type,username,routing_no,swift_code,interest_rate) VALUES (?,?,?,?,?,?)";
        dataBase.execute(sql, new String[]{String.valueOf(account.getAccountNumber()), String.valueOf(account.getType()),
                String.valueOf(account.getUsername()), String.valueOf(account.getRoutingNumber()), String.valueOf(account.getSwiftCode()),
                String.valueOf(account.getInterestRate())});
        switch (account.getType()) {
            case LOAN:
                Loan loan = (Loan) account;
                LoanDAO loanDAO = new LoanDAO();
                loanDAO.create(loan);
                break;
            case SECURITY:
                SecurityAccount securityAccount = (SecurityAccount) account;
                SecurityDAO securityDAO = new SecurityDAO();
                securityDAO.create(securityAccount);
                break;
        }
    }

    @Override
    public void delete(Account account) {
        String sql = "DELETE FROM Account WHERE account_no = ?";
        dataBase.execute(sql, new String[]{String.valueOf(account.getAccountNumber())});
        switch (account.getType()) {
            case LOAN:
                Loan loan = (Loan) account;
                LoanDAO loanDAO = new LoanDAO();
                loanDAO.delete(loan);
                break;
            case SECURITY:
                SecurityAccount securityAccount = (SecurityAccount) account;
                SecurityDAO securityDAO = new SecurityDAO();
                securityDAO.delete(securityAccount);
                break;
        }
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE Account SET account_type = ?, username = ?, routing_no = ?, swift_code = ?, interest_rate = ? WHERE account_no = ?";
        dataBase.execute(sql, new String[]{String.valueOf(account.getType()), String.valueOf(account.getUsername()),
                String.valueOf(account.getRoutingNumber()), String.valueOf(account.getSwiftCode()), String.valueOf(account.getInterestRate()),
                String.valueOf(account.getAccountNumber())});
        // Update Money table
        MoneyDAO moneyDAO = new MoneyDAO();
        List<Money> moneyList = account.getCurrentBalance();
        for (Money money : moneyList) {
            money.setAccountNumber(account.getAccountNumber());
            moneyDAO.update(money);
        }
        // Consider security account and loan account(need to modify other tables)
        switch (account.getType()) {
            case LOAN:
                Loan loan = (Loan) account;
                LoanDAO loanDAO = new LoanDAO();
                loanDAO.update(loan);
                break;
            case SECURITY:
                SecurityAccount securityAccount = (SecurityAccount) account;
                SecurityDAO securityDAO = new SecurityDAO();
                securityDAO.update(securityAccount);
                break;
        }
    }

    public Account readByAccountNumber(int accountNumber) {
        String sql = "SELECT * FROM Account WHERE account_no = ?";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{String.valueOf(accountNumber)});
        if (results.size() == 0) {
            return null;
        }
        if (results.size() > 1) {
            throw new RuntimeException("More than one row returned");
        }
        Map<String, String> row = results.get(0);
        AccountType type = AccountType.valueOf(row.get("account_type"));
        MoneyDAO moneyDAO = new MoneyDAO();
        switch (type) {
            case LOAN:
                Loan loan = new LoanDAO().readByAccountNumber(accountNumber);
                loan.setInterestRate(Double.parseDouble(row.get("interest_rate")));
                loan.setUsername(row.get("username"));
                loan.setCurrentBalance(moneyDAO.readByAccount(accountNumber));
            case SAVING:
                Saving saving = new Saving(Integer.parseInt(row.get("account_no")), row.get("routing_no"), row.get("swift_code"));
                saving.setInterestRate(Double.parseDouble(row.get("interest_rate")));
                saving.setUsername(row.get("username"));
                saving.setCurrentBalance(moneyDAO.readByAccount(accountNumber));
                return saving;
            case CHECKING:
                Checking checking = new Checking(Integer.parseInt(row.get("account_no")), row.get("routing_no"), row.get("swift_code"));
                checking.setInterestRate(Double.parseDouble(row.get("interest_rate")));
                checking.setUsername(row.get("username"));
                checking.setCurrentBalance(moneyDAO.readByAccount(accountNumber));
                return checking;
            case SECURITY:
                SecurityAccount securityAccount = new SecurityAccount(Integer.parseInt(row.get("account_no")), row.get("routing_no"), row.get("swift_code"));
                securityAccount.setCurrentBalance(moneyDAO.readByAccount(accountNumber));
                securityAccount.setUsername(row.get("username"));
                // TODO: check the BoughtStock table to set stock map
                return securityAccount;
        }
        return null;
    }
}

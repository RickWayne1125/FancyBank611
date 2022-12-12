package Account;

import Account.Loan.Loan;
import Account.Loan.LoanDAO;
import Account.Security.SecurityAccount;
import DataBase.DataBase;
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
    }

    @Override
    public void delete(Account account) {
        String sql = "DELETE FROM Account WHERE account_no = ?";
        // TODO: Consider security account and loan account(need to modify other tables)
        dataBase.execute(sql, new String[]{String.valueOf(account.getAccountNumber())});
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE Account SET account_type = ?, username = ?, routing_no = ?, swift_code = ?, interest_rate = ? WHERE account_no = ?";
        // TODO: Consider security account and loan account(need to modify other tables)
        dataBase.execute(sql, new String[]{String.valueOf(account.getType()), String.valueOf(account.getUsername()),
                String.valueOf(account.getRoutingNumber()), String.valueOf(account.getSwiftCode()), String.valueOf(account.getInterestRate()),
                String.valueOf(account.getAccountNumber())});
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
        switch (type) {
            case LOAN:
                return new LoanDAO().readByAccountNumber(accountNumber);
            case SAVING:
                Saving saving = new Saving(Integer.parseInt(row.get("account_no")), row.get("routing_no"), row.get("swift_code"));
                saving.setInterestRate(Double.parseDouble(row.get("interest_rate")));
                return saving;
            case CHECKING:
                Checking checking = new Checking(Integer.parseInt(row.get("account_no")), row.get("routing_no"), row.get("swift_code"));
                checking.setInterestRate(Double.parseDouble(row.get("interest_rate")));
                return checking;
            case SECURITY:
                SecurityAccount securityAccount = new SecurityAccount(Integer.parseInt(row.get("account_no")), row.get("routing_no"), row.get("swift_code"));
                // TODO: check the BoughtStock table to set stock map
                return securityAccount;
        }
        return null;
    }
}

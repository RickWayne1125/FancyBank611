package Account.Loan;

import Account.AccountType;
import DataBase.DataBase;
import Utils.DAO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class LoanDAO implements DAO<Loan> {
    private static DataBase dataBase = new DataBase();

    @Override
    public void create(Loan loan) {
        // TODO: Implement
    }

    @Override
    public void update(Loan loan) {
        // TODO: Implement
    }

    @Override
    public void delete(Loan loan) {
        // TODO: Implement
    }

    public Loan readByAccountNumber(int accountNumber) {
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
        String sql2 = "SELECT * FROM Loan WHERE account_no = ?";
        List<Map<String, String>> results2 = dataBase.query(sql2, new String[]{String.valueOf(accountNumber)});
        if (results2.size() == 0) {
            return null;
        }
        if (results2.size() > 1) {
            throw new RuntimeException("More than one row returned");
        }
        Date dueDate = new Date(Long.parseLong(results2.get(0).get("end_date")));
        return new Loan(Integer.parseInt(row.get("account_no")), row.get("routing_no"), row.get("swift_code"),
                Double.parseDouble(row.get("interest_rate")), dueDate);
    }

}

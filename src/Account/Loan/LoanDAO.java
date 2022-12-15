package Account.Loan;

import Account.AccountType;
import DataBase.DataBase;
import Utils.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LoanDAO implements DAO<Loan> {
    private static DataBase dataBase = new DataBase();

    @Override
    public void create(Loan loan) {
        // Insert into Loan table
        String sql = "INSERT INTO Loan (account_no, start_date, end_date, approved) VALUES (?,?,?,?)";
        dataBase.execute(sql, new String[]{String.valueOf(loan.getAccountNumber()), String.valueOf(loan.getStartDate()),
                String.valueOf(loan.getDueDate()), loan.isApproved() ? "1" : "0"});
    }

    @Override
    public void update(Loan loan) {
        // update Loan table
        String sql = "UPDATE Loan SET start_date = ?, end_date = ?, approved = ? WHERE account_no = ?";
        dataBase.execute(sql, new String[]{String.valueOf(loan.getStartDate()), String.valueOf(loan.getDueDate()),
                loan.isApproved() ? "1" : "0", String.valueOf(loan.getAccountNumber())});
    }

    @Override
    public void delete(Loan loan) {
        // delete from Loan table
        String sql = "DELETE FROM Loan WHERE account_no = ?";
        dataBase.execute(sql, new String[]{String.valueOf(loan.getAccountNumber())});
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
        Date startDate = new Date(results2.get(0).get("start_date"));
        Date dueDate = new Date(results2.get(0).get("end_date"));
        Loan loan = new Loan(Integer.parseInt(row.get("account_no")), row.get("routing_no"), row.get("swift_code"),
                Double.parseDouble(row.get("interest_rate")), startDate, dueDate);
        loan.setApproved(results2.get(0).get("approved").equals("1"));
        loan.setUsername(row.get("username"));
        return loan;
    }

    public List<Loan> readUnapprovedLoans() {
        String sql = "SELECT * FROM Loan WHERE approved = 0";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{});
        List<Loan> loans = new ArrayList<>();
        if (results.size() == 0) {
            return loans;
        }
        for (Map<String, String> row : results) {
            Loan loan = readByAccountNumber(Integer.parseInt(row.get("account_no")));
            loans.add(loan);
        }
        return loans;
    }

    public List<Loan> readApprovedLoans() {
        String sql = "SELECT * FROM Loan WHERE approved = 1";
        List<Map<String, String>> results = dataBase.query(sql, new String[]{});
        List<Loan> loans = new ArrayList<>();
        if (results.size() == 0) {
            return loans;
        }
        for (Map<String, String> row : results) {
            Loan loan = readByAccountNumber(Integer.parseInt(row.get("account_no")));
            loans.add(loan);
        }
        return loans;
    }
}

package Account.Security;

import Account.AccountDAO;
import DataBase.DataBase;
import Utils.DAO;
import Utils.IO;
import Utils.MessageType;

public class SecurityDAO implements DAO<SecurityAccount> {
    private static DataBase dataBase = new DataBase();
    @Override
    public void create(SecurityAccount securityAccount) {
        String sql = "INSERT INTO SecurityAccount (account_no, realized, total_paid) VALUES (?,?,?)";
        dataBase.execute(sql,new String[]{String.valueOf(securityAccount.getAccountNumber()), String.valueOf(securityAccount.getRealized().getAmount()),String.valueOf(securityAccount.getTotalPaid().getAmount())});
    }

    @Override
    public void update(SecurityAccount securityAccount) {
        String sql = "UPDATE SecurityAccount SET realized=?, total_paid=? WHERE account_no=?";
        IO.displayMessage(securityAccount.getTotalPaid().toString(), MessageType.ERROR);
        dataBase.execute(sql,new String[]{String.valueOf(securityAccount.getRealized().getAmount()),String.valueOf(securityAccount.getTotalPaid().getAmount()),String.valueOf(securityAccount.getAccountNumber())});
    }

    @Override
    public void delete(SecurityAccount securityAccount) {
        String sql = "DELETE FROM SecurityAccount WHERE account_no=?";
        dataBase.execute(sql,new String[]{String.valueOf(securityAccount.getAccountNumber())});
    }

    public SecurityAccount readByAccountNumber(int accountNumber) {
        return (SecurityAccount) new AccountDAO().readByAccountNumber(accountNumber);
    }

}

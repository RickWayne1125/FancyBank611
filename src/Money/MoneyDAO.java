package Money;

import DataBase.DataBase;
import Utils.DAO;

public class MoneyDAO implements DAO<Money> {
    static DataBase dataBase = new DataBase();

    @Override
    public void create(Money money) {
        String sql = "INSERT INTO Money (amount,currency) VALUES ("
                + money.getAmount() + ",'" + money.getCurrency().getCurrencyName() + "')";
        dataBase.execute(sql);
    }


    public Money read(String id) {
        String sql = "SELECT * FROM Money WHERE id = " + id;
        return null;
    }

    @Override
    public void delete(Money money) {

    }

    @Override
    public void update(Money money) {

    }

}

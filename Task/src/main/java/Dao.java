import java.sql.SQLException;

public interface Dao<T> {

    void balance(T item) throws SQLException, UnknownAccountException;
    void deposit(T item) throws SQLException, UnknownAccountException;
    void withdraw(T item) throws SQLException, UnknownAccountException, NotEnoughMoneyException;
    void transfer(T item1, T item2) throws SQLException, UnknownAccountException, NotEnoughMoneyException;

    void createNew();
}

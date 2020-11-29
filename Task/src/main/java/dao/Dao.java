package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Dao<T> {

    void balance(T item) throws SQLException, UnknownAccountException, IOException;
    void deposit(T item) throws SQLException, UnknownAccountException, IOException;
    void withdraw(T item) throws SQLException, UnknownAccountException, NotEnoughMoneyException, IOException;
    void transfer(T item1, T item2) throws SQLException, UnknownAccountException, NotEnoughMoneyException, IOException;

    void createNew(ArrayList<T> accounts) throws IOException;
}

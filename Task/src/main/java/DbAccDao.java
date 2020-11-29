import java.sql.*;
import java.util.ArrayList;

class DbAccDao implements Dao<Account> {

    private final String SELECT_QUERY = "SELECT * FROM account WHERE id = ?";
    private final String UPDATE_DEPOSIT_QUERY ="update account set amount = amount +  ? WHERE id = ?";
    private final String UPDATE_WITHDRAW_QUERY ="update account set amount = amount -  ? WHERE id = ?";

    public void withdraw(Account acc) throws SQLException, UnknownAccountException, NotEnoughMoneyException {
        sqlSelect(acc);
        updateWithdraw(acc);
        sqlSelect(acc);
        System.out.println();

    }

    public void balance(Account acc) throws SQLException, UnknownAccountException {
        sqlSelect(acc);
    }

    public void deposit(Account acc) throws SQLException, UnknownAccountException {
        sqlSelect(acc);
        updateDeposit(acc);
        sqlSelect(acc);
        System.out.println();
    }

    public void transfer(Account acc1, Account acc2) throws SQLException, UnknownAccountException, NotEnoughMoneyException {

        balance(acc1);
        balance(acc2);
        updateWithdraw(acc1);
        updateDeposit(acc2);
        balance(acc1);
        balance(acc2);
    }

    public void createNew(ArrayList<Account> accounts) {

        try {
            DriverManager
                    .getConnection("jdbc:h2:mem:ACCOUNT;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM './Task/schema.sql'\\;RUNSCRIPT FROM './Task/data.sql'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void sqlSelect(Account acc) throws SQLException, UnknownAccountException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {

                connection = DriverManager
                        .getConnection("jdbc:h2:mem:ACCOUNT");
                preparedStatement = connection.prepareStatement(SELECT_QUERY);
                preparedStatement.setInt(1, (acc.getId()));
                ResultSet resultSet = preparedStatement.executeQuery();
                int id;
                String name;
                int amount;
                if (!resultSet.next()) {
                    throw new UnknownAccountException("Счет: " + acc.getId() + " неверный");
                }
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                    name = resultSet.getString(2);
                    amount = resultSet.getInt(3);
                    System.out.println(id + " " + name + " " + amount);
                }

            } catch (SQLException | NumberFormatException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    void updateWithdraw(Account acc) throws SQLException, NotEnoughMoneyException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {
                int amount = 0;
                connection = DriverManager
                        .getConnection("jdbc:h2:mem:ACCOUNT");
                preparedStatement = connection.prepareStatement(SELECT_QUERY);
                preparedStatement.setInt(1, acc.getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    amount = resultSet.getInt(3);
                }

                if ((amount - acc.getAmountOperation()) < 0) {
                    throw new NotEnoughMoneyException("Недостаточно средств");
                }

                System.out.println("Сняли: " + acc.getAmountOperation());

                connection = DriverManager
                        .getConnection("jdbc:h2:mem:ACCOUNT");
                preparedStatement = connection.prepareStatement(UPDATE_WITHDRAW_QUERY);
                preparedStatement.setInt(1, acc.getAmountOperation());
                preparedStatement.setInt(2, acc.getId());
                preparedStatement.executeUpdate();

                preparedStatement = null;

                preparedStatement = connection.prepareStatement(SELECT_QUERY);
                preparedStatement.setInt(1, acc.getId());
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new UnknownAccountException("Счет: " + acc.getId() + " неверный");
                }

            } catch (NotEnoughMoneyException | UnknownAccountException e) {
                throw new NotEnoughMoneyException("Недостаточно средств на счете! #" + acc.getId());
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    private void updateDeposit(Account acc) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {

                System.out.println("Положили: " + acc.getAmountOperation());

                connection = DriverManager
                        .getConnection("jdbc:h2:mem:ACCOUNT");
                preparedStatement = connection.prepareStatement(UPDATE_DEPOSIT_QUERY);
                preparedStatement.setInt(1, acc.getAmountOperation());
                preparedStatement.setInt(2, acc.getId());
                preparedStatement.executeUpdate();

                preparedStatement = null;

                preparedStatement = connection.prepareStatement(SELECT_QUERY);
                preparedStatement.setInt(1, acc.getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new UnknownAccountException("Счет: " + acc.getAmountOperation() + " неверный");
                }

            } catch (SQLException | UnknownAccountException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}

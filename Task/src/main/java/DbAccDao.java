import java.sql.*;ppublic

class DbAccDao implements Dao<Account> {

    void withdraw(String accountId, String amount2) throws SQLException, UnknownAccountException, NotEnoughMoneyException {
        sqlSelect(accountId);
        updateWithdraw(accountId, amount2);
        sqlSelect(accountId);

    }

    void balance(String accountId) throws SQLException, UnknownAccountException {
        sqlSelect(accountId);
    }

    void deposit(String accountId, String amount2) throws SQLException, UnknownAccountException {
        sqlSelect(accountId);
        updateDeposit(accountId, amount2);
        sqlSelect(accountId);
    }

    public void transfer(String from, String to, String amount) throws SQLException, UnknownAccountException, NotEnoughMoneyException {

        balance(from);
        balance(to);
        updateWithdraw(from, amount);
        updateDeposit(to, amount);
        balance(from);
        balance(to);
    }

    void createNew() {

        try {
            DriverManager
                    .getConnection("jdbc:h2:mem:ACCOUNT;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM './Task/schema.sql'\\;RUNSCRIPT FROM './Task/data.sql'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void sqlSelect(String accountId) throws SQLException, UnknownAccountException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {

                connection = DriverManager
                        .getConnection("jdbc:h2:mem:ACCOUNT");
                preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE id = ?");
                preparedStatement.setInt(1, Integer.parseInt(accountId));
                ResultSet resultSet = preparedStatement.executeQuery();
                int id;
                String name;
                int amount;
                if (!resultSet.next()) {
                    throw new UnknownAccountException("Счет: " + accountId + " неверный");
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
            preparedStatement.close();
            connection.close();
        }
    }

    void updateWithdraw(String accountId, String amount2) throws SQLException, NotEnoughMoneyException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {
                int amount = 0;
                connection = DriverManager
                        .getConnection("jdbc:h2:mem:ACCOUNT");
                preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE id = ?");
                preparedStatement.setInt(1, Integer.parseInt(accountId));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    amount = resultSet.getInt(3);
                }

                if ((amount - Integer.parseInt(amount2)) < 0) {
                    throw new NotEnoughMoneyException("Недостаточно средств");
                }

                System.out.println("Сняли: " + amount2);

                connection = DriverManager
                        .getConnection("jdbc:h2:mem:ACCOUNT");
                String sql = "update account set amount = amount - ? WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, Integer.parseInt(amount2));
                preparedStatement.setInt(2, Integer.parseInt(accountId));
                preparedStatement.executeUpdate();

                preparedStatement = null;

                preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE id = ?");
                preparedStatement.setInt(1, Integer.parseInt(accountId));
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new UnknownAccountException("Счет: " + accountId + " неверный");
                }

            } catch (NotEnoughMoneyException | UnknownAccountException e) {
                throw new NotEnoughMoneyException("Недостаточно средств на счете! #" + accountId);
            }
        } finally {
            preparedStatement.close();
            connection.close();
        }
    }

    void updateDeposit(String accountId, String amount2) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            try {

                System.out.println("Положили: " + amount2);

                connection = DriverManager
                        .getConnection("jdbc:h2:mem:ACCOUNT");
                String sql = "update account set amount = amount +  ? WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, Integer.parseInt(amount2));
                preparedStatement.setInt(2, Integer.parseInt(accountId));
                preparedStatement.executeUpdate();

                preparedStatement = null;

                preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE id = ?");
                preparedStatement.setInt(1, Integer.parseInt(accountId));
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new UnknownAccountException("Счет: " + accountId + " неверный");
                }

            } catch (SQLException | UnknownAccountException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            preparedStatement.close();
            connection.close();
        }
    }

}

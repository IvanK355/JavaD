package application;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbcpDataSource {

    private static final String urlStart = "jdbc:h2:mem:ACCOUNT;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM './Task/src/main/resources/data/schema.sql'\\;RUNSCRIPT FROM './Task/src/main/resources/data/data.sql'";
    private static final String url = "jdbc:h2:mem:ACCOUNT";
    // private static final String user = "user";
    // private static final String password = "password";
    private static final List<Connection> connectionPool;
    private static final List<Connection> usedConnections = new ArrayList<>();
    private static final JdbcDataSource dataSource = new JdbcDataSource();

    private static Connection createConnection(
            String url)
            throws SQLException {
        return DriverManager.getConnection(url);
    }

    static {
        dataSource.setUrl("jdbc:h2:mem:ACCOUNT;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM './Task/src/main/resources/data/schema.sql'\\;RUNSCRIPT FROM './Task/src/main/resources/data/data.sql'");
        //dataSource.setUser("user");
        //dataSource.setPassword("password");
        int INITIAL_POOL_SIZE = 10;
        connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            try {
                //connectionPool.add(createConnection(urlStart, user, password));
                connectionPool.add(createConnection(urlStart));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        if (connectionPool.isEmpty()) {
            int MAX_POOL_SIZE = 100;
            if (usedConnections.size() < MAX_POOL_SIZE) {
                try {
                    connectionPool.add(createConnection(url));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                throw new RuntimeException(
                        "Maximum pool size reached, no available connections!");
            }
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    private DbcpDataSource() {

    }
}

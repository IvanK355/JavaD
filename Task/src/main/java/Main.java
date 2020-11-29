import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, UnknownAccountException, UnknownNameOperationException, NotEnoughMoneyException, SQLException {
        BankFacade facade = new BankFacade();
        facade.info();
    }
}


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class BankFacade {

    private final ArrayList<Account> accounts;
    private final DbAccDao dbAccDao;


    public BankFacade() {
        this.accounts = new ArrayList<>();
        this.dbAccDao = new DbAccDao();
    }

    public void info() throws UnknownNameOperationException, SQLException, UnknownAccountException, NotEnoughMoneyException, IOException {

        AccDaoFactory accDaoFactory = new AccDaoFactory();
        Dao<Account> dao = accDaoFactory.getDao("json");

        dao.createNew();

        for (int i = 0; i < 11; i++) {
            accounts.add(new Account());
        }


        // *  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // * String s = reader.readLine();
        // * String[] array = s.split("\\W");
        accounts.get(0).setOperation("balance");
        accounts.get(0).setId(1);
        accounts.get(1).setOperation("deposit");
        accounts.get(1).setId(2);
        accounts.get(1).setAmount(100);
        accounts.get(2).setOperation("withdraw");
        accounts.get(2).setId(3);
        accounts.get(2).setAmount(100);
        accounts.get(3).setOperation("transfer");
        accounts.get(3).setId(4);
        accounts.get(3).setAmount(100);
        accounts.get(4).setOperation("transfer");
        accounts.get(4).setId(5);
        accounts.get(4).setAmount(100);

        for (int i = 0; i < 4; i++) {

            switch (accounts.get(i).getOperation()) {
                case "balance" -> dao.balance(accounts.get(i));
                case "withdraw" -> dao.withdraw(accounts.get(i));
                case "deposit" -> dao.deposit(accounts.get(i));
                case "transfer" -> dao.transfer(accounts.get(i), accounts.get(i+1));

                default -> throw new UnknownNameOperationException("Неизвестная операция " + accounts.get(i).getOperation());
            }
        }
    }
}
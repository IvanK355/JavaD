import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

public class BankFacade {

    private ArrayList<Account> accounts;
    private AccountService accountService;


    public BankFacade() {
        this.accounts = new ArrayList<>();
        this.accountService = new AccountService();
    }

    public void info() throws IOException, UnknownNameOperationException, SQLException, UnknownAccountException, NotEnoughMoneyException {


        accountService.createNew();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String s = reader.readLine();

        String[] array = s.split("\\W");

        switch (array[0]) {
            case "balance" -> accountService.balance(array[1]);
            case "withdraw" -> accountService.withdraw(array[1], array[2]);
            case "deposit" -> accountService.deposit(array[1], array[2]);
            case "transfer" -> accountService.transfer(array[1], array[2], array[3]);

            default -> throw new UnknownNameOperationException("Неизвестная операция " + array[0]);
        }
    }
}
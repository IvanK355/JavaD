package application;

import dao.Dao;
import dao.NotEnoughMoneyException;
import dao.UnknownAccountException;
import dao.UnknownNameOperationException;
import domain.Account;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class BankFacade {

    private final ArrayList<Account> accounts;


    public BankFacade() {
        this.accounts = new ArrayList<>();
    }

    public void info() throws UnknownNameOperationException, SQLException, UnknownAccountException, NotEnoughMoneyException, IOException {

        AccDaoFactory accDaoFactory = new AccDaoFactory();
        Dao<Account> dao = accDaoFactory.getDao("db");


        for (int i = 0; i < 11; i++) {
            accounts.add(new Account(i, "Holder" + i, 0, 500, 500, "deposit"));
        }
        dao.createNew(accounts);


        // *  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // * String s = reader.readLine();
        // * String[] array = s.split("\\W");
        accounts.get(1).setOperation("balance");
        accounts.get(2).setAmountOperation(100);
        accounts.get(2).setOperation("deposit");
        accounts.get(3).setAmountOperation(100);
        accounts.get(3).setOperation("withdraw");
        accounts.get(4).setAmountOperation(100);
        accounts.get(5).setAmountOperation(100);
        accounts.get(4).setOperation("transfer");
        accounts.get(5).setOperation("transfer");

        for (int i = 1; i < 5; i++) {

            switch (accounts.get(i).getOperation()) {
                case "balance" -> dao.balance(accounts.get(i));
                case "withdraw" -> dao.withdraw(accounts.get(i));
                case "deposit" -> dao.deposit(accounts.get(i));
                case "transfer" -> dao.transfer(accounts.get(i), accounts.get(i + 1));

                default -> throw new UnknownNameOperationException("Неизвестная операция " + accounts.get(i).getOperation());
            }
        }
    }
}
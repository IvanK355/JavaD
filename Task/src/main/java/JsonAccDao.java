import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class JsonAccDao implements Dao<Account> {

    public static final String filePath = "./Task/accounts.json";
    public static final Type itemsMapType = new TypeToken<HashMap<Integer, Account>>() {}.getType();

    @Override

    public void balance(Account item) throws IOException {

        HashMap<Integer, Account> balanceHashMap = readJson();
        System.out.println(balanceHashMap.get(item.getId()));

    }

    @Override

    public void deposit(Account item) throws IOException {
        HashMap<Integer, Account> balanceHashMap = readJson();
        System.out.println(balanceHashMap.get(item.getId()));
        int newValue=+item.getAmount();
        Account newAcc = new Account(item.getId(), item.getHolder(),newValue, "deposit");
        balanceHashMap.put(item.getId(), newAcc);
        writeJson(balanceHashMap);
    }

    @Override
    public void withdraw(Account item) throws IOException {
        HashMap<Integer, Account> balanceHashMap = readJson();
        System.out.println(balanceHashMap.get(item.getId()));
        int newValue=-item.getAmount();
        Account newAcc = new Account(item.getId(), item.getHolder(),newValue, "deposit");
        balanceHashMap.put(item.getId(), newAcc);
        System.out.println(balanceHashMap.get(item.getId()));
        writeJson(balanceHashMap);

    }

    @Override
    public void transfer(Account item1, Account item2) throws IOException {

        HashMap<Integer, Account> balanceHashMap = readJson();
        System.out.println(balanceHashMap.get(item1.getId()));
        System.out.println(balanceHashMap.get(item2.getId()));
        int newValue1=-item1.getAmount();
        int newValue2=+item2.getAmount();
        Account newAcc1 = new Account(item1.getId(), item1.getHolder(),newValue1, "withdraw");
        Account newAcc2 = new Account(item2.getId(), item2.getHolder(),newValue2, "deposit");
        balanceHashMap.put(item1.getId(), newAcc1);
        balanceHashMap.put(item2.getId(), newAcc1);
        System.out.println(balanceHashMap.get(item1.getId()));
        System.out.println(balanceHashMap.get(item2.getId()));
        writeJson(balanceHashMap);

    }

    @Override
    public void createNew() throws IOException {

        Gson gson = new Gson();

        FileWriter fw = new FileWriter(filePath);

        HashMap<Integer, Account> mapItems = new HashMap<Integer, Account>();
        for (int i = 1; i < 11; i++) {
            mapItems.put(i, new Account(i, "Holder"+i, 500, "deposit"));
        }
        gson.toJson(mapItems, fw);
        //String jsonStr = gson.toJson(mapItems);
        fw.close();
    }

    private void writeJson(HashMap<Integer, Account> mapItems) throws IOException {

        Gson gson = new Gson();

        FileWriter fw = new FileWriter(filePath);

        gson.toJson(mapItems, fw);
        //String jsonStr = gson.toJson(mapItems);
        fw.close();
    }

    private HashMap<Integer, Account> readJson() throws IOException {

        Gson gson = new Gson();

        FileReader fr = new FileReader(filePath);

        BufferedReader bufferedReader = new BufferedReader(fr);

        HashMap<Integer, Account> mapItemsDes = gson.fromJson(bufferedReader, itemsMapType);

        return mapItemsDes;

    }

}

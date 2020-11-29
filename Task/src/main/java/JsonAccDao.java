import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonAccDao implements Dao<Account> {

    public static final String filePath = "./Task/accounts.json";
    public static final Type itemsMapType = new TypeToken<HashMap<Integer, Account>>() {
    }.getType();

    @Override

    public void balance(Account item) throws IOException {

        HashMap<Integer, Account> balanceHashMap = readJson();
        System.out.println(balanceHashMap.get(item.getId()));
        System.out.println();

    }

    @Override

    public void deposit(Account item) throws IOException {
        HashMap<Integer, Account> balanceHashMap = readJson();
        int newValue = balanceHashMap.get(item.getId()).getEndSum() + item.getAmountOperation();
        Account newAcc = new Account(item.getId(), item.getHolder(), item.getEndSum(), item.getAmountOperation(), newValue, "deposit");
        balanceHashMap.put(item.getId(), newAcc);
        System.out.println(balanceHashMap.get(item.getId()));
        writeJson(balanceHashMap);
        System.out.println();
    }

    @Override
    public void withdraw(Account item) throws IOException {
        HashMap<Integer, Account> balanceHashMap = readJson();
        int newValue = balanceHashMap.get(item.getId()).getEndSum() - item.getAmountOperation();
        Account newAcc = new Account(item.getId(), item.getHolder(), item.getEndSum(), item.getAmountOperation(), newValue, "withdraw");
        balanceHashMap.put(item.getId(), newAcc);
        System.out.println(balanceHashMap.get(item.getId()));
        writeJson(balanceHashMap);
        System.out.println();

    }

    @Override
    public void transfer(Account item1, Account item2) throws IOException {

        HashMap<Integer, Account> balanceHashMap = readJson();
        int newValue1 = balanceHashMap.get(item1.getId()).getEndSum() - item1.getAmountOperation();
        int newValue2 = balanceHashMap.get(item2.getId()).getEndSum() + item2.getAmountOperation();
        Account newAcc1 = new Account(item1.getId(), item1.getHolder(), item1.getEndSum(), item1.getAmountOperation(), newValue1, "withdraw");
        Account newAcc2 = new Account(item2.getId(), item2.getHolder(), item2.getEndSum(), item2.getAmountOperation(), newValue2, "deposit");
        balanceHashMap.put(item1.getId(), newAcc1);
        balanceHashMap.put(item2.getId(), newAcc2);
        System.out.println(balanceHashMap.get(item1.getId()));
        System.out.println(balanceHashMap.get(item2.getId()));
        writeJson(balanceHashMap);
        System.out.println();

    }

    @Override
    public void createNew(ArrayList<Account> accounts) throws IOException {

        Gson gson = new Gson();

        FileWriter fw = new FileWriter(filePath);

        HashMap<Integer, Account> mapItems = new HashMap<>();
        for (int i = 1; i < 11; i++) {
            mapItems.put(i, accounts.get(i));
        }
        gson.toJson(mapItems, fw);
        //String jsonStr = gson.toJson(mapItems);
        fw.close();
    }

    private void writeJson(HashMap<Integer, Account> mapItems) throws IOException {

        Gson gson = new Gson();

        FileWriter fw = new FileWriter(filePath);

        gson.toJson(mapItems, fw);
        fw.close();
    }

    private HashMap<Integer, Account> readJson() throws IOException {

        Gson gson = new Gson();

        FileReader fr = new FileReader(filePath);

        BufferedReader bufferedReader = new BufferedReader(fr);


        HashMap<Integer, Account> mapItemsDes = gson.fromJson(bufferedReader, itemsMapType);
        fr.close();

        return mapItemsDes;
    }
}

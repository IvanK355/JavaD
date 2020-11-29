public interface Dao<T> {

    void balance(T item);
    void deposit(T item);
    void withdraw(T item);
    void transfer(T item);

}

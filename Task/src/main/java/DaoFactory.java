public interface DaoFactory<T> {

    Dao<T> getDao(String type);

}

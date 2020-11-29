public class AccDaoFactory implements DaoFactory<Account> {
    @Override
    public Dao<Account> getDao(String type) {
        Dao<Account> dao = null;
        switch (type){
            case "db":
                dao = new DbAccDao();
                break;
            case "json":
                dao = new JsonAccDao();
                break;
            default:
                throw new UnsupportedOperationException("Dont find dao");

        }
        return dao;

    }
}

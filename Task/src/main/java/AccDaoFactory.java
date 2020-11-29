public class AccDaoFactory implements DaoFactory<Account> {
    @Override
    public Dao<Account> getDao(String type) {
        return switch (type) {
            case "db" -> new DbAccDao();
            case "json" -> new JsonAccDao();
            default -> throw new UnsupportedOperationException("Dont find dao");
        };
    }
}

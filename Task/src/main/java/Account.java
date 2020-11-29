public class Account {

    private int id;
    private String holder;
    private int beginSum;
    private int amountOperation;
    private int endSum;
    private String operation;


    public Account(int id, String holder, int beginSum, int amountOperation, int endSum, String operation) {
        this.id = id;
        this.holder = holder;
        this.beginSum = beginSum;
        this.amountOperation = amountOperation;
        this.endSum = endSum;
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getBeginSum() {
        return beginSum;
    }

    public void setBeginSum(int beginSum) {
        this.beginSum = beginSum;
    }

    public int getAmountOperation() {
        return amountOperation;
    }

    public void setAmountOperation(int amountOperation) {
        this.amountOperation = amountOperation;
    }

    public int getEndSum() {
        return endSum;
    }

    public void setEndSum(int endSum) {
        this.endSum = endSum;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getHolder() {

        return holder;
    }

    public void setHolder(String holder) {

        this.holder = holder;
    }

    public String toString() {
        return id + " : " + holder + " : " + "Нач. сумма: " + beginSum
                + " Сумма операции: " + amountOperation + " Конеч сумма: " + endSum
                + " : " + operation;
    }
}

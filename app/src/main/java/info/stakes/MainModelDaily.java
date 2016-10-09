package info.stakes;

/**
 * Created by SHRADDHA on 11-09-2016.
 */
public class MainModelDaily {

    String field;
    int Amount;

    public MainModelDaily(String field, int amount) {
        this.field = field;
        Amount = amount;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}

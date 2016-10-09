package info.stakes;

/**
 * Created by SHRADDHA on 25-09-2016.
 */
public class DailyModel {
    String Field;
    int Amount;
    int isSelected;



    public DailyModel() {
    }

    public DailyModel(String field, int amount) {
        Field = field;
        Amount = amount;
    }

    public DailyModel(String field, int amount, int isSelected) {
        Field = field;
        Amount = amount;
        this.isSelected = isSelected;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int isSelected() {
        return isSelected;
    }

    public void setSelected(int selected) {
        isSelected = selected;
    }
}

package info.stakes;

/**
 * Created by SHRADDHA on 02-10-2016.
 */
public class ScheduleModel {

    String Field;
    float percent;
    int isSelected;

    public ScheduleModel() {
    }

    public ScheduleModel(String field, float percent) {
        Field = field;
        this.percent = percent;
    }

    public ScheduleModel(String field, float percent, int isSelected) {
        Field = field;
        this.percent = percent;
        this.isSelected = isSelected;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}

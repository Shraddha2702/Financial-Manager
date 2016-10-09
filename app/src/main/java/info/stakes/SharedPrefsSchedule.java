package info.stakes;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SHRADDHA on 08-10-2016.
 */
public class SharedPrefsSchedule {

    Context context;
    int Total;

    SharedPreferences pref;
    SharedPreferences.Editor edit;

    public SharedPrefsSchedule(Context context) {
        this.context = context;

        pref = context.getSharedPreferences("total", 0);
        edit = pref.edit();
    }

    public int getAmount() {
        return pref.getInt("Total", Total);
    }

    public void setAmount(int total) {
        Total = total;
        edit.putInt("Total",total);
        edit.commit();
    }
}

package info.stakes;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SHRADDHA on 06-10-2016.
 */
public class CheckAmount {

    Context context;
    int Amount;

    SharedPreferences pref;
    SharedPreferences.Editor edit;

    public CheckAmount(Context context) {
        this.context = context;

        pref = context.getSharedPreferences("Amount", 0);
        edit = pref.edit();
    }

    public int getAmount() {
        return pref.getInt("AmountMonthly", Amount);
    }

    public void setAmount(int amount) {
        Amount = amount;
        edit.putInt("AmountMonthly",amount);
        edit.commit();
    }
}

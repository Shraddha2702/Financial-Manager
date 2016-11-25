package info.stakes;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by SHRADDHA on 18-08-2016.
 */
public class GoalFragment extends Fragment {

    TextView tv;
    TextView printgoal;
    TextView printamount;
    TextView printsaving;

    DatabaseHelper db;
    DatabaseHelperSavings ds;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_goal, container, false);

        tv = (TextView) v.findViewById(R.id.tvgoalhere);
        printgoal = (TextView) v.findViewById(R.id.goalhereprint);
        printamount = (TextView) v.findViewById(R.id.goalamthereprint);
        printsaving = (TextView)v.findViewById(R.id.printsavings);


        db = new DatabaseHelper(getActivity());
        ds = new DatabaseHelperSavings(getActivity());

        Cursor res = db.getAllGoals();

        if (res.getCount() == 0) {
            showMessage("Error", "No Data");
        } else {

            res.moveToLast();

            Cursor c = ds.getAllSaving();
            c.moveToFirst();

            if(c.getCount() > 0 ){
            float amt = c.getFloat(0);

            printsaving.setText(""+amt);}

            printgoal.setText(res.getString(0));
            printamount.setText(" " + res.getInt(1));

            Log.d("resString", res.getString(0));
            Log.d("restInt", " " + res.getInt(1));
            res.close();
            db.close();
            ds.close();
        }
        return v;

        }



    public void showMessage(String Title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();
    }
}



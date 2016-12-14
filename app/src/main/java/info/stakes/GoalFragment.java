package info.stakes;

import android.app.Dialog;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.EmptyStackException;
import java.util.List;

/**
 * Created by SHRADDHA on 18-08-2016.
 */
public class GoalFragment extends Fragment {

    TextView printgoal;
    TextView printamount;
    TextView printsaving;
    ImageView iv;

    DatabaseHelper db;
    DatabaseHelperSavings ds;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_goal, container, false);

        printgoal = (TextView) v.findViewById(R.id.goalhereprint);
        printamount = (TextView) v.findViewById(R.id.goalamthereprint);
        printsaving = (TextView) v.findViewById(R.id.printsavings);
        iv = (ImageView) v.findViewById(R.id.ivgo);

        db = new DatabaseHelper(getActivity());
        ds = new DatabaseHelperSavings(getActivity());

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(getActivity());
                d.setContentView(R.layout.help_goal);
                d.setTitle("Goal Info");

                d.show();
                Button b = (Button) d.findViewById(R.id.bdg);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
            }
        });


        Cursor res = db.getAllGoals();

        if (res.getCount() == 0) {
            showMessage("Error", "No Data");
        } else {

            float amt=0;
            res.moveToLast();

            Cursor c = ds.getAllSaving();
            c.moveToFirst();

            if (c.getCount() > 0) {
                amt = c.getFloat(0);

                printsaving.setText("" + amt);
            }

            printgoal.setText(res.getString(0));

            float f = (float) res.getInt(1);
            printamount.setText(" " + f);

            boolean sd = amt >= f;
            Log.d("Boolean",""+sd);

            if(sd){
                final Dialog d = new Dialog(getActivity());
                d.setContentView(R.layout.goal_change_dialog);
                d.setTitle("Change Goal");
                d.show();
                Button b = (Button)d.findViewById(R.id.upbtn);
                Button bc = (Button)d.findViewById(R.id.cancel);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ds.delete();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        UpdateGoalFragment sa = new UpdateGoalFragment();
                        ft.replace(R.id.frame, sa);
                        ft.commit();
                    }
                });

                bc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
            }

            Log.d("resString", res.getString(0));
            Log.d("restInt", " " + res.getInt(1));
            res.close();
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



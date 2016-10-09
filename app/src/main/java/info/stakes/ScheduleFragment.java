package info.stakes;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SHRADDHA on 18-08-2016.
 */
public class ScheduleFragment extends Fragment {

    RecyclerView rv;
    RecyclerView.Adapter madap;

    EditText budget;
    Button confirmed;
    TextView sumhere;
    int flag = 0;

    ArrayList<ScheduleModel> ar = new ArrayList<>();
    ArrayList<Float> sum;

    DatabaseHelperSchedule db;
    DatabaseHelperBudget dbbudget;

    //ScheduleAdapter sh;
    //CheckAmount ca = new CheckAmount(getActivity());
    //SharedPrefsSchedule sh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_schedule, container, false);

        budget = (EditText) v.findViewById(R.id.mainbudgetschedule);

        sum = new ArrayList<>();
        budget.setText("0");
        confirmed = (Button) v.findViewById(R.id.scheduleconfirm);
        sumhere = (TextView) v.findViewById(R.id.schedulesum);

        rv = (RecyclerView) v.findViewById(R.id.schedulerecycle);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*sh = new SharedPrefsSchedule(getActivity());
        try {
            int total = sh.getAmount();
            sumhere.setText(""+total);
        }catch (Exception e)
        {
            Log.d("FirstTime",""+e);
        }*/

        db = new DatabaseHelperSchedule(getActivity());

        final Cursor res = db.getAllSch();

        res.moveToFirst();


        dbbudget = new DatabaseHelperBudget(getActivity());

        Cursor c = dbbudget.getAllBudget();

        Log.d("Cursorc", " " + c);

        if (c.getCount() > 0) {
            c.moveToFirst();
            int s = c.getInt(0);
            budget.setText("" + s);

            while (!res.isAfterLast()) {


                String field = res.getString(0);
                int x = res.getInt(1);

                Log.d("FieldSchedule", field);
                Log.d("PercentSche", " " + x);


                float amt = (x / 100.0f) * s;

                Log.d("Floatamt", " " + amt);
                ar.add(new ScheduleModel(field, amt));
                sum.add(amt);

                madap = new ScheduleAdapter(getActivity(), ar);

                rv.setAdapter(madap);

                res.moveToNext();
            }
            db.close();
            dbbudget.close();

            float val = addall(sum);
            sumhere.setText(""+val);

        } else {

            Toast.makeText(getActivity(), " Add Budget for the Day ", Toast.LENGTH_SHORT).show();
        }


        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //sh = new ScheduleAdapter();
                //float sum = sh.PassSum();
                String s = budget.getText().toString();
                int amt = Integer.parseInt(s);

                dbbudget = new DatabaseHelperBudget(getActivity());
                int check = dbbudget.UpdateBudget(amt);

                Log.d("CheckBudget", " " + check);

                if (check == 1) {

                    flag = 1;

                    //sumhere.setText(""+sum);
                    ar.removeAll(ar);
                    madap.notifyDataSetChanged();
                    rv.setAdapter(madap);

                    Toast.makeText(getActivity(), " Done Updation ", Toast.LENGTH_SHORT).show();
                } else if (check == 2) {
                    Toast.makeText(getActivity(), "Inserted ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Not Inserted ", Toast.LENGTH_SHORT).show();
                }
                dbbudget.close();
            }
        });

        //int sum = ca.getAmount();
        //sumhere.setText(""+sum);

        return v;
    }

    private float addall(ArrayList<Float> sum) {
        float total = 0;

        for(int i=0; i<sum.size(); i++)
        {
            float val = sum.get(i);
            total = total + val;
        }
        return total;

    }

}


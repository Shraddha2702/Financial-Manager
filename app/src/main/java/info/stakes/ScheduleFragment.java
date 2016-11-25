package info.stakes;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SHRADDHA on 18-08-2016.
 */
public class ScheduleFragment extends Fragment {


    RecyclerView rv;
    RecyclerView.Adapter ad;
    ArrayList<ScheduleModel> sh = new ArrayList<>();
    ArrayList<Float> sum = new ArrayList<>();

    Button confirm;
    TextView totalhere;
    TextView budgethere;
    TextView savingshere;

    DatabaseHelperSchedule db;
    DatabaseHelperBudget dbb;

    DatabaseHelperSavings ds;
    float amoun;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_schedule, container, false);


        rv = (RecyclerView) v.findViewById(R.id.schedulerecycle);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));


        totalhere = (TextView)v.findViewById(R.id.totalhere);
        budgethere = (TextView)v.findViewById(R.id.budgethere);
        savingshere = (TextView)v.findViewById(R.id.savingshere);

        ds = new DatabaseHelperSavings(getActivity());

        confirm = (Button)v.findViewById(R.id.scheduleconfirm);

        dbb = new DatabaseHelperBudget(getActivity());
        Cursor cbud = dbb.getAllBudget();
        cbud.moveToFirst();

        final int budamt = cbud.getInt(0);
        final int buddate = cbud.getInt(1);

        Calendar cal = Calendar.getInstance();
        final int pdate = cal.get(Calendar.DATE);

        if(pdate == buddate)
        {
            db = new DatabaseHelperSchedule(getActivity());

            Cursor c = db.getAllSch();
            c.moveToFirst();

            while (!c.isAfterLast()) {
                String field = c.getString(0);
                int Amount = c.getInt(1);
                int updated = c.getInt(2);

                if(updated == 0){
                amoun = (Amount / 100.0f) * budamt;}
                else {
                    amoun = (float)Amount;
                }
                sh.add(new ScheduleModel(field, amoun));
                sum.add(amoun);


                ad = new ScheduleAdapter(getActivity(), sh, new ScheduleAdapter.OnItemCheckListener() {
                    @Override
                    public void onItemCheck(final ScheduleModel item) {

                        final Dialog d = new Dialog(getActivity());
                        d.setContentView(R.layout.update_schedule_bud);
                        d.setTitle("Update Value");

                        final String field = item.getField();
                        final float Amount = item.getPercent();


                        final TextView pbudget = (TextView)d.findViewById(R.id.tvbudget);
                        final TextView pfield = (TextView)d.findViewById(R.id.tvfield);
                        final EditText et = (EditText) d.findViewById(R.id.etnewbud);
                        Button b = (Button) d.findViewById(R.id.btnconfirm);

                        pbudget.setText(""+Amount);
                        pfield.setText(field);
                        d.show();

                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                String f = et.getText().toString();
                                float amt = Float.parseFloat(f);
                                int amt1 = Integer.parseInt(f);

                                boolean ch = db.UpdateSchedule(field, amt1,1);

                                if (ch) {
                                    Log.d("InsertinPopUp", " " + ch);
                                }
                                d.dismiss();
                                Toast.makeText(getActivity(), "Updated ! ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                    @Override
                    public void onItemUncheck(ScheduleModel item) {

                        final Dialog d = new Dialog(getActivity());
                        d.setContentView(R.layout.update_schedule_bud);
                        d.setTitle("Update Value");

                        final String field = item.getField();
                        final float Amount = item.getPercent();


                        final TextView pbudget = (TextView)d.findViewById(R.id.tvbudget);
                        final TextView pfield = (TextView)d.findViewById(R.id.tvfield);
                        final EditText et = (EditText) d.findViewById(R.id.etnewbud);
                        Button b = (Button) d.findViewById(R.id.btnconfirm);

                        pbudget.setText(""+Amount);
                        pfield.setText(field);
                        d.show();

                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                String f = et.getText().toString();
                                float amt = Float.parseFloat(f);
                                int amt1 = Integer.parseInt(f);

                                boolean ch = db.UpdateSchedule(field, amt1,1);

                                if (ch) {
                                    Log.d("InsertinPopUp", " " + ch);
                                }
                                d.dismiss();
                                Toast.makeText(getActivity(), "Updated ! ", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                rv.setAdapter(ad);
                c.moveToNext();
            }


        }
        else
        {


            final Dialog d = new Dialog(getActivity());
            d.setContentView(R.layout.update_budget_schedule);
            d.setTitle("Update Budget");

            final EditText et = (EditText) d.findViewById(R.id.budpop);
            Button b = (Button) d.findViewById(R.id.budconfirm);
            d.show();

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String f = et.getText().toString();
                    int amt = Integer.parseInt(f);

                    Calendar cal = Calendar.getInstance();
                    int pdate = cal.get(Calendar.DATE);

                    boolean ch = dbb.insertBudget(amt,pdate);

                    if (ch) {
                        Log.d("InsertinPopUp", " " + ch);
                    }
                    d.dismiss();
                    Toast.makeText(getActivity(), "Not the same date", Toast.LENGTH_SHORT).show();
                }
            });
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float total = addall(sum);

                totalhere.setVisibility(View.VISIBLE);
                budgethere.setVisibility(View.VISIBLE);
                savingshere.setVisibility(View.VISIBLE);

                totalhere.setText(""+total);

                Cursor cb = dbb.getAllBudget();
                cb.moveToFirst();
                int budg = cb.getInt(0);
                float budget = (float)budg;

                budgethere.setText(""+budget);

                float save = budg -total;

                if(save >0.0)
                {
                    savingshere.setText("You saved "+save);
                    float t = 0;
                    float a =0;

                    Cursor c = ds.getAllSaving();
                    c.moveToFirst();

                    if(c.getCount() > 0) {
                        if(pdate == buddate) {
                            t = save;
                        }
                        else{
                            a = c.getFloat(0);
                            t = a + save;}
                        ds.insertSaving(t);
                    }else
                    {
                        ds.insertSaving(save);
                    }

                }else
                {
                    savingshere.setText("You wasted "+save);
                }
            }
        });

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



package info.stakes;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {

    public TextView amtdisplay;
    public Button btn;
    public List<DailyModel> currentSelectedItems;
    public DatabaseHelperDaily db;
    public ArrayList<DailyModel> lists;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView mRecyclerView;
    public List<Integer> sumlist;

    CheckAmount ca;
    int flag = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_todo, container, false);

        amtdisplay = (TextView) v.findViewById(R.id.amttodo);
        btn = (Button) v.findViewById(R.id.btnaddDaily);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        currentSelectedItems = new ArrayList<>();
        sumlist = new ArrayList<>();
        lists = new ArrayList<>();

        ca = new CheckAmount(getActivity());


        try {
            int am = ca.getAmount();
            amtdisplay.setText("" + am);
        } catch (Exception e) {
            Log.d("FirstTime", "e");
        }

        this.db = new DatabaseHelperDaily(getActivity());
        final Cursor c = db.getAllDaily();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String field = c.getString(0);
            final int amt = c.getInt(1);
            int check = c.getInt(2);

            lists.add(new DailyModel(field, amt, check));


            this.mAdapter = new TodoAdapter(getActivity(), lists, new TodoAdapter.OnItemCheckListener() {
                public void onItemCheck(DailyModel item) {
                    currentSelectedItems.add(item);
                    String s = item.getField();
                    int am = item.getAmount();
                    item.isSelected = 1;

                    int check = item.isSelected();

                    if (check == 1) {
                        sumlist.add(am);
                        //int sum = addsum(sumlist);
                        int sum = ca.getAmount() + am;
                        boolean ch = db.UpdateDaily(s, am, 1);

                        if (ch) {
                            amtdisplay.setText("" + sum);
                            ca.setAmount(sum);
                        }
                    }

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    TodoFragment sa = new TodoFragment();
                    ft.replace(R.id.frame,sa);
                    ft.commit();

                }

                public void onItemUncheck(DailyModel item) {
                    //currentSelectedItems.remove(item);
                    String s = item.getField();
                    int a = item.getAmount();

                    item.isSelected = 0;
                    int c = item.isSelected();

                    if (c == 0) {
                        for (int i = 0; i < currentSelectedItems.size(); i++) {
                            DailyModel d = currentSelectedItems.get(i);
                            String field = d.getField();

                            if (field.equals(s)) {
                                sumlist.remove(i);
                                currentSelectedItems.remove(i);
                            }
                        }

                        int sum = ca.getAmount() - a;

                        boolean upd = db.UpdateDaily(s, a, 0);
                        if (upd) {
                            amtdisplay.setText("" + sum);
                            ca.setAmount(sum);
                        } else {
                            Log.d("Error", "error");
                        }
                    }

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    TodoFragment sa = new TodoFragment();
                    ft.replace(R.id.frame,sa);
                    ft.commit();

                }
            });
            this.mRecyclerView.setAdapter(this.mAdapter);
            c.moveToNext();
        }

        for (int i = 0; i < lists.size(); i++) {
            DailyModel s = lists.get(i);
            int checkhere = s.isSelected();

            if (checkhere == 0) {
                flag = 1;
                break;
            }

        }

        if (flag == 0) {
            final Dialog d = new Dialog(getActivity());
            d.setContentView(R.layout.done_todo);
            d.setTitle("Congrats !!!");


            Button b = (Button) d.findViewById(R.id.btntododone);
            Button bkeep = (Button) d.findViewById(R.id.btnkeepcheck);
            d.show();

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < lists.size(); i++) {
                        DailyModel d = lists.get(i);
                        d.isSelected = 0;
                        String s = d.getField();
                        int a = d.getAmount();
                        boolean up = db.UpdateDaily(s, a, 0);

                        if (up) {
                            int sum = 0;
                            ca.setAmount(sum);
                            Log.d("Done", " " + up);
                        }
                    }

                    d.dismiss();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    TodoFragment sa = new TodoFragment();
                    ft.replace(R.id.frame,sa);
                    ft.commit();

                }
            });

            bkeep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    d.dismiss();

                }
            });

        }


        this.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(getActivity());
                d.setContentView(R.layout.custom_dialog_daily);
                d.setTitle("Add Elements");

                final AutoCompleteTextView tv = (AutoCompleteTextView) d.findViewById(R.id.etfieldtodo);
                final EditText et = (EditText) d.findViewById(R.id.etamttodo);
                Button b = (Button) d.findViewById(R.id.todoadd);
                d.show();

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String field = tv.getText().toString();
                        String f = et.getText().toString();
                        int amt = Integer.parseInt(f);

                        boolean ch = db.insertdaily(field, amt, 0);

                        if (ch) {
                            Log.d("InsertinPopUp", " " + ch);
                        }
                        d.dismiss();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        TodoFragment sa = new TodoFragment();
                        ft.replace(R.id.frame,sa);
                        ft.commit();

                    }
                });
            }
        });


        return v;
    }

    public int addsum(List<Integer> ar) {
        int i;
        int sum = 0;

        for (i = 0; i < ar.size(); i++) {
            sum = sum + ar.get(i);
        }
        return sum;
    }
}
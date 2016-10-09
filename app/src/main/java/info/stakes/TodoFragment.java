package info.stakes;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_todo, container, false);

        amtdisplay = (TextView)v.findViewById(R.id.amttodo);
        btn = (Button)v.findViewById(R.id.btnaddDaily);

        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        currentSelectedItems = new ArrayList<>();
        sumlist = new ArrayList<>();
        lists = new ArrayList<>();

        ca = new CheckAmount(getActivity());

        try
        {
            int am = ca.getAmount();
            amtdisplay.setText(""+am);
        }catch (Exception e)
        {
            Log.d("FirstTime","e");
        }

        this.db = new DatabaseHelperDaily(getActivity());
        final Cursor c = db.getAllDaily();
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            String field = c.getString(0);
            final int amt = c.getInt(1);
            int check = c.getInt(2);

            lists.add(new DailyModel(field, amt,check));

            this.mAdapter = new TodoAdapter(getActivity(), lists, new TodoAdapter.OnItemCheckListener()
            {
                public void onItemCheck(DailyModel item)
                {
                   currentSelectedItems.add(item);
                    String s = item.getField();
                    int am = item.getAmount();
                    item.isSelected = 1;

                    int check = item.isSelected();

                    if(check == 1)
                    {
                        sumlist.add(am);
                        int sum = addsum(sumlist);

                        boolean ch = db.UpdateDaily(s,am,1);

                        if(ch)
                        {
                            amtdisplay.setText(""+sum);
                            ca.setAmount(sum);
                        }
                    }

                }

                public void onItemUncheck(DailyModel item)
                {
                    //currentSelectedItems.remove(item);
                    String s = item.getField();
                    int a = item.getAmount();

                    item.isSelected = 0;
                    int c = item.isSelected();

                    if(c == 0)
                    {
                        for(int i = 0; i <currentSelectedItems.size(); i++)
                        {
                            DailyModel d = currentSelectedItems.get(i);
                            String field = d.getField();

                            if(field.equals(s))
                            {
                                sumlist.remove(i);
                                currentSelectedItems.remove(i);
                            }
                        }

                        int sum = addsum(sumlist);

                        boolean upd = db.UpdateDaily(s, a, 0);
                        if(upd)
                        {
                            amtdisplay.setText(""+sum);
                            ca.setAmount(sum);
                        }
                        else
                        {
                            Log.d("Error","error");
                        }
                    }
                }
            });
            this.mRecyclerView.setAdapter(this.mAdapter);
            c.moveToNext();
        }



        this.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(getActivity());
                d.setContentView(R.layout.custom_dialog_daily);
                d.setTitle("Add Elements");

                final AutoCompleteTextView tv =(AutoCompleteTextView) d.findViewById(R.id.etfieldtodo);
                final EditText et = (EditText)d.findViewById(R.id.etamttodo);
                Button b = (Button)d.findViewById(R.id.todoadd);
                d.show();

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String field = tv.getText().toString();
                        String f = et.getText().toString();
                        int amt = Integer.parseInt(f);

                        boolean ch = db.insertdaily(field, amt, 0);

                        if(ch) {
                            Log.d("InsertinPopUp", " " + ch);
                        }
                        d.dismiss();

                    }
                });}});


        return v;
    }

    public int addsum(List<Integer> ar)
    {
        int i;
        int sum =0;

        for(i=0; i<ar.size();i++)
        {
            sum = sum+ ar.get(i);
        }
       return sum;
    }
}
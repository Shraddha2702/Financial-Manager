package info.stakes;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by SHRADDHA on 02-10-2016.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MainModelHolder>
{
    Context c;
    ArrayList<ScheduleModel> ar;
    float sum;

    //SharedPrefsSchedule ca = new SharedPrefsSchedule(c);

    public ScheduleAdapter() {
    }

    public ScheduleAdapter(Context c, ArrayList<ScheduleModel> ar) {
        this.c = c;
        this.ar = ar;
    }

    public class MainModelHolder extends RecyclerView.ViewHolder {
        TextView tv;
        EditText te;

        public MainModelHolder(View itemView) {
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.onetext);
            te = (EditText)itemView.findViewById(R.id.oneedit);
        }
    }

    @Override
    public MainModelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_view_schedule,null);
        ScheduleAdapter.MainModelHolder mmh = new MainModelHolder(v);
        return mmh;
    }

    @Override
    public void onBindViewHolder(final MainModelHolder holder, int position) {
        holder.tv.setText(ar.get(position).getField());
        holder.te.setText(" "+ar.get(position).getPercent());


        try {
            //sum = ca.getAmount();
            float amt = Float.parseFloat(holder.te.getText().toString());
            sum = sum + amt;
            int sum1 = (int)sum;
           // ca.setAmount(sum1);
            Log.d("Sum"," "+sum);
        }
        catch (NullPointerException e)
        {
            Log.d("Exception","e");
        }

    }

    @Override
    public int getItemCount() {
        return ar.size();
    }


    public float PassSum()
    {
        return sum;
    }


}

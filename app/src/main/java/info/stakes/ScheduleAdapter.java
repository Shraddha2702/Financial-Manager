package info.stakes;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by SHRADDHA on 02-10-2016.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MainModelHolder>
{
    Context c;
    ArrayList<ScheduleModel> ar;
    float sum;

    @NonNull
    private OnItemCheckListener onItemClick;

    interface OnItemCheckListener {
        void onItemCheck(ScheduleModel item);
        void onItemUncheck(ScheduleModel item);
    }



    //SharedPrefsSchedule ca = new SharedPrefsSchedule(c);

    public ScheduleAdapter() {
    }

    public ScheduleAdapter(Context c, ArrayList<ScheduleModel> ar,@NonNull OnItemCheckListener onItemCheckListener) {
        this.c = c;
        this.ar = ar;
        this.onItemClick = onItemCheckListener;
    }


    public class MainModelHolder extends RecyclerView.ViewHolder {
        TextView tv;
        TextView te;
        CheckBox check;
        CardView schedule;

        public MainModelHolder(View itemView) {
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.onetext);
            te = (TextView)itemView.findViewById(R.id.oneedit);
            check = (CheckBox)itemView.findViewById(R.id.checkschedule);
            check.setClickable(false);
            schedule = (CardView)itemView.findViewById(R.id.cardviewschedule);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }

    @Override
    public MainModelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_view_schedule,null);
        ScheduleAdapter.MainModelHolder mmh = new MainModelHolder(v);
        return mmh;
    }

    @Override
    public void onBindViewHolder(final MainModelHolder holder, final int position) {


        if (holder instanceof MainModelHolder) {

            final ScheduleModel currentItem = ar.get(position);

            holder.tv.setText(ar.get(position).getField());
            holder.te.setText(" "+ar.get(position).getPercent());

            String[] array = c.getResources().getStringArray(R.array.rainbow2);
            String randomStr = array[new Random().nextInt(array.length)];
            holder.schedule.setCardBackgroundColor(Color.parseColor(randomStr));



            ((MainModelHolder) holder).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((MainModelHolder) holder).check.setChecked(
                            !((MainModelHolder) holder).check.isChecked());
                    if (((MainModelHolder) holder).check.isChecked()) {
                        onItemClick.onItemCheck(currentItem);
                    } else {
                        onItemClick.onItemUncheck(currentItem);
                    }
                }
            });
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

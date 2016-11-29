package info.stakes;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by SHRADDHA on 25-09-2016.
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MainModelHolder> {

    Context context;
    ArrayList<DailyModel> arrays;
    @NonNull
    private OnItemCheckListener onItemClick;


    interface OnItemCheckListener {
        void onItemCheck(DailyModel item);
        void onItemUncheck(DailyModel item);
    }


    public TodoAdapter(Context context, ArrayList<DailyModel> arrays, @NonNull OnItemCheckListener onItemCheckListener) {
        this.context = context;
        this.arrays = arrays;
        this.onItemClick = onItemCheckListener;
    }

    public class MainModelHolder extends RecyclerView.ViewHolder {
        TextView field;
        TextView amount;
        CheckBox check;
        CardView todo;

        public MainModelHolder(View itemView) {

            super(itemView);
            field = (TextView)itemView.findViewById(R.id.tvFielddaily);
            amount = (TextView)itemView.findViewById(R.id.tvAmountdaily);
            check = (CheckBox)itemView.findViewById(R.id.chkSelected);
            check.setClickable(false);
            todo = (CardView)itemView.findViewById(R.id.cardviewtodo);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }

    @Override
    public MainModelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_daily, null);
        MainModelHolder vd = new MainModelHolder(v);
        return vd;
    }

    @Override
    public void onBindViewHolder(final MainModelHolder holder, final int position) {


        if (holder instanceof MainModelHolder) {

            final DailyModel currentItem = arrays.get(position);

            holder.field.setText(arrays.get(position).getField());
            holder.amount.setText(Integer.toString(arrays.get(position).getAmount()));

            String[] array = context.getResources().getStringArray(R.array.rainbow1);
            String randomStr = array[new Random().nextInt(array.length)];
            holder.todo.setCardBackgroundColor(Color.parseColor(randomStr));

            int ce = arrays.get(position).isSelected();
            boolean check;
            if(ce == 0)
            {
                check = false;
            }
            else
            {
                check = true;
            }
            holder.check.setChecked(check);

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
        return arrays.size();
    }

    public ArrayList<DailyModel> getList() {
        return arrays;
    }


}
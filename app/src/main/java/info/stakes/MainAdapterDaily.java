package info.stakes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SHRADDHA on 11-09-2016.
 */
public class MainAdapterDaily extends RecyclerView.Adapter<MainAdapterDaily.MainModelHolder>{



    Context myContext;
    ArrayList<MainModelDaily> a2 = null;

    public MainAdapterDaily(Context myContext, ArrayList<MainModelDaily> a2) {
        this.myContext = myContext;
        this.a2 = a2;
    }

    @Override
    public MainModelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dailyinflate, parent, false);
        MainModelHolder mmh = new MainModelHolder(v);
        return mmh;
    }

    @Override
    public void onBindViewHolder(MainModelHolder holder, int position) {
        holder.field.setText(a2.get(position).getField());
        holder.amount.setText("" + a2.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return a2.size();
    }

    public class MainModelHolder extends RecyclerView.ViewHolder {

        TextView field;
        TextView amount;

        public MainModelHolder(View itemView) {
            super(itemView);
            field=(TextView)itemView.findViewById(R.id.tvfieldinflate);
            amount=(TextView)itemView.findViewById(R.id.tvamountinflate);
        }
    }
}

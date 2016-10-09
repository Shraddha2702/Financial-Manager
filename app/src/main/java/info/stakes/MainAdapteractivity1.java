package info.stakes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SHRADDHA on 10-08-2016.
 */
public class MainAdapteractivity1 extends RecyclerView.Adapter<MainAdapteractivity1.MainModelHolder> {
    Context myContext;
    ArrayList<MainModelactivity1> a = null;
    View v;

    public MainAdapteractivity1(Context myContext, ArrayList<MainModelactivity1> a) {
        this.myContext = myContext;
        this.a = a;
    }

    @Override
    public MainAdapteractivity1.MainModelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        MainModelHolder mmh = new MainModelHolder(v);
        return mmh;
    }

    public void removeV()
    {
        ((ViewGroup)v.getParent()).removeView(v);
    }

    public void removeVat()
    {
        ((ViewGroup)v.getParent()).removeViewAt(0);
    }



    @Override
    public void onBindViewHolder(MainAdapteractivity1.MainModelHolder holder, int position) {
        holder.e1.setText(a.get(position).getField());
        holder.e2.setText(" "+a.get(position).getPercent());
    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    public class MainModelHolder extends RecyclerView.ViewHolder {
        TextView e1;
        TextView e2;

        public MainModelHolder(View itemView) {
            super(itemView);
            e1 = (TextView) itemView.findViewById(R.id.field);
            e2 = (TextView) itemView.findViewById(R.id.percent);

        }
    }

}

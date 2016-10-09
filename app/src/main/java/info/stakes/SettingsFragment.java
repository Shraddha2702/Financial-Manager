package info.stakes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by SHRADDHA on 19-08-2016.
 */
public class SettingsFragment extends Fragment {


    RelativeLayout changegoal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_setting,container,false);

        changegoal= (RelativeLayout) v.findViewById(R.id.setgoal);

        changegoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FragmentManager fm= getFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();

                UpdateGoalFragment ug = new UpdateGoalFragment();
                ft.replace(R.id.frame,ug);
                ft.commit();
            }
        });
        return v;
    }
}

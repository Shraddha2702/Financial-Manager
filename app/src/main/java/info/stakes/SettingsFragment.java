package info.stakes;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by SHRADDHA on 19-08-2016.
 */
public class SettingsFragment extends Fragment {


    LinearLayout changegoal;
    LinearLayout setProfile;
    LinearLayout logout;
    LinearLayout setcontact;
    LinearLayout setmonthly;
    LinearLayout setdaily;

    Session session;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_setting,container,false);

        changegoal= (LinearLayout) v.findViewById(R.id.setgoal);
        setProfile = (LinearLayout) v.findViewById(R.id.setprofile);
        logout = (LinearLayout) v.findViewById(R.id.setLogout);
        setcontact = (LinearLayout) v.findViewById(R.id.setcontact);
        setmonthly = (LinearLayout) v.findViewById(R.id.setmonthly);
        setdaily = (LinearLayout) v.findViewById(R.id.setdaily);

        session = new Session(getActivity());



        setmonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SetMonthlyStatsFragment sm = new SetMonthlyStatsFragment();
                ft.replace(R.id.frame,sm);
                ft.commit();
            }
        });

        setdaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SetScheduleStatsFragment ss = new SetScheduleStatsFragment();
                ft.replace(R.id.frame, ss);
                ft.commit();
            }
        });


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

        setProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ChangeUsernameFragment sa = new ChangeUsernameFragment();
                ft.replace(R.id.frame,sa);
                ft.commit();
            }
        });

        setcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ContactUsFragment cu = new ContactUsFragment();
                ft.replace(R.id.frame, cu);
                ft.commit();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(getActivity());
                d.setContentView(R.layout.logout_main);
                d.setTitle("Log Out");

                Button yes = (Button) d.findViewById(R.id.yeslogout);
                Button no = (Button) d.findViewById(R.id.nologout);
                d.show();

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        session.setLoggedIn(false);

                        getActivity().deleteDatabase("DatabaseManager.db");
                        getActivity().deleteDatabase("Budget.db");
                        getActivity().deleteDatabase("DatabaseManagerDaily.db");
                        getActivity().deleteDatabase("Savings.db");
                        getActivity().deleteDatabase("ScheduleDatabase.db");
                        //getActivity().deleteDatabase("DatabaseManagerUser.db");

                        d.dismiss();

                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                        getActivity().finish();
                        System.exit(0);

                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
            }
        });
        return v;
    }
}

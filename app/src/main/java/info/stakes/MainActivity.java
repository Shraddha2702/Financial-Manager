package info.stakes;

import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.roughike.bottombar.OnSizeDeterminedListener;

public class MainActivity extends AppCompatActivity {

    BottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar=BottomBar.attachShy((CoordinatorLayout)findViewById(R.id.myCoordinator),
                findViewById(R.id.myScrollingContent),savedInstanceState);

        bottomBar.noTabletGoodness();

        Log.d("Height",""+bottomBar.getHeight());

        bottomBar.setItems(R.menu.main_menu);
        bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if(menuItemId==R.id.calender)
                {
                    ScheduleFragment f=new ScheduleFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                }

                else if (menuItemId==R.id.todo)
                {
                    TodoFragment t=new TodoFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,t).commit();
                }

                else if (menuItemId==R.id.goals)
                {
                    GoalFragment g=new GoalFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,g).commit();
                }

                else if(menuItemId==R.id.setting)
                {
                    SettingsFragment s=new SettingsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,s).commit();
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });

        bottomBar.mapColorForTab(0,"#009688");
        bottomBar.mapColorForTab(1,"#009688");
        bottomBar.mapColorForTab(2,"#009688");
        bottomBar.mapColorForTab(3,"#009688");

        bottomBar.setActiveTabColor("#009688");
        /* BottomBarBadge unread;
        unread=bottomBar.makeBadgeForTabAt(2,"#FF0000",13);
        unread.show();*/
    }
}

package info.stakes;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ScrollingView;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.roughike.bottombar.OnSizeDeterminedListener;

public class MainActivity extends AppCompatActivity {

    BottomBar bottomBar;
    NestedScrollView sv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar=BottomBar.attachShy((CoordinatorLayout)findViewById(R.id.myCoordinator),
                findViewById(R.id.myScrollingContent),savedInstanceState);

        sv = (NestedScrollView) findViewById(R.id.myScrollingContent);

        bottomBar.noTabletGoodness();

        Log.d("Height",""+bottomBar.getHeight());

        bottomBar.setItems(R.menu.main_menu);
        bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if(menuItemId==R.id.calender)
                {
                    //sv.setBackground(getDrawable(R.drawable.gridbackhorizontal));
                    ScheduleFragment f=new ScheduleFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                }

                else if (menuItemId==R.id.todo)
                {
                    //sv.setBackground(getDrawable(R.drawable.gridbackcspiral));
                    TodoFragment t=new TodoFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,t).commit();
                }

                else if (menuItemId==R.id.goals)
                {
                   // sv.setBackground(getDrawable(R.drawable.gridbackstar));
                    GoalFragment g=new GoalFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,g).commit();
                }

                else if(menuItemId==R.id.setting)
                {
                    //sv.setBackground(getDrawable(R.drawable.backnormal));
                    SettingsFragment s=new SettingsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,s).commit();
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });

        bottomBar.mapColorForTab(0,"#66ee22");
        bottomBar.mapColorForTab(1,"#66ee22");
        bottomBar.mapColorForTab(2,"#66ee22");
        bottomBar.mapColorForTab(3,"#66ee22");

        bottomBar.setActiveTabColor("#66ee22");
        /* BottomBarBadge unread;
        unread=bottomBar.makeBadgeForTabAt(2,"#FF0000",13);
        unread.show();*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        bottomBar.onSaveInstanceState(outState);
    }
}

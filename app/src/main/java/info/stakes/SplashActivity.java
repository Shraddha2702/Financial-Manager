package info.stakes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT=3000;
    Session session;
    DatabaseHelperSchedule dbs;
    DatabaseHelperDaily dbd;
    DatabaseHelper dbg;
    DatabaseHelperBudget dbb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dbs = new DatabaseHelperSchedule(getApplicationContext());
        dbd = new DatabaseHelperDaily(getApplicationContext());
        dbg = new DatabaseHelper(getApplicationContext());
        dbb = new DatabaseHelperBudget(getApplicationContext());

        final Cursor cs = dbs.getAllSch();
        final Cursor cd = dbd.getAllDaily();
        final Cursor cg = dbg.getAllGoals();
        final Cursor cb = dbb.getAllBudget();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                session = new Session(getApplicationContext());

                if(session.getLoggedIn().equals(true) && (cs != null) && (cd != null) && (cg != null) && (cb != null))
                {
                    Intent i = new Intent(SplashActivity.this,MainActivity.class);
                    overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_up);
                    startActivity(i);
                    finish();
                }

                else if(session.getLoggedIn().equals(true) && (cs == null))
                {
                    Intent i = new Intent(SplashActivity.this, Activity1.class);
                    startActivity(i);
                     finish();
                }

                else if(session.getLoggedIn().equals(true) && (cs != null) && (cd == null))
                {
                    Intent i = new Intent(SplashActivity.this, AskDailyActivity.class);
                    startActivity(i);
                    finish();
                }

                else if(session.getLoggedIn().equals(true) && (cs != null) && (cd != null) && (cg == null))
                {
                    Intent i = new Intent(SplashActivity.this, AskGoalActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(SplashActivity.this,LoginPage.class);
                    overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_up);
                    startActivity(i);
                    finish();
                }

            }

        },SPLASH_TIME_OUT);
    }
}

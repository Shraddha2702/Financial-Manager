package info.stakes;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AskGoalActivity extends AppCompatActivity {

    TextView goal;

    ImageView next;
    ImageView previous;
    RelativeLayout rl;
    Button submit;
    TextView goaltext;
    TextView amount;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_goal);

        db = new DatabaseHelper(getApplicationContext());

        rl=(RelativeLayout)findViewById(R.id.rlgoal);
        next=(ImageView)findViewById(R.id.circleleftgoal);
        previous=(ImageView)findViewById(R.id.circlerightgoal);
        goal=(TextView)findViewById(R.id.tvaskgoal);
        submit=(Button)findViewById(R.id.submitgoal);

        goaltext=(TextView)findViewById(R.id.entergoal);
        amount=(TextView)findViewById(R.id.enteramountgoal);


        previous.setEnabled(false);

        goal.setText("In this Section, you can add up if you want to save for something," +
                "and helps you keep a track of all your savings in a systematic way.");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.setVisibility(View.VISIBLE);

                String goalmain=goaltext.getText().toString();
                String am=amount.getText().toString();
                int amountmain=Integer.parseInt(am);

                boolean isInserted = db.insertgoal(goalmain,amountmain);

                if(isInserted == true )
                {
                    Toast.makeText(getApplicationContext(), "Data Inserted Successfully", Toast.LENGTH_LONG).show();

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i=new Intent(AskGoalActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error in Inserting",Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}

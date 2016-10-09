package info.stakes;

import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AskDailyActivity extends AppCompatActivity {

    RecyclerView rvMain;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLM;

    ArrayList<MainModelDaily> arrayName = new ArrayList<>();

    TextView lower;
    TextView upper;
    EditText amt;
    AutoCompleteTextView field;
    Button btnadd;
    Button btndone;
    int sum;

    ImageView next;
    ImageView previous;
    RelativeLayout rl;

    DatabaseHelperDaily db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_daily);

        next = (ImageView) findViewById(R.id.circleleftdaily);
        previous = (ImageView) findViewById(R.id.circlerightdaily);
        rl = (RelativeLayout) findViewById(R.id.rlad);

        previous.setEnabled(false);

        rvMain = (RecyclerView) findViewById(R.id.rvdaily);
        rvLM = new LinearLayoutManager(AskDailyActivity.this);
        rvMain.setLayoutManager(rvLM);
        rvMain.setHasFixedSize(true);


        lower = (TextView) findViewById(R.id.tvinflate);
        upper = (TextView) findViewById(R.id.tvdaily);
        upper.setText("There are things where you need to invest every month without any options like" +
                "Rents, Loans, Electricity Bills, etc. Above you need to add all such fields, and hit ADD" +
                "to keep a track of all such activities.");

        field = (AutoCompleteTextView) findViewById(R.id.etfield);
        amt = (EditText) findViewById(R.id.etamt);
        btnadd = (Button) findViewById(R.id.btnadddaily);
        btndone = (Button) findViewById(R.id.btndone);

        db = new DatabaseHelperDaily(getApplicationContext());

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fie = field.getText().toString();
                String a = amt.getText().toString();
                int amt1 = Integer.parseInt(a);


                boolean insert = db.insertdaily(fie, amt1,0);

                if (insert == true) {
                    sum = sum + amt1;
                    Toast.makeText(getApplicationContext(), "Inserted Successfully ", Toast.LENGTH_LONG).show();
                    arrayName.add(new MainModelDaily(fie, amt1));
                    rvAdapter = new MainAdapterDaily(getApplicationContext(), arrayName);
                    rvMain.setAdapter(rvAdapter);

                } else {
                    Toast.makeText(getApplicationContext(), "Can't Insert, Duplication ! ", Toast.LENGTH_LONG).show();
                }


            }
        });


        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnadd.setEnabled(false);
                lower.setVisibility(View.VISIBLE);
                lower.setText("Your Total Amount sums up to Rs. " + sum + " for the Monthly Expenses");
                btndone.setEnabled(false);
                rl.setVisibility(View.VISIBLE);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AskDailyActivity.this, AskGoalActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}

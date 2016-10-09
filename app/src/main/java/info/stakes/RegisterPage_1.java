package info.stakes;

/**
 * Created by SHRADDHA on 09-08-2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class RegisterPage_1 extends AppCompatActivity {



    ImageView next;
    ImageView previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page_1);


        next=(ImageView)findViewById(R.id.circleleft);
        previous=(ImageView)findViewById(R.id.circleright);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RegisterPage_1.this,LoginPage.class);
                startActivity(i);
                finish();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RegisterPage_1.this,RegisterPage_2.class);
                startActivity(i);
                finish();
            }
        });
    }
}



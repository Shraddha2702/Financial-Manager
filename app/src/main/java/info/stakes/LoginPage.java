package info.stakes;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    Button login;
    TextView acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        login=(Button)findViewById(R.id.login);
        acc=(TextView)findViewById(R.id.createacc);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Quando-Regular.ttf");

        login.setTypeface(custom_font);

        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginPage.this,RegisterPage_1.class);
                startActivity(i);
                finish();
            }
        });
    }
}

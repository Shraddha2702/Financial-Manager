package info.stakes;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    Button login;
    TextView acc;
    EditText user,password;
    DatabaseHelperUser db;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        login=(Button)findViewById(R.id.login);
        acc=(TextView)findViewById(R.id.createacc);
        user = (EditText)findViewById(R.id.loginemail);
        password = (EditText)findViewById(R.id.loginpassword);
        db = new DatabaseHelperUser(getApplicationContext());


        session = new Session(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String uname = user.getText().toString();
                    String pswd = password.getText().toString();

                    int flag = 0;
                    Cursor c = db.getAllUsers();

                    c.moveToFirst();

                    while (!c.isAfterLast())
                    {
                        String uname1 = c.getString(1);
                        String pswd1 = c.getString(2);

                        if(uname1.equals(uname) && pswd1.equals(pswd))
                        {
                            flag = 1;
                            Log.d("Login","Login Successful Match found");

                            session.setLoggedIn(true);
                            Intent i = new Intent(LoginPage.this,Activity1.class);
                            startActivity(i);
                            finish();
                            break;
                        }
                        else
                        {
                            Log.d("match","not found");
                        }
                        c.moveToNext();
                    }

                    if(flag == 0)
                    {
                        Toast.makeText(getApplicationContext(),"Enter Correct Details or Register below !",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Enter your Details first ",Toast.LENGTH_SHORT).show();
                }


            }
        });

        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginPage.this,RegisterPage_1.class);
                startActivity(i);
                finish();
            }
        });
    }
}

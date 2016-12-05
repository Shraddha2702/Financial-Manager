package info.stakes;

/**
 * Created by SHRADDHA on 09-08-2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterPage_1 extends AppCompatActivity {


    Button Register;
    EditText user,email,pswd,cpswd;
    DatabaseHelperUser db;
    UserInformation userinfo;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page_1);


        Register=(Button)findViewById(R.id.btnreg);
        user = (EditText)findViewById(R.id.reguser);
        email = (EditText)findViewById(R.id.regemail);
        pswd = (EditText)findViewById(R.id.regpass);
        cpswd = (EditText)findViewById(R.id.regrepass);

        db = new DatabaseHelperUser(getApplicationContext());
        userinfo = new UserInformation(getApplicationContext());
        session = new Session(getApplicationContext());

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = user.getText().toString();
                    String mail = email.getText().toString();
                    String password = pswd.getText().toString();
                    String repassword = cpswd.getText().toString();

                    if (password.equals(repassword)) {
                        boolean insert = db.insertUser(name, mail, password);

                        if (insert) {
                            Log.d("RegisterUser", "User Registered Successfully !");
                            userinfo.setUsername(name);
                            userinfo.setEmail(mail);
                            userinfo.setPassword(password);

                            session.setLoggedIn(true);

                            Intent i = new Intent(RegisterPage_1.this, Activity1.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Not Inserted", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Enter same password in both fields !", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Data first ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}



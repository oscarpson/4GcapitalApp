package joslabs.a4gcapitalapp.reglogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

import joslabs.a4gcapitalapp.R;

public class Login extends AppCompatActivity implements View.OnClickListener {
Button btnlog,btnreg;
EditText edtusername,password;
DatabaseReference dbref;
String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edtusername=findViewById(R.id.edtname);
        password=findViewById(R.id.edtidno);
        btnlog=findViewById(R.id.btnlog);
        btnreg=findViewById(R.id.btnreg);

        btnlog.setOnClickListener(this);
        btnreg.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnlog:
                LoginUser();
                break;
            case R.id.btnreg:
                Intent ints=new Intent(getApplicationContext(),Register.class);
                startActivity(ints);
                break;
        }

    }

    private void LoginUser() {
    }
}

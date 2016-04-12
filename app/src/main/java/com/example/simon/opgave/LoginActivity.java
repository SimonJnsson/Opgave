package com.example.simon.opgave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    DBHandler dbHandler;
    Button btnLogin;

    EditText eTUsername, eTPassword;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnConfirmLogin);
        eTUsername = (EditText) findViewById(R.id.eTUsername);
        eTPassword = (EditText) findViewById(R.id.eTPassword);

        dbHandler = new DBHandler(this, null);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.equals(btnLogin))
        {
            username = eTUsername.getText().toString();
            password = eTPassword.getText().toString();

            MessageDigest m= null;
            try
            {
                m = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }

            m.update(password.getBytes(),0,password.length());
            String hashedPassword = new BigInteger(1,m.digest()).toString(16);

            if(dbHandler.confirmLogIn(username, hashedPassword))
            {
                Toast.makeText(getApplicationContext(), "Logged in.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Incorrect username/password.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

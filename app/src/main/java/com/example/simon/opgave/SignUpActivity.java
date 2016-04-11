package com.example.simon.opgave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener
{

    Button btnConfirm;
    EditText eTUsername, eTPassword;
    String newUsername, newPassword;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        eTUsername = (EditText) findViewById(R.id.eTSignUpUsername);
        eTPassword = (EditText) findViewById(R.id.eTSignUpPassword);
        btnConfirm = (Button) findViewById(R.id.btnConfirmSignUp);
        dbHandler = new DBHandler(this, null);

        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.equals(btnConfirm))
        {
            newUsername = eTUsername.getText().toString();
            newPassword = eTPassword.getText().toString();

            MessageDigest m= null;
            try
            {
                m = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }

            m.update(newPassword.getBytes(),0,newPassword.length());
            String hashedPassword = new BigInteger(1,m.digest()).toString(16);

            if(!newUsername.equals("") && !newUsername.contains(" ") && !newPassword.equals("") && !newPassword.contains(" "))
            {
                if(dbHandler.usernameAvailable(newUsername))
                {
                    User user = new User(newUsername, hashedPassword, 0);
                    dbHandler.adduser(user);

                    Toast.makeText(getApplicationContext(), "Sign up successful, please login.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Username already in use.", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                if(newUsername.equals("") || newUsername.contains(" "))
                    Toast.makeText(getApplicationContext(), "Invalid username", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
            }

            Log.i("LOG", "Log: \n" + dbHandler.ToString());
        }
    }
}

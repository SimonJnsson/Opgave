package com.example.simon.opgave;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button logInBtn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logInBtn = (Button) findViewById(R.id.btnLogin);
        signUpBtn = (Button) findViewById(R.id.btnSignUp);


        logInBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.equals(logInBtn))
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if(v.equals(signUpBtn))
        {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
    }
}

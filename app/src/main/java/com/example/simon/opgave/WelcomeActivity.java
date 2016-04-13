package com.example.simon.opgave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class WelcomeActivity extends AppCompatActivity
{
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btn1 = (Button) findViewById(R.id.btnLogin);
    }

    public void RunCompass(View v)
    {
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }
}

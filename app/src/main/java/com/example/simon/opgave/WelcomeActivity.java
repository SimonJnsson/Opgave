package com.example.simon.opgave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    public void OnBtnClick(View v){
        if (v == findViewById(R.id.GPSBtn)){
            Intent intent = new Intent(this, LocationActivity.class);
            startActivity(intent);

        }
    }
}

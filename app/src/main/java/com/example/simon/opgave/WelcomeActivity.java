package com.example.simon.opgave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener
{

    Button btnPlay, btnLight, btn1,btnGps,btnCompass;

    @Override
    public void onClick(View v)
    {
        if (v.equals(btnPlay))
        {
            Intent i = new Intent(this, GameActivity.class);
            startActivity(i);
        }
        else if (v.equals(btnLight))
        {
            Intent intent = new Intent(this, Lightsensor.class);
            startActivity(intent);
        }
        else if(v.equals(btnGps))
        {
            Intent i = new Intent(this, LocationActivity.class);
            startActivity(i);
        }
        else if(v.equals(btnCompass))
        {
            Intent i = new Intent(this, CompassAppActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnGps = (Button) findViewById(R.id.btnGps);
        btnLight = (Button) findViewById(R.id.btn_Lightsensor);
        btnCompass = (Button) findViewById(R.id.btnCompass);

        btnPlay.setOnClickListener(this);
        btnGps.setOnClickListener(this);
        btnLight.setOnClickListener(this);
        btnCompass.setOnClickListener(this);


        btn1 = (Button) findViewById(R.id.btnLogin);
    }
}

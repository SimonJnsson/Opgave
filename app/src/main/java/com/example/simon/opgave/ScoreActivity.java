package com.example.simon.opgave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button btnBack;
    private TextView scores;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        btnBack = (Button) findViewById(R.id.btnBack);
        scores = (TextView) findViewById(R.id.tvScores);
        db = new DBHandler(this, null);

        scores.setText(db.ToString(true));
        btnBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        if(v.equals(btnBack))
        {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

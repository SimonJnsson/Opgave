package com.example.simon.opgave;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity
{
    private static final String LOG = "MyActivityLog";
    public int guess, numToGuess, guessesLeft;
    public final int START_GUESS = 10;
    TextView txtInfo;
    EditText txtGuess;

    private boolean isEmpty(EditText etText)
    {
        //If the trimmed text is of length 0, the EditText element is empty
        return etText.getText().toString().trim().length() == 0;
    }

    private int GetRandomNum(int min, int max)
    {
        //               Min + (int)(Math.random() * ((Max - Min) + 1))
        int randomNum = (min + (int) (Math.random() * ((max - min) + 1)));
        Log.v(LOG, "Random number generated: " + randomNum);
        return randomNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        SharedPreferences sp = getSharedPreferences("savedVal",MODE_PRIVATE);

        try
        {
            if (sp.contains("number")){
                numToGuess = sp.getInt("number", 0);
                Log.v(LOG, "Number passed through sharedPref: " + numToGuess);
            }
        }
        catch(Exception e)
        {
            numToGuess = GetRandomNum(0, 100);

            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("number", numToGuess);
            editor.commit();
        }

        guessesLeft = START_GUESS;

        txtInfo = (TextView) findViewById(R.id.TxtInfo);
        txtGuess = (EditText) findViewById(R.id.EditTxtGuess);
        txtInfo.setText("");
    }


    private void ResetGame()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtGuess.getWindowToken(), 0);

        Context context = getApplicationContext();
        String text = "New number generated";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                txtInfo.setText("");
                txtGuess.setText("");
            }
        }, 2000);

        numToGuess = GetRandomNum(0, 100);
        guessesLeft = START_GUESS;
    }

    public void OnClick(View v)
    {
        // If EditText not empty and a number between 0 - 100 has been entered
        if (!isEmpty(txtGuess) && (Integer.parseInt(txtGuess.getText().toString()) >= 0 && Integer.parseInt(txtGuess.getText().toString()) <= 100))
        {
            guessesLeft--;
            guess = Integer.parseInt(txtGuess.getText().toString());

            if (guess == numToGuess)
            {
                // Only one guess was used
                if (guessesLeft == START_GUESS - 1)
                {
                    txtInfo.setText("Correct! You guessed '" + numToGuess + "' in " + (START_GUESS - guessesLeft) + " guess");
                }
                else
                {
                    txtInfo.setText("Correct! You guessed '" + numToGuess + "' in " + (START_GUESS - guessesLeft) + " guesses");
                }

                ResetGame();

            }
            else
            {
                if (guess > numToGuess)
                {
                    txtInfo.setText("Lower! " + guessesLeft + " guesses left");
                }
                else
                {
                    txtInfo.setText("Higher! " + guessesLeft + " guesses left");
                }

                txtGuess.setText("");
            }
        }
        else
        {
            txtInfo.setText("Enter a number between 0 - 100!");
            txtGuess.setText("");
        }

        if (guessesLeft == 0)
        {
            txtInfo.setText("No more guesses!\nThe number was " + numToGuess);
            ResetGame();
        }
    }
}


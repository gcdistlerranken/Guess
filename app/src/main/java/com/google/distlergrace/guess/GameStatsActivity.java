package com.google.distlergrace.guess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameStatsActivity extends AppCompatActivity
{
    TextView    textViewGameStats;
    Button      buttonReturnHome;

    Integer attempts;
    Integer randomNumber;
    Integer numberOfGames;
    String  result;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats);
        textViewGameStats   = findViewById(R.id.textViewGameStats);
        buttonReturnHome    = findViewById(R.id.buttonReturnHome);

        //Get The Intent In The Target Activity
        Intent intent = getIntent();

        //Get The Attached Bundle From The Intent
        Bundle extras = intent.getExtras();

        //Extract The Stored Data From The Bundle
        if (extras != null) {
            if (extras.containsKey("attempts")) {
                attempts = extras.getInt("attempts", 0);
            }

            if (extras.containsKey("randomNumber")) {
                randomNumber = extras.getInt("randomNumber", 0);
            }


            if (extras.containsKey("numberOfGames")) {
                numberOfGames = extras.getInt("numberOfGames", 0);
            }

            result = "\n\tNumber To Find: " + randomNumber + "\n\n";
            result += "\tAttempts: " + attempts + "\n\n";
            result += "\tStatus: " + "You found the secret \n\t\t\t\t\t\t\t\t\t number ("
                       + randomNumber + ") in " + attempts + " guesses." + "\n\n";
            result += "\tNumber Of Games: " + numberOfGames + "\n\n";

            textViewGameStats.setText(result);
        }

        buttonReturnHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}

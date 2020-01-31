package com.google.distlergrace.guess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    //Program Constants
    final int MINNUMBER         = 1;
    final int MAXNUMBER         = 100;
    final String INVALIDINPUT   = "INVALID GUESS INPUTTED!\nRESETTING AND RETURNING.";
    final String OORINPUT       = "GUESS INPUTTED OUT OF RANGE!\nGuess Must Be Between " +
                                   MINNUMBER + " and " + MAXNUMBER;
    final String GUESSLOW       = "Guess Too Low. Try Again.";
    final String GUESSHIGH      = "Guess Too High. Try Again.";
    final String GUESSCORRECT   = "Congratulations!\nGuess Correct! You Did It!";

    //Program Widget Variables
    EditText    editTextGuessInput;
    Button      buttonCalculate;
    Button      buttonClear;
    Button      buttonNewGame;
    Button      buttonStats;
    TextView    textViewResults;

    //Program Non-Widget Variables
    int guess               = 0;
    int attempts            = 0;
    int numberOfGames       = 0;
    Random randomGenerator  = new Random();
    int randomNumber        = 0;
    String guessStatus      = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextGuessInput  = (EditText) findViewById(R.id.editTextGuessInput);
        buttonCalculate     = (Button)   findViewById(R.id.buttonCalculate);
        buttonClear         = (Button)   findViewById(R.id.buttonClear);
        buttonNewGame       = (Button)   findViewById(R.id.buttonNewGame);
        buttonStats         = (Button)   findViewById(R.id.buttonStats);
        textViewResults     = (TextView) findViewById(R.id.textViewResults);

        randomGenerator = new Random();
        randomNumber    = randomGenerator.nextInt(MAXNUMBER) + 1;


        buttonCalculate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean keepGoing = validateGuess();

                if (keepGoing)
                {
                    compareGuess();
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clearAll();
            }
        });

        buttonNewGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                newGame();
            }
        });

        buttonStats.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Create A Bundle Object
                Bundle extras = new Bundle();
                extras.putInt("attempts", attempts);
                extras.putInt("numberOfGames", numberOfGames);
                extras.putInt("randomNumber", randomNumber);

                //Create & Initialize An Intent
                Intent statsIntent = new Intent(getApplicationContext(),
                                                GameStatsActivity.class);

                //Attach The Bundle To The Intent Object
                statsIntent.putExtras(extras);

                //Start The Activity
                startActivity(statsIntent);
            }
        });
    }

    private boolean validateGuess()
    {
        try
        {
            //Read Value From editTextGuessInput
            guess = Integer.parseInt(editTextGuessInput.getText().toString());

            //Make Sure Guess Is Within Range (1-100)
            while ((guess < MINNUMBER) || (guess > MAXNUMBER))
            {
                guess = 0;
                editTextGuessInput.setText("");
                editTextGuessInput.requestFocus();
                throw new NumberFormatException();
            }

            return true;
        }
        catch (NumberFormatException nfe)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                                         INVALIDINPUT,
                                         Toast.LENGTH_LONG);
            toast.show();

            return false;
        }
    }

    private String compareGuess()
    {
        String s = "";

        if (guess < randomNumber)
        {
            ++attempts;
            s = "Guess Too Low";
            Toast toast = Toast.makeText(getApplicationContext(),
                    GUESSLOW,
                    Toast.LENGTH_LONG);
            toast.show();
        }
        else if (guess > randomNumber)
        {
            ++attempts;
            s = "Guess Too High.";
            Toast toast = Toast.makeText(getApplicationContext(),
                    GUESSHIGH,
                    Toast.LENGTH_LONG);
            toast.show();
        }
        else if (guess == randomNumber)
        {
            ++attempts;
            ++numberOfGames;
            s = "You found the secret number (" + randomNumber + ") in " +
                    attempts + " guesses.";
            guessStatus = "You found the secret number (" + randomNumber + ") in " +
                    attempts + " guesses.";
            textViewResults.setText(guessStatus);
            Toast toast = Toast.makeText(getApplicationContext(),
                    GUESSCORRECT,
                    Toast.LENGTH_LONG);
            toast.show();
        }

        return s;
    }

    private void clearAll()
    {
        editTextGuessInput.setText("");
        editTextGuessInput.requestFocus();
        textViewResults.setText("");
        attempts        = 0;
        numberOfGames   = 0;
    }

    private void newGame()
    {
        editTextGuessInput.setText("");
        editTextGuessInput.requestFocus();
        textViewResults.setText("");
        attempts        = 0;
        randomGenerator = new Random();
        randomNumber    = randomGenerator.nextInt(MAXNUMBER) + 1;
    }

    private void showStats()
    {

    }
}

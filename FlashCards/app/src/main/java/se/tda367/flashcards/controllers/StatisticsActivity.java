package se.tda367.flashcards.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.Deck;

public class StatisticsActivity extends AppCompatActivity {

    private Deck currentDeck;
    private TextView timePlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        DecimalFormat df = new DecimalFormat("#.##");
        currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();
        timePlayed = (TextView) findViewById(R.id.averageTime);
        timePlayed.setText(df.format(currentDeck.getAmountOfTimePlayed()/currentDeck.getNbrOfCardsPlayed()/1000) + " seconds");
    }
}

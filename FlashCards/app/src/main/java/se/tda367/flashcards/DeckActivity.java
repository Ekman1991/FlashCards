package se.tda367.flashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DeckActivity extends AppCompatActivity {
    private Deck currentDeck;
    private TextView name;
    private TextView numberOfCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();
        name = (TextView) findViewById(R.id.deckName);
        numberOfCards = (TextView) findViewById(R.id.numberOfCards);

        name.setText(currentDeck.getName());
        numberOfCards.setText(Integer.toString(currentDeck.getSize()));
    }

}

package se.tda367.flashcards.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import se.tda367.flashcards.services.JsonService;
import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.Card;
import se.tda367.flashcards.models.Deck;

public class ImportCardsActivity extends AppCompatActivity {
    private Deck currentDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_cards);

        Log.v("ImportCardsActivity", "On Create");
        currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();

        View importButton = findViewById(R.id.importButton);
        importCards(importButton);
    }
    public void importCards(View v){
        JsonService jc = new JsonService();
        Deck deck = currentDeck;
        String url = Singleton.getInstance().getFlashCards().getUrl();
        JSONObject object = jc.toJson(jc.fromURL(url));
        try {
            for(int i = 0; i<(object.length()-2)/2; i++) {
                Card tmp = new Card(object.getString(i + "question"), object.getString(i + "answer"));
                Singleton.getInstance().getDatabaseController(getApplicationContext()).createCardInDeck(tmp, deck);
                deck.addCard(tmp);
            }

        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"No cards found", Toast.LENGTH_SHORT).show();
        }

        Intent intentMain = new Intent(ImportCardsActivity.this ,
                DeckActivity.class);
        ImportCardsActivity.this.startActivityForResult(intentMain, 0);

    }
}

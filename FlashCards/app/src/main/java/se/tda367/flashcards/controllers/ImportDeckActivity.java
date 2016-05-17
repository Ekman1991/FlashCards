package se.tda367.flashcards.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import se.tda367.flashcards.JsonConverter;
import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.Deck;


public class ImportDeckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_deck);

        Intent intent = getIntent();
        String url = intent.getDataString().substring(13);
        Singleton.getInstance().getFlashCards().setUrl(url);
        importDeck(url);

    }
    public void importDeck(String url){
        JsonConverter jc = new JsonConverter();
        JSONObject object = jc.toJson(jc.fromURL(url));
        Deck deck = new Deck(object);
        Singleton.getInstance().getDatabaseController(getApplicationContext()).createDeck(deck);
        int length = Singleton.getInstance().getDatabaseController(getApplicationContext()).getAllDecks().size() - 1;
        Singleton.getInstance().getFlashCards().setCurrentDeck(Singleton.getInstance().getDatabaseController(getApplicationContext()).getAllDecks().get(length));

        Intent intentMain = new Intent(ImportDeckActivity.this ,
                ImportCardsActivity.class);
        ImportDeckActivity.this.startActivityForResult(intentMain, 0);

    }

}

package se.tda367.flashcards.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import se.tda367.flashcards.services.JsonService;
import se.tda367.flashcards.R;
import se.tda367.flashcards.ServiceLocator;
import se.tda367.flashcards.models.Deck;


public class ImportDeckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_deck);

        Intent intent = getIntent();
        //removes the app custom uri scheme from the link so that only the actual url remains
        String url = intent.getDataString().substring(13);
        ServiceLocator.getInstance().getFlashCards().setUrl(url);
        importDeck(url);

    }
    public void importDeck(String url){
        JsonService jc = new JsonService();
        JSONObject object = jc.toJson(jc.fromURL(url));
        Deck deck = new Deck(object);
        ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).createDeck(deck);
        int length = ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).getAllDecks().size() - 1;
        ServiceLocator.getInstance().getFlashCards().setCurrentDeck(ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).getAllDecks().get(length));

        Intent intentMain = new Intent(ImportDeckActivity.this ,
                ImportCardsActivity.class);
        ImportDeckActivity.this.startActivityForResult(intentMain, 0);

    }

}

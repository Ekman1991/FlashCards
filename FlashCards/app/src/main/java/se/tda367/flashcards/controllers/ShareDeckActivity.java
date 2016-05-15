package se.tda367.flashcards.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import se.tda367.flashcards.JsonConverter;
import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.Deck;

public class ShareDeckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_deck);
        Deck deck = Singleton.getInstance().getFlashCards().getCurrentDeck();
        EditText uriText = (EditText) findViewById(R.id.uriText);
        try {
            JsonConverter jc = new JsonConverter(deck);
            uriText.setText(jc.toUri(jc.toURL(jc.getJsonString())));
        }
        catch(Exception e){

        }

    }
    public void backButton(View v){
        Intent intent = new Intent(ShareDeckActivity.this, DeckActivity.class);
        ShareDeckActivity.this.startActivityForResult(intent, 0);
    }
}

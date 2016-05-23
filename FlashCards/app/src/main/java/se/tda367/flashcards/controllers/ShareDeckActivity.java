package se.tda367.flashcards.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import se.tda367.flashcards.services.JsonService;
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
            JsonService jc = new JsonService(deck);
            uriText.setText(jc.toUri(jc.toURL(jc.getJsonString())));
        }
        catch(Exception e){

        }

    }
    public void backButton(View v){
        Intent intent = new Intent(ShareDeckActivity.this, DeckActivity.class);
        ShareDeckActivity.this.startActivityForResult(intent, 0);
    }
    public void sendEmailButton(View v){
        EditText uri = (EditText)findViewById(R.id.uriText);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Flash Cards Deck URL");
        i.putExtra(Intent.EXTRA_TEXT   , uri.getText().toString());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ShareDeckActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}

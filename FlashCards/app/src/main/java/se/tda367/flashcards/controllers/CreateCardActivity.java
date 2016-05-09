package se.tda367.flashcards.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import se.tda367.flashcards.CardFactory;
import se.tda367.flashcards.MyNotification;
import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.Card;
import se.tda367.flashcards.models.Deck;

public class CreateCardActivity extends AppCompatActivity {
    EditText question;
    EditText answer;
    CardFactory cardFactory;
    MyNotification nf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        question = (EditText)findViewById(R.id.questionField);
        answer = (EditText)findViewById(R.id.answerField);
        cardFactory = new CardFactory();
    }
    public void notify(View v){
        nf.addNotification();
        nf.notify();
    }
    public void createCardButton(View v) {

        String questionText;
        String answerText;
        Deck currentDeck;

        //TODO: Move this to a utility class. Will be duplicated all over the codebase
        if ((question == null || question.getText().toString().trim().length() == 0) && (answer == null || answer.getText().toString().trim().length() == 0)) {
            Log.d("CreateCard", "DeckName is empty");
        } else {
            questionText = question.getText().toString();
            answerText = answer.getText().toString();
            currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();

            Card card = new Card(questionText, answerText);
            Singleton.getInstance().getDatabaseController(getApplicationContext()).createCardInDeck(card, currentDeck);
            //TODO: Replace this, will easily be duplicates of cards. Refetch from database instead.
            currentDeck.addCard(card);


            Intent intentMain = new Intent(CreateCardActivity.this ,
                    DeckActivity.class);
            CreateCardActivity.this.startActivityForResult(intentMain, 0);
        }

    }
    public void cancelCreateCard(View v) {
        Log.d("YourTag", "YourOutput");
        Intent intentMain = new Intent(CreateCardActivity.this ,
                DeckActivity.class);
        CreateCardActivity.this.startActivityForResult(intentMain, 0);
    }
}

package se.tda367.flashcards.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import se.tda367.flashcards.OnSwipeTouchListener;
import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.Card;
import se.tda367.flashcards.models.Deck;

public class PlayDeckActivity extends AppCompatActivity {
    private Boolean showQuestion;
    private Deck currentDeck;
    private Card currentCard;
    private TextView textView;
    private int mode;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_deck);

        mode = Singleton.getInstance().getFlashCards().getMode();

        currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();

        currentDeck.shuffle();
        currentDeck.setCounter(0);

        //selectMode();

        currentCard = currentDeck.getNextCard();

        textView = (TextView) findViewById(R.id.textView);
        textView.setText(currentCard.getQuestion());


        showQuestion = true;
        startTime = System.currentTimeMillis();
        activateSwipe();
        setRadioGraphic();

    }
    public void activateSwipe(){
        final View background = findViewById(R.id.background);

        background.setOnTouchListener(new OnSwipeTouchListener(PlayDeckActivity.this) {
            public void onSwipeRight() {

            }

            public void onSwipeLeft() {
                currentDeck.increaseNbrOfCardsPlayed();
                if (currentDeck.hasNext()) {
                    if (!showQuestion) {
                        currentCard = currentDeck.getNextCard();
                        showQuestion = true;
                        textView.setText(currentCard.getQuestion());
                        setRadioGraphic();
                    }
                }
                else finishedDeck(background);
            }


        });

    }

    public void setAnswerOrQuestion() {
        if (!showQuestion) {
            textView.setText(currentCard.getQuestion());
            showQuestion = true;
        } else {
            textView.setText(currentCard.getAnswer());
            showQuestion = false;

        }
    }
    public void selectMode(){

        if(mode == 0) {

            //Standard mode. Use all the cards.

        } else if(mode == 1){
            Deck tmp = new Deck("tmp");
            for(int i = 0; i < currentDeck.getSize(); i++){
                if(currentDeck.getList().get(i).getDifficulty() != 0){
                    tmp.addCard(currentDeck.getList().get(i));
                }
            }
            currentDeck = tmp;
        } else if(mode == 2){
            Deck tmp = new Deck("tmp");
            int size = currentDeck.getSize();
            int ez = (int)Math.ceil(0.05*size);
            int med = (int)Math.ceil(0.35*size);
            int hard = (int)Math.ceil(0.6*size);

            for(int i = 0; i<size; i++){
                if(currentDeck.getList().get(i).getDifficulty() == 0 && ez > 0){
                    tmp.addCard(currentDeck.getList().get(i));
                    ez = ez - 1;
                }
                if(currentDeck.getList().get(i).getDifficulty() == 1 && med > 0){
                    tmp.addCard(currentDeck.getList().get(i));
                    med = med - 1;
                }
                if(currentDeck.getList().get(i).getDifficulty() == 2 && hard > 0){
                    tmp.addCard(currentDeck.getList().get(i));
                    hard = hard - 1;
                }
            }
            currentDeck = tmp;
        }
    }

    public void finishedDeck(View v) {

        Log.v("PlayDeckActivity", "Finished");
        currentDeck.setNbrOfTimesPlayed(currentDeck.getNbrOfTimesPlayed() + 1);
        currentDeck.setPlayedNow();
        currentDeck.increaseAmountOfTimePlayed((System.currentTimeMillis()-startTime));
        Singleton.getInstance().getDatabaseController(getApplicationContext()).updateDeck(currentDeck);
        //This updates the current deck so we are in phase with the database.
        //TODO: Redo this, implement a safer way of updating the deck. E.g everytime a DB CRUD is happening
        Singleton.getInstance().getFlashCards().setCurrentDeck(currentDeck);

        Intent intentMain = new Intent(PlayDeckActivity.this ,
                DeckActivity.class);
        PlayDeckActivity.this.startActivityForResult(intentMain, 0);

    }

    public void flipCard(View v) {
        if (currentCard != null) {
            setAnswerOrQuestion();
        }   else   {
            finishedDeck(v);
        }
    }
    public void backButton(View v){
        Log.v("PlayDeckActivity", "Back");
        Intent intentPrev = new Intent(PlayDeckActivity.this, DeckActivity.class);
        PlayDeckActivity.this.startActivityForResult(intentPrev, 0);
    }
    public void setRadioGraphic(){
        if(currentCard.getDifficulty() == 0){
            RadioButton tmp = (RadioButton)findViewById(R.id.easyButton);
            tmp.setChecked(true);
        }
        if(currentCard.getDifficulty() == 1){
            RadioButton tmp = (RadioButton)findViewById(R.id.mediumButton);
            tmp.setChecked(true);
        }
        if(currentCard.getDifficulty() == 2){
            RadioButton tmp = (RadioButton)findViewById(R.id.hardButton);
            tmp.setChecked(true);
        }
    }
    public void setDiff(View view){
        boolean isChecked = ((RadioButton)view).isChecked();
        switch(view.getId()) {
            case R.id.easyButton:
                if (isChecked)
                    currentCard.setDifficulty(0);
                    Singleton.getInstance().getDatabaseController(getApplicationContext()).updateCard(currentCard);
                    break;
            case R.id.mediumButton:
                if (isChecked)
                    currentCard.setDifficulty(1);
                    Singleton.getInstance().getDatabaseController(getApplicationContext()).updateCard(currentCard);
                    break;
            case R.id.hardButton:
                if(isChecked)
                    currentCard.setDifficulty(2);
                    Singleton.getInstance().getDatabaseController(getApplicationContext()).updateCard(currentCard);
                    break;
        }
    }

}
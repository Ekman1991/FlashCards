package se.tda367.flashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Random;

public class PlayDeckActivity extends AppCompatActivity {
    private Boolean showQuestion;
    private Deck currentDeck;
    private Card currentCard;
    private TextView textView;
    private int mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_deck);

        mode = Singleton.getInstance().getFlashCards().getMode();

        currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();

        currentDeck.shuffle();
        currentDeck.setCounter(0);

        selectMode();

        currentCard = currentDeck.getNextCard();

        textView = (TextView) findViewById(R.id.textView);
        textView.setText(currentCard.getQuestion());


        showQuestion = true;

        activateSwipe();
        setRadioGraphic();

    }
    public void activateSwipe(){
        final View background = findViewById(R.id.background);

        background.setOnTouchListener(new OnSwipeTouchListener(PlayDeckActivity.this) {
            public void onSwipeRight() {

            }

            public void onSwipeLeft() {
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
        if(mode == 1){
            Deck tmp = new Deck("tmp");
            for(int i = 0; i<currentDeck.getSize(); i++){
                if(currentDeck.getList().get(i).getDifficulty() != 0){
                    tmp.addCard(currentDeck.getList().get(i));
                }
            }
            currentDeck = tmp;
        }
        if(mode == 2){
            random();
        }
    }
    public void random(){
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

    public void finishedDeck(View v) {
        Intent intent = new Intent(this, MainActivity.class);

        setResult(RESULT_OK, intent);

        //TODO: Set these variables. Maybe need to rethink Calender class.
        //currentDeck.setPlayedSince();
        //currentDeck.playedDeck();


        finish();
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
                    break;
            case R.id.mediumButton:
                if (isChecked)
                    currentCard.setDifficulty(1);
                    break;
            case R.id.hardButton:
                if(isChecked)
                    currentCard.setDifficulty(2);
                    break;
        }
    }

}
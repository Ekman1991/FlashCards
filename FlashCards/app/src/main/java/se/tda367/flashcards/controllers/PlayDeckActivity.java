package se.tda367.flashcards.controllers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import se.tda367.flashcards.OnSwipeTouchListener;
import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.Card;
import se.tda367.flashcards.models.Deck;

public class PlayDeckActivity extends AppCompatActivity {
    private Boolean showQuestion;
    private Deck currentDeck;
    private Deck realDeck;
    private Card currentCard;
    private TextView textView;
    private int mode;
    private long startTime;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_deck);

        mode = Singleton.getInstance().getFlashCards().getMode();

        currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();

        currentDeck.shuffle();
        currentDeck.setCounter(0);

        selectMode(Singleton.getInstance().getFlashCards().getAmount());

        currentCard = currentDeck.getNextCard();

        textView = (TextView) findViewById(R.id.textView);
        textView.setText(currentCard.getQuestion());


        showQuestion = true;
        startTime = System.currentTimeMillis();
        activateSwipe();
        setRadioGraphic();
    }
    //enables swipe left inside the playDeck menu
    //wont ever give an exception
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
    //flips the card
    public void setAnswerOrQuestion() {
        if (!showQuestion) {
            textView.setText(currentCard.getQuestion());
            showQuestion = true;
        } else {
            textView.setText(currentCard.getAnswer());
            showQuestion = false;
            //TODO Definitely change this somehow
            if (!currentCard.hasBeenPlayedOnce()) {
                currentDeck.increaseCardDifficultyAmount(2);
                currentCard.setHasBeenPlayedOnce(true);
            }
        }
    }

    public void notification (){
        //start of a notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Time for you to play" + currentDeck)
                .setContentText("It's scheduldge that you learn best if you play this Deck now")
                .setAutoCancel(true)
                .setColor(Color.BLUE);

        //start program when active
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

        PendingIntent content = PendingIntent.getActivity(getApplicationContext(), 1, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(content);

        // Add a notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
        //end of a notificaiton
    }

    public void selectMode(int amount){
        if(amount == 0){
            backButton(findViewById(R.id.background));
        }
        else

        if(mode == 0) {
            realDeck = currentDeck;
            //standard mode, uses all the cards unless the user choses to limit amounts
            Deck tmp = new Deck(currentDeck);

            if(amount < currentDeck.getSize() && amount > 0) {
                currentDeck.shuffle();
                for (int i = 0; i < amount; i++) {
                    tmp.addCard(currentDeck.getList().get(i));
                }
                realDeck = currentDeck;
                currentDeck = tmp;
            }

        } else if(mode == 1){
            //selects only hard and medium rated cards

            Deck tmp = new Deck(currentDeck);
            for(int i = 0; i < currentDeck.getSize(); i++){
                if(currentDeck.getList().get(i).getDifficulty() != 0){
                    tmp.addCard(currentDeck.getList().get(i));
                }
            }
            realDeck = currentDeck;
            currentDeck = tmp;
            if(amount < currentDeck.getSize() && amount > 0) {
                currentDeck.shuffle();
                tmp = new Deck(currentDeck);
                for (int i = 0; i < amount; i++) {
                    tmp.addCard(currentDeck.getList().get(i));
                }
                currentDeck = tmp;
            }
        } else if(mode == 2){
            //selects cards based on difficulty with a percentage algorithm
            Deck tmp = new Deck(currentDeck);
            int size = currentDeck.getSize();
            int ez = (int)Math.ceil(0.05*size);
            int med = (int)Math.ceil(0.35*size);
            int hard = (int)Math.ceil(0.6*size);

            for(int i = 0; i<size; i++){
                if(currentDeck.getList().get(i).getDifficulty() == 0 && ez > 0 && amount > 0){
                    tmp.addCard(currentDeck.getList().get(i));
                    ez = ez - 1;
                    amount = amount - 1;
                }
                if(currentDeck.getList().get(i).getDifficulty() == 1 && med > 0 && amount > 0){
                    tmp.addCard(currentDeck.getList().get(i));
                    med = med - 1;
                    amount = amount - 1;
                }
                if(currentDeck.getList().get(i).getDifficulty() == 2 && hard > 0 && amount > 0){
                    tmp.addCard(currentDeck.getList().get(i));
                    hard = hard - 1;
                    amount = amount - 1;
                }
            }
            realDeck = currentDeck;
            currentDeck = tmp;
        }
    }

    public void finishedDeck(View v) {
        currentDeck = realDeck;

        Log.v("PlayDeckActivity", "Finished");
        currentDeck.setNbrOfTimesPlayed(currentDeck.getNbrOfTimesPlayed() + 1);
        currentDeck.setPlayedNow();
        currentDeck.increaseAmountOfTimePlayed((System.currentTimeMillis()-startTime));
        startTimeToNotify();


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Fun that you played" + currentDeck)
                .setContentText("We will notify you when it's time to play this deck again")
                .setAutoCancel(true)
                .setColor(Color.BLUE);

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

        PendingIntent content = PendingIntent.getActivity(getApplicationContext(), 1, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(content);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());


        Singleton.getInstance().getDatabaseController(getApplicationContext()).updateDeck(currentDeck);
        //This updates the current deck so we are in phase with the database.
        //TODO: Redo this, implement a safer way of updating the deck. E.g everytime a DB CRUD is happening
        Singleton.getInstance().getFlashCards().setCurrentDeck(currentDeck);

        Intent intentMain = new Intent(PlayDeckActivity.this ,
                DeckActivity.class);
        PlayDeckActivity.this.startActivityForResult(intentMain, 0);

    }

    public void startTimeToNotify(){
        int hard = 0;

        for (Card c : currentDeck.getList()) {
            if (c.getDifficulty() == 0) {
                hard = hard + 2;
            } else if (c.getDifficulty() == 1) {
                hard = hard + 1;
            } else {
                hard = hard + 0;
            }
        }
        timer = new Timer();


        if (currentDeck.getList().size() >= 3){
            if (hard >= 2*currentDeck.getList().size()-2) {
                    TimerTask t = new TimerTask() {
                        @Override
                        public void run() {

                        }
                };
                //notification in 1 day
                timer.schedule (t, 0l, 1000*60*60*24);
            }
            else if (hard >= currentDeck.getList().size()-2) {
                TimerTask t = new TimerTask () {
                    @Override
                    public void run () {
                        notification();
                    }
                };
                //notification in 3 days
                timer.schedule (t, 0l, 1000*60*60*24*3);
            }
            else {
                TimerTask t = new TimerTask () {
                    @Override
                    public void run () {
                        notification();
                    }
                };
                //notification in 7 days
                timer.schedule (t, 0l, 1000*60*60*24*7);
            }

        }

        else {
            TimerTask t = new TimerTask () {
                @Override
                public void run () {
                    notification();
                }
            };
            //notification in 5 days
            timer.schedule (t, 0l, 1000*60*60*24*5);
        }



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
            currentDeck.decreaseCardDifficultyAmount(currentCard.getDifficulty());
        switch(view.getId()) {
            case R.id.easyButton:
                if (isChecked)
                    //TODO Rethink this method
                    currentDeck.increaseCardDifficultyAmount(0);
                    currentCard.setDifficulty(0);
                    Singleton.getInstance().getDatabaseController(getApplicationContext()).updateCard(currentCard);
                    break;
            case R.id.mediumButton:
                if (isChecked)
                    currentDeck.increaseCardDifficultyAmount(1);
                    currentCard.setDifficulty(1);
                    Singleton.getInstance().getDatabaseController(getApplicationContext()).updateCard(currentCard);
                    break;
            case R.id.hardButton:
                if(isChecked)
                    currentDeck.increaseCardDifficultyAmount(2);
                    currentCard.setDifficulty(2);
                    Singleton.getInstance().getDatabaseController(getApplicationContext()).updateCard(currentCard);
                    break;
        }
    }

}
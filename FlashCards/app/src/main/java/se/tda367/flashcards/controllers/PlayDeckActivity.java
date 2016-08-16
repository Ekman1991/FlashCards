package se.tda367.flashcards.controllers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import se.tda367.flashcards.OnSwipeTouchListener;
import se.tda367.flashcards.R;
import se.tda367.flashcards.ServiceLocator;
import se.tda367.flashcards.models.Card;
import se.tda367.flashcards.models.Deck;

public class PlayDeckActivity extends AppCompatActivity {
    private Boolean showQuestion;
    private Deck currentDeck;
    private Deck realDeck;
    private Card currentCard;
    private EditText textView;
    private EditText editText;
    private int mode;
    private long startTime;
    private Timer timer;
    private boolean editMode = false;
    private Button delButton;
    private ImageView imageView;
    private MediaPlayer mediaPlayer;
    private Button play;
    private byte[] audio;
    CardEditor cardE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_deck);

        mediaPlayer = new MediaPlayer();

        play = (Button) findViewById(R.id.play);

        mode = ServiceLocator.getInstance().getFlashCards().getMode();

        currentDeck = ServiceLocator.getInstance().getFlashCards().getCurrentDeck();

        ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).updateDeck(currentDeck);
        currentDeck.setCardsArray(ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).getCardsForDeck(currentDeck));

        currentDeck.shuffle();
        currentDeck.setCounter(0);

        selectMode(ServiceLocator.getInstance().getFlashCards().getAmount());

        currentCard = currentDeck.getNextCard();

        Log.v("CURRENT CARD = " , currentCard + "SKRIV UT DETTA");

        textView = (EditText) findViewById(R.id.textView);
        textView.setText(currentCard.getQuestion());
        textView.setFocusable(false);

        editText = (EditText)findViewById(R.id.editText);
        editText.setText(currentCard.getAnswer());
        editText.setFocusable(false);
        editText.setVisibility(View.GONE);


        audio = currentCard.getAudioByte();
        if (audio != null){
            play.setEnabled(true);
        }
        else {
            play.setEnabled(false);
        }

        imageView = (ImageView) findViewById(R.id.imageView);
        Log.v("THE IMAGE = ", "" + imageView);

        if (currentCard.getImageByte() == null)
        {
            imageView.setImageResource(android.R.color.transparent);
        }
        else {
            Bitmap bmp;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            bmp = BitmapFactory.decodeByteArray(currentCard.getImageByte(), 0, currentCard.getImageByte().length, options);
            Log.v("THE IMAGE = ", "" + bmp);
            imageView.setImageBitmap(bmp);
        }



        textChanged();
        secondTextChanged();

        delButton = (Button)findViewById(R.id.delButton);
        delButton.setVisibility(View.GONE);


        showQuestion = true;
        startTime = System.currentTimeMillis();
        activateSwipe();
        setRadioGraphic();

        cardE = new CardEditor();
        ServiceLocator.getInstance().getFlashCards().setEditMode(false);
    }

    public void play(View v){


        audio = currentCard.getAudioByte();
        String outputFile=Environment.getExternalStorageDirectory().getAbsolutePath() + "/output.3gp";
        File path = new File(outputFile);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(path);
            fos.write(audio);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MediaPlayer mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(outputFile);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //enables swipe left inside the playDeck menu
    //wont ever give an exception
    public void activateSwipe(){
        final View background = findViewById(R.id.background);

        background.setOnTouchListener(new OnSwipeTouchListener(PlayDeckActivity.this) {

            public void onSwipeLeft() {
                activateLeft(background);
            }


        });

    }
    public void activateLeft(View background){
        currentDeck.increaseNbrOfCardsPlayed();
        if (currentDeck.hasNext()) {
            if (!showQuestion) {
                currentCard = currentDeck.getNextCard();
                showQuestion = true;
                textView.setText(currentCard.getQuestion());
                textView.setVisibility(View.VISIBLE);
                editText.setText(currentCard.getAnswer());

                currentCard.getAudioByte();

                activateAudio();

                activateImage();

                editText.setVisibility(View.GONE);
                setRadioGraphic();
            }
        }
        else finishedDeck(background);
    }
    public void activateAudio(){
        if (audio != null){
            play.setEnabled(true);
        }
        else {
            play.setEnabled(false);
        }
    }
    public void activateImage(){
        if (currentCard.getImageByte() == null)
        {
            imageView.setImageResource(android.R.color.transparent);
        }
        else {
            Bitmap bmp;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            bmp = BitmapFactory.decodeByteArray(currentCard.getImageByte(), 0, currentCard.getImageByte().length, options);
            imageView.setImageBitmap(bmp);
            imageView.setVisibility(View.VISIBLE);

        }
    }
    //flips the card
    public void setAnswerOrQuestion() {
        if (!showQuestion) {
            textView.setText(currentCard.getQuestion());
            textView.setVisibility(View.VISIBLE);
            editText.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            showQuestion = true;
        } else {
            editText.setText(currentCard.getAnswer());
            editText.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);

            showQuestion = false;
            //TODO Definitely change this somehow
            if (!currentCard.hasBeenPlayedOnce()) {
                currentDeck.increaseCardDifficultyAmount(2);
                currentCard.setHasBeenPlayedOnce(true);
            }
        }
    }

    public void selectMode(int amount){
        if(amount == 0){
            backButton(findViewById(R.id.background));
        }
        else

        if(mode == 0) {
            activateMode0(amount);

        } else if(mode == 1){
            activateMode1(amount);
        } else if(mode == 2){
           activateMode2(amount);
        }
    }
    public void activateMode0(int amount){
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
    }
    public void activateMode1(int amount){
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
    }
    public void activateMode2(int amount){
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

    public void finishedDeck(View v) {
        currentDeck = realDeck;

        Log.v("PlayDeckActivity", "Finished");
        currentDeck.setNbrOfTimesPlayed(currentDeck.getNbrOfTimesPlayed() + 1);
        currentDeck.setPlayedNow();
        currentDeck.increaseAmountOfTimePlayed((System.currentTimeMillis()-startTime));



        ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).updateDeck(currentDeck);
        //This updates the current deck so we are in phase with the database.
        //TODO: Redo this, implement a safer way of updating the deck. E.g everytime a DB CRUD is happening
        currentDeck.setCardsArray(ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).getCardsForDeck(currentDeck));

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
        currentDeck = realDeck;
        ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).updateDeck(currentDeck);
        currentDeck.setCardsArray(ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).getCardsForDeck(currentDeck));
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
                    ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).updateCard(currentCard);
                    break;
            case R.id.mediumButton:
                if (isChecked)
                    currentDeck.increaseCardDifficultyAmount(1);
                    currentCard.setDifficulty(1);
                    ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).updateCard(currentCard);
                    break;
            case R.id.hardButton:
                if(isChecked)
                    currentDeck.increaseCardDifficultyAmount(2);
                    currentCard.setDifficulty(2);
                    ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).updateCard(currentCard);
                    break;
        }
    }

    public void textChanged(){
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(showQuestion) {
                    currentCard.setQuestion(textView.getText().toString());
                }
                ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).updateCard(currentCard);

            }
        });

    }
    public void secondTextChanged(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!showQuestion) {
                    currentCard.setAnswer(editText.getText().toString());
                }
                ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).updateCard(currentCard);

            }
        });
    }
    public void removeCard(View v){
        ServiceLocator.getInstance().getDatabaseService(getApplicationContext()).deleteCard(currentCard.getId());
        if (currentDeck.hasNext()) {
            currentCard = currentDeck.getNextCard();
            showQuestion = true;
            textView.setText(currentCard.getQuestion());
            textView.setVisibility(View.VISIBLE);
            editText.setText(currentCard.getAnswer());
            editText.setVisibility(View.GONE);
            currentCard.getAudioByte();
            if (audio != null){
                play.setEnabled(true);
            }
            else {
                play.setEnabled(false);
            }
            if (currentCard.getImageByte() == null)
            {
                imageView.setImageResource(android.R.color.transparent);
            }
            else {
                Bitmap bmp;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                bmp = BitmapFactory.decodeByteArray(currentCard.getImageByte(), 0, currentCard.getImageByte().length, options);
                imageView.setImageBitmap(bmp);
            }
            setRadioGraphic();



        }
        else finishedDeck(v);
    }
    public void editCard(View v){
        if(!ServiceLocator.getInstance().getFlashCards().getEditMode()){
            cardE.firstEdit(v);
        }
        else {
            cardE.secondEdit(v);
        }

    }

}
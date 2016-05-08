package se.tda367.flashcards.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.Deck;

public class DeckActivity extends AppCompatActivity {

    private Deck currentDeck;
    private TextView name;
    private TextView numberOfCards;
    private int mode;
    private TextView playedSince;
    private TextView made;
    private TextView timesPlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        Log.v("DeckActivity", "On Create");

        RadioButton regular = (RadioButton)findViewById(R.id.firstButton);
        regular.setChecked(true);

        currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();

        mode = Singleton.getInstance().getFlashCards().getMode();

        name = (TextView) findViewById(R.id.deckName);
        numberOfCards = (TextView) findViewById(R.id.numberOfCards);

        playedSince = (TextView) findViewById(R.id.playedSince);
        timesPlayed= (TextView) findViewById(R.id.timesPlayed);
        made = (TextView) findViewById(R.id.made);



        numberOfCards.setText(Integer.toString(currentDeck.getSize()));
        name.setText(currentDeck.getName());

        if(currentDeck.getPlayedSince() == -1) {
            playedSince.setText("PS: Not played yet");
        } else {
            //TODO: Convert unix time to readable date
            playedSince.setText("PS:" + currentDeck.getPlayedSince());
        }

        //TODO: Convert unix time to readable date
        made.setText("Made:" + currentDeck.getMade());

        timesPlayed.setText("TP:" + currentDeck.getNbrOfTimesPlayed());

        setAmount(0);

    }

    public void createCard(View v) {
        Intent intentMain = new Intent(DeckActivity.this ,
                CreateCardActivity.class);
        DeckActivity.this.startActivityForResult(intentMain, 0);
    }

    public void startDeck(View v) {
        Log.v("DeckActivity", "StartDeck");

        if (currentDeck.getSize() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(DeckActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("You do not have any cards");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else {
            Intent intentMain = new Intent(DeckActivity.this ,
                    PlayDeckActivity.class);
            DeckActivity.this.startActivityForResult(intentMain, 0);
        }

    }

    public void backButton(View v) {
        Log.v("DeckActivity", "Back");

        Intent intentMain = new Intent(DeckActivity.this ,
                MainActivity.class);
        DeckActivity.this.startActivityForResult(intentMain, 0);

    }
    public void setMode(View v){
        boolean isChecked = ((RadioButton)v).isChecked();
        switch(v.getId()) {
            case R.id.firstButton:
                if (isChecked)
                    Singleton.getInstance().getFlashCards().setMode(0);
                setAmount(0);
                break;
            case R.id.secondButton:
                if (isChecked)
                    Singleton.getInstance().getFlashCards().setMode(1);
                setAmount(1);
                break;
            case R.id.thirdButton:
                if(isChecked)
                    Singleton.getInstance().getFlashCards().setMode(2);
                setAmount(2);
                break;
        }
    }
    public void delDeck(View v){
        backButton(v);
        Singleton.getInstance().getDatabaseController(getApplicationContext()).deleteDeck(currentDeck.getId());
    }
    public void setAmount(int i){
        EditText editAmount = (EditText)findViewById(R.id.editAmount);
        if(i == 0){
            editAmount.setText(currentDeck.getSize()+"");
        }
        if(i == 1){
            int tmp = 0;
            for(int j = 0; j<currentDeck.getSize(); j++){
                if(currentDeck.getList().get(j).getDifficulty()>0){
                    tmp = tmp + 1;
                }
            }
            editAmount.setText(tmp+"");
        }
        if(i == 2){
            int tmp = 0;
            int size = currentDeck.getSize();
            int ez =(int)Math.ceil(0.05*size);
            int med = (int)Math.ceil(0.35*size);
            int hard = (int)Math.ceil(0.6*size);

            for(int j = 0; j<size; j++){
                if(currentDeck.getList().get(j).getDifficulty() == 0 && ez > 0){
                    tmp = tmp + 1;
                    ez = ez - 1;
                }
                if(currentDeck.getList().get(j).getDifficulty() == 1 && med > 0){
                    tmp = tmp + 1;
                    med = med - 1;
                }
                if(currentDeck.getList().get(j).getDifficulty() == 2 && hard > 0){
                    tmp = tmp + 1;
                    hard = hard - 1;
                }
            }
            editAmount.setText(tmp+"");
        }
    }

}

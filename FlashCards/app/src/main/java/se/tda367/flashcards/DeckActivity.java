package se.tda367.flashcards;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class DeckActivity extends AppCompatActivity {
    private Deck currentDeck;
    private TextView name;
    private TextView numberOfCards;
    private int mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);
        RadioButton regular = (RadioButton)findViewById(R.id.firstButton);
        regular.setChecked(true);

        currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();
        mode = Singleton.getInstance().getFlashCards().getMode();
        name = (TextView) findViewById(R.id.deckName);
        numberOfCards = (TextView) findViewById(R.id.numberOfCards);

        name.setText(currentDeck.getName());
        numberOfCards.setText(Integer.toString(currentDeck.getSize()));
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
                break;
            case R.id.secondButton:
                if (isChecked)
                    Singleton.getInstance().getFlashCards().setMode(1);
                break;
            case R.id.thirdButton:
                if(isChecked)
                    Singleton.getInstance().getFlashCards().setMode(2);
                break;
        }
    }

}

package se.tda367.flashcards;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DeckActivity extends AppCompatActivity {
    private Deck currentDeck;
    private TextView name;
    private TextView numberOfCards;
    private TextView playedSince;
    private TextView made;
    private TextView timesPlayed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();

        name = (TextView) findViewById(R.id.deckName);
        numberOfCards = (TextView) findViewById(R.id.numberOfCards);

        playedSince = (TextView) findViewById(R.id.playedSince);
        timesPlayed= (TextView) findViewById(R.id.timesPlayed);
        made = (TextView) findViewById(R.id.made);



        numberOfCards.setText(Integer.toString(currentDeck.getSize()));
        name.setText(currentDeck.getName());

        //playedSince.setText(Singleton.getInstance().getFlashCards().getCurrentDeck().getPlayedSince().sincePlayed());
        //made.setText(Singleton.getInstance().getFlashCards().getCurrentDeck().getMade().sinceMade());
        //timesPlayed.setText(Singleton.getInstance().getFlashCards().getCurrentDeck().getNbrOfTimesPlayed());


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

}

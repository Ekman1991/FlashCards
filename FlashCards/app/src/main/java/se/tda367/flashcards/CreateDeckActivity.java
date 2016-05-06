package se.tda367.flashcards;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class CreateDeckActivity extends AppCompatActivity {

    private TextView deckName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createdeck);
        deckName = (TextView) findViewById(R.id.createDeckTextField);
    }

    public void saveTheDeck (View v) {

        //TODO: Move this to a utility class. Will be duplicated all over the codebase
        if (deckName == null || deckName.getText().toString().trim().length() == 0) {
            Log.d("CreateDeck", "DeckName is empty");
        } else {
            Deck deck = new Deck(deckName.getText().toString());
            Singleton.getInstance().getDatabaseController(getApplicationContext()).createDeck(deck);

            deck.setStartDay();


            Intent intentMain = new Intent(CreateDeckActivity.this ,
                    MainActivity.class);
            CreateDeckActivity.this.startActivityForResult(intentMain, 0);

        }

    }

    public void cancelSaveDeck(View v) {
        Log.d("YourTag", "YourOutput");
        Intent intentMain = new Intent(CreateDeckActivity.this ,
                MainActivity.class);
        CreateDeckActivity.this.startActivityForResult(intentMain, 0);
    }

    //This will add an action so that when u press outside of the keyboard or the text field
    //the keyboard will disappear
    //TODO: Refactor this if we should use it on all input
    /*public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(CreateDeckActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }*/

}

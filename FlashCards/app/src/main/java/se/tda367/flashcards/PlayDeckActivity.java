package se.tda367.flashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.util.Log;

public class PlayDeckActivity extends AppCompatActivity {
    private Boolean showQuestion;
    private Deck currentDeck;
    private TextView textView;
    private int deckSize;
    int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_deck);
        Intent intent = getIntent();
        currentDeck = (Deck)intent.getParcelableExtra("D");
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(currentDeck.play(0, position));
        showQuestion = true;
        deckSize = currentDeck.getSize();
        Log.d("decksize", Integer.toString(deckSize));
    }

    public void setAnswerOrQuestion() {
        if (!showQuestion) {
            textView.setText(currentDeck.play(0, position));
            showQuestion = true;
        } else {
            textView.setText(currentDeck.play(1, position));
            showQuestion = false;
            Log.d("Position", Integer.toString(position));
            position++;
        }
    }

    public void finishedDeck(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void flipCard(View v) {
        if (deckSize > position) {
            setAnswerOrQuestion();
        }   else   {
            finishedDeck(v);
        }
    }
}
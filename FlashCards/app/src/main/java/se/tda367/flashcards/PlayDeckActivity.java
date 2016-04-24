package se.tda367.flashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PlayDeckActivity extends AppCompatActivity {
    private Boolean showQuestion;
    private Deck currentDeck;
    private Card currentCard;
    private TextView textView;
    private int deckSize;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_deck);

        currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();
        currentDeck.shuffle();

        currentCard = currentDeck.getNextCard();

        textView = (TextView) findViewById(R.id.textView);
        textView.setText(currentCard.getQuestion());


        showQuestion = true;
        deckSize = currentDeck.getSize();
    }

    public void setAnswerOrQuestion() {
        if (!showQuestion) {
            textView.setText(currentCard.getQuestion());
            showQuestion = true;
        } else {
            textView.setText(currentCard.getAnswer());
            showQuestion = false;
            currentCard = currentDeck.getNextCard();
        }
    }

    public void finishedDeck(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void flipCard(View v) {
        if (currentCard != null) {
            setAnswerOrQuestion();
        }   else   {
            finishedDeck(v);
        }
    }
}
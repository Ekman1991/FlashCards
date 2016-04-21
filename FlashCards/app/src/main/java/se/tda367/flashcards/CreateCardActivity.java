package se.tda367.flashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateCardActivity extends AppCompatActivity {
    EditText question;
    EditText answer;
    CardFactory cardFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        question = (EditText)findViewById(R.id.questionField);
        answer = (EditText)findViewById(R.id.answerField);
        cardFactory = new CardFactory();
    }

    public void addCardToDeck(View v) {
        Intent intent = getIntent();
        Deck deck = intent.getParcelableExtra("D");
        String q = question.getText().toString();
        String a = answer.getText().toString();
        deck.addCard(cardFactory.createCard(q,a));
        intent = new Intent(this, DeckActivity.class);
        intent.putExtra("D", deck);
        setResult(RESULT_OK, intent);
        finish();
    }
}

package se.tda367.flashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateDeckActivity extends AppCompatActivity {

    //createDeckCancelButton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createdeck);
    }



    public void cancelSaveDeck(View v) {

        Intent intentMain = new Intent(CreateDeckActivity.this ,
                MainActivity.class);
        CreateDeckActivity.this.startActivity(intentMain);

    }

    //Saves deck into the array in MainActivity
    public void saveDeck (View v) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText)findViewById(R.id.createDeckTextField);
        String message = editText.getText().toString();
        intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
        setResult(RESULT_OK, intent);
        finish();
    }
}

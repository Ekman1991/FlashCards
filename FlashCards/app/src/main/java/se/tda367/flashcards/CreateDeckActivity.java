package se.tda367.flashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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


}

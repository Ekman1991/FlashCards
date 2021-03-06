package se.tda367.flashcards.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import se.tda367.flashcards.OnSwipeTouchListener;
import se.tda367.flashcards.R;

public class HelpActivity extends AppCompatActivity {

    private TextView helpView1;
    private TextView helpView2;
    private TextView helpView3;
    private Button buttonNext;
    private Button buttonBack;
    private TextView textHelp;
    private OnSwipeTouchListener left, right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        helpView1 = (TextView) findViewById(R.id.helpView1);
        helpView2 = (TextView) findViewById(R.id.helpView2);
        helpView3 = (TextView) findViewById(R.id.helpView3);
        buttonNext = (Button) findViewById(R.id.next);
        buttonBack = (Button) findViewById(R.id.back);
        textHelp = (TextView) findViewById(R.id.textHelp);

        buttonBack.setEnabled(false);
        buttonNext.setEnabled(true);
        helpView1.setVisibility(View.VISIBLE);
        helpView2.setVisibility(View.GONE);
        helpView3.setVisibility(View.GONE);
        textHelp.setVisibility(View.VISIBLE);

        assignLeft();



    }
    public void assignLeft(){
        final View background = findViewById(R.id.background);
        background.setOnTouchListener(new OnSwipeTouchListener(HelpActivity.this) {
            public void onSwipeLeft() {
                activateLeft();
            }

        });

    }


    public void nextClick(View v){
        if (helpView1.getVisibility() == View.VISIBLE){
            helpView1.setVisibility(View.GONE);
            helpView2.setVisibility(View.VISIBLE);
            helpView3.setVisibility(View.GONE);
            buttonBack.setEnabled(true);
            textHelp.setVisibility(View.GONE);
        }
        else if (helpView2.getVisibility() == View.VISIBLE){
            helpView1.setVisibility(View.GONE);
            helpView2.setVisibility(View.GONE);
            helpView3.setVisibility(View.VISIBLE);
            buttonNext.setEnabled(false);
        }

    }

    public void backClick(View v){
        if (helpView3.getVisibility() == View.VISIBLE){
            helpView1.setVisibility(View.GONE);
            helpView2.setVisibility(View.VISIBLE);
            helpView3.setVisibility(View.GONE);
            buttonNext.setEnabled(true);
        }
        else if (helpView2.getVisibility() == View.VISIBLE){
            helpView1.setVisibility(View.VISIBLE);
            helpView2.setVisibility(View.GONE);
            helpView3.setVisibility(View.GONE);
            buttonBack.setEnabled(false);
            textHelp.setVisibility(View.VISIBLE);
        }

    }

    public void menyClick(View v){
        Intent mainIntent = new Intent(HelpActivity.this, MainActivity.class);
        HelpActivity.this.startActivityForResult(mainIntent, 0);
    }


    public void activateLeft(){
        if (helpView1.getVisibility() == View.VISIBLE){
            helpView1.setVisibility(View.GONE);
            helpView2.setVisibility(View.VISIBLE);
            helpView3.setVisibility(View.GONE);
            buttonBack.setEnabled(true);
            textHelp.setVisibility(View.GONE);
        }
        else if (helpView2.getVisibility() == View.VISIBLE){
            helpView1.setVisibility(View.GONE);
            helpView2.setVisibility(View.GONE);
            helpView3.setVisibility(View.VISIBLE);
            buttonNext.setEnabled(false);
        }
    }

}

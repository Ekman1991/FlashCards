package se.tda367.flashcards.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import se.tda367.flashcards.models.BarChart;
import se.tda367.flashcards.models.PieChart;
import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.StatisticsItem;
import se.tda367.flashcards.models.Deck;

public class StatisticsActivity extends AppCompatActivity {

    private Deck currentDeck;
    private TextView timePlayed;
    private PieChart pieChart;
    private BarChart barChart;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        DecimalFormat df = new DecimalFormat("#.##");
        currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();
        this.pieChart = (PieChart) findViewById(R.id.pieChart);
        fillPieChart();
        this.barChart = (BarChart) findViewById(R.id.barChart);
        fillBarChart();
        timePlayed = (TextView) findViewById(R.id.averageTime);
        timePlayed.setText(df.format(currentDeck.getAmountOfTimePlayed() / currentDeck.getNbrOfCardsPlayedTotal() / 1000) + " seconds");
    }

    private void fillPieChart() {
        for (int i = 0; i < 3; i++) {
            pieChart.addItem(new StatisticsItem((setPieChartColors(i)), currentDeck.getAmountOfCardsWithDifficulty(i)));
        }
    }

    //Sets different colors to the difficulty slices, red for hard,  yellow for normal and blue for easy
    private int setPieChartColors(int i) {
        //Initializes the variable, could be moved to onCreate
        int color = 1;
        switch (i) {
            case 0:
                color = 0xFF3366FF;
                break;
            case 1:
                color = 0xFFFFFF00;
                break;
            case 2:
                color = 0xFFFF0000;
                break;
            default:
                break;
        }
        return color;
    }

    private void fillBarChart() {
        ArrayList<Integer> arrayList= currentDeck.getCardsPlayedPerDay();
        for (int i = 0; i < arrayList.size(); i++) {
            barChart.addItem(new StatisticsItem((0xFFFF0000), arrayList.get(i)));
        }
    }

    //Returns to the main activity
    public void backButton(View v) {
        Intent intentMain = new Intent(StatisticsActivity.this ,
                MainActivity.class);
        StatisticsActivity.this.startActivity(intentMain);
    }
}
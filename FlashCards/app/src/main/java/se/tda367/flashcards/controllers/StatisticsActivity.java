package se.tda367.flashcards.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;

import se.tda367.flashcards.BarChart;
import se.tda367.flashcards.PieChart;
import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.StatisticsItem;
import se.tda367.flashcards.models.Deck;

public class StatisticsActivity extends AppCompatActivity {

    private Deck currentDeck;
    private TextView timePlayed;
    private PieChart pieChart;
    private BarChart barChart;

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
        timePlayed.setText(df.format(currentDeck.getAmountOfTimePlayed() / currentDeck.getNbrOfCardsPlayed() / 1000) + " seconds");
    }

    private void fillPieChart() {
        for (int i = 0; i < 3; i++) {
            pieChart.addItem(new StatisticsItem((setPieChartColors(i)), currentDeck.getAmountOfCardsWithDifficulty(i)));
        }
    }

    private int setPieChartColors(int i) {
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

    }
}
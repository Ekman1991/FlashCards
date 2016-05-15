package se.tda367.flashcards.controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

import se.tda367.flashcards.CardFactory;
import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.Card;
import se.tda367.flashcards.models.Deck;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "se.tda367.flashcards.MESSAGE";
    private final int OPEN_CREATE_DECK = 1;

    private ListView lv;
    protected ArrayList<Deck> your_array_list;
    CardFactory factory = new CardFactory();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        your_array_list = null;
        your_array_list = Singleton.getInstance().getFlashCards().getArrayOfDecks(getApplicationContext());
        checkIfAllDecksPlayed();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.listView);

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<Deck> arrayAdapter = new ArrayAdapter<Deck>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                Deck selectedFromList = (Deck)(lv.getItemAtPosition(myItemInt));
                Singleton.getInstance().getFlashCards().setCurrentDeck(selectedFromList);
                Intent intent = new Intent(MainActivity.this, DeckActivity.class);
                startActivityForResult(intent,2);
            }
        });
    }

    public void checkIfAllDecksPlayed(){
        for (Deck d : your_array_list) {
            if (d.getNbrOfTimesPlayed() == 0) {

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Time to play deck " + d)
                        .setContentText("You haven't played deck " + d + " yet")
                        .setAutoCancel(true)
                        .setColor(Color.BLUE);

                Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent content = PendingIntent.getActivity(getApplicationContext(), 1, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                notificationBuilder.setContentIntent(content);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, notificationBuilder.build());

            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_statistics):
                Intent intent = new Intent(this, StatisticsActivity.class);
                this.startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void buttonOnClick(View v) {
        Log.d("YourTag", "YourOutput");
        Intent intentMain = new Intent(MainActivity.this ,
                CreateDeckActivity.class);
        MainActivity.this.startActivityForResult(intentMain, OPEN_CREATE_DECK);
    }

}

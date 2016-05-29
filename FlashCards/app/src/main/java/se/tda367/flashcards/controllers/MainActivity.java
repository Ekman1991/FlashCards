package se.tda367.flashcards.controllers;

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
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.Deck;

public class MainActivity extends AppCompatActivity implements Comparator<Deck> {
    public final static String EXTRA_MESSAGE = "se.tda367.flashcards.MESSAGE";
    private final int OPEN_CREATE_DECK = 1;
    private String[] Spinner;
    private Spinner spinner;


    private ListView lv;
    private MenuItem statistics;

    private ArrayList<Deck> your_array_list;


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

        this.Spinner = new String[] {
                "Oldest",
                "Leasts numbers of cards",
                "Most numbers of cards",
                "Name",
                "Newest",
                "Most often played",
                "Least often played",
                "Longest time played since",
                "Shortest time played since"
        };
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, this.Spinner);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String itemSelected = parentView.getItemAtPosition(position).toString();
                if (your_array_list.size() != 0) {
                    ArrayList<Deck> your_new_array_list = new ArrayList<Deck>();

                    if (itemSelected.equals("Leasts numbers of cards")) {
                        for (Deck d : your_array_list) {
                            for (int i = 0; i < your_array_list.size(); i++) {
                                if (d.getSize() < your_array_list.get(i).getSize())
                                    your_new_array_list.add(i, d);

                            }
                        }
                        your_array_list = your_new_array_list;
                        ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();


                    }
                    else if (itemSelected.equals("Most numbers of cards")) {
                        for (int i = 0; i < your_array_list.size(); i++) {
                            for (Deck d : your_array_list) {
                                if (d.getSize() > your_array_list.get(i).getSize())
                                    your_new_array_list.add(i, d);
                            }
                        }
                        your_array_list = your_new_array_list;
                        ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();

                    }

                    else if (itemSelected.equals("Most often played")){
                        for (int i = 0; i < your_array_list.size(); i++) {
                            for (Deck d : your_array_list) {
                                if (d.getNbrOfTimesPlayed() < your_array_list.get(i).getNbrOfTimesPlayed())
                                    your_new_array_list.add(i, d);
                            }
                        }
                        your_array_list = your_new_array_list;
                        ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();


                    }


                    else if (itemSelected.equals("Name")){
                        Collections.sort(your_array_list, new Comparator<Deck>() {
                            public int compare(Deck deck, Deck deck2) {
                                return deck.getName().compareTo(deck2.getName());
                            }
                        });
                        ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();


                    }

                    else if (itemSelected.equals("Newest")){
                        for (int i = 0; i < your_array_list.size(); i++) {
                            for (Deck d : your_array_list) {
                                if (d.getMade() > your_array_list.get(i).getMade())
                                    your_new_array_list.add(i, d);
                            }
                        }
                        your_array_list = your_new_array_list;
                        ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();


                    }


                    else if (itemSelected.equals("Least often played")){
                        for (int i = 0; i < your_array_list.size(); i++) {
                            for (Deck d : your_array_list) {
                                if (d.getNbrOfTimesPlayed() > your_array_list.get(i).getNbrOfTimesPlayed())
                                    your_new_array_list.add(i, d);
                            }
                        }
                        your_array_list = your_new_array_list;
                        ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();


                    }


                    else if (itemSelected.equals("Longest time played since")){
                        if (your_array_list.size() != 0) {
                            for (int i = 0; i < your_array_list.size(); i++) {
                                for (Deck d : your_array_list) {
                                    if (d.getPlayedSince() < your_array_list.get(i).getPlayedSince())
                                        your_new_array_list.add(i, d);
                                }
                            }
                            your_array_list = your_new_array_list;
                            ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();
                        }

                    }



                    else if (itemSelected.equals("Shortest time played since")){
                        for (int i = 0; i < your_array_list.size(); i++) {
                            for (Deck d : your_array_list) {
                                if (d.getPlayedSince() > your_array_list.get(i).getPlayedSince())
                                    your_new_array_list.add(i, d);
                            }
                        }
                        your_array_list = your_new_array_list;
                        ((ArrayAdapter) lv.getAdapter()).notifyDataSetChanged();


                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
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
        statistics = menu.findItem(R.id.action_statistics);
        if (Singleton.getInstance().getFlashCards().hasCurrentDeck() == false) {
            statistics.setVisible(false);
        }   else {
            statistics.setVisible(true);
        }
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
            case (R.id.action_help):
                Intent intentHelp = new Intent(this, HelpActivity.class);
                this.startActivity(intentHelp);
                return true;
            case (R.id.action_statistics):
                Intent intent = new Intent(this, StatisticsActivity.class);
                this.startActivity(intent);
                return true;
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

    @Override
    public int compare(Deck deck, Deck t1) {
        return deck.getName().compareTo(t1.getName());
    }
}

package se.tda367.flashcards;

import android.content.Intent;
import android.os.Bundle;
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

        if (savedInstanceState == null) {
            Deck deck = new Deck("Swift");
            your_array_list = new ArrayList<Deck>();
            your_array_list.add(deck);
        }  else {
            your_array_list = savedInstanceState.getParcelableArrayList("Deck");
        }

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
                Intent intent = new Intent(MainActivity.this, DeckActivity.class);
                intent.putExtra("D",selectedFromList);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected void onResume() {
        //Updates the ArrayAdapter upon resume of MainActivity.
        super.onResume();
        ArrayAdapter<Deck> arrayAdapter = new ArrayAdapter<Deck>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );
        lv.setAdapter(arrayAdapter);
        int size = your_array_list.size();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList("Deck", your_array_list);
        super.onSaveInstanceState(savedInstanceState);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void buttonOnClick(View v) {
        Log.d("YourTag", "YourOutput");
        Intent intentMain = new Intent(MainActivity.this ,
                CreateDeckActivity.class);
        MainActivity.this.startActivityForResult(intentMain, OPEN_CREATE_DECK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Returns input text from CreateDeckActivity and adds it into the deck arraylist
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String deck = data.getStringExtra(EXTRA_MESSAGE);
            your_array_list.add(new Deck(deck));
        } else if(requestCode == 2 && resultCode == RESULT_OK && data != null) {

        }
    }
}

package se.tda367.flashcards;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import java.util.ArrayList;

import se.tda367.flashcards.models.Deck;

/**
 * Created by ZlatanH on 2016-05-29.
 */
public class DatabaseAlarmService extends IntentService {

    private Deck currentDeck;
    public DatabaseAlarmService() {
        super("SchedulingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v("Does this", "work?");
        ArrayList<Deck> decks = Singleton.getInstance().getFlashCards().getArrayOfDecks(getApplicationContext());
        for(int i = 0; i < decks.size(); i++) {;
            currentDeck = decks.get(i);
            currentDeck.addCardsPlayedPerDay(currentDeck.getNbrOfCardsPlayedToday());
            currentDeck.setNbrOfCardsPlayedToday(0);
            Singleton.getInstance().getDatabaseController(this).updateDeck(currentDeck);
        }
        DatabaseAlarm.completeWakefulIntent(intent);

    }
}

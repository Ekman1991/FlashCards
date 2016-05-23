package se.tda367.flashcards;

import android.content.Context;

import se.tda367.flashcards.models.FlashCards;
import se.tda367.flashcards.services.DatabaseService;

/**
 * Created by Razzan on 2016-04-21.
 */
public class Singleton {

    //TODO: Inte trådsäker
    //TODO: Rename to ServiceLocator

    private static Singleton singleton = null;
    private final FlashCards flashCards;
    //TODO: Make this final and get rid of context passing
    private DatabaseService dbController;

    protected Singleton() {
        flashCards = new FlashCards();
    }

    public static Singleton getInstance() {
        if(singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    public FlashCards getFlashCards() {
        return this.flashCards;
    }

    //TODO: Rename to getDatabaseService
    public DatabaseService getDatabaseController(Context context) {
        if (dbController == null) {
            dbController = new DatabaseService(context);
            return dbController;
        } else {
            return dbController;
        }
    }

}

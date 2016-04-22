package se.tda367.flashcards;

import android.content.Context;

/**
 * Created by Razzan on 2016-04-21.
 */
public class Singleton {

    private static Singleton singleton = null;
    private FlashCards flashCards;
    private DatabaseController dbController;

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

    public DatabaseController getDatabaseController(Context context) {
        if (dbController == null) {
            dbController = new DatabaseController(context);
            return dbController;
        } else {
            return dbController;
        }
    }

}

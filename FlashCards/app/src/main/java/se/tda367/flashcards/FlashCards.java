package se.tda367.flashcards;

import android.app.Application;
import android.content.Context;

/**
 * Created by ekman on 21/04/16.
 */
public class FlashCards {

    private Deck currentDeck;

    public FlashCards() {

    }

    public Deck getCurrentDeck() {

        //Update the current deck
        /*int deck_id = currentDeck.getId();
        Deck newDeck = Singleton.getInstance().getDatabaseController(context).getDeck(deck_id);
        this.currentDeck = null;
        this.currentDeck = newDeck;*/

        return this.currentDeck;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

}

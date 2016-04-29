package se.tda367.flashcards;

import android.app.Application;
import android.content.Context;

/**
 * Created by ekman on 21/04/16.
 */
public class FlashCards {

    private Deck currentDeck;
    private int mode = 0;

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
    public int getMode(){
        return this.mode;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

}

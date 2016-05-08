package se.tda367.flashcards.models;

import android.content.Context;

import java.util.ArrayList;

import se.tda367.flashcards.Singleton;

/**
 * Created by ekman on 21/04/16.
 */
public class FlashCards {

    private Deck currentDeck;
    private ArrayList<Deck> arrayOfDecks;
    private int mode;

    public FlashCards() {

    }

    public ArrayList<Deck> getArrayOfDecks(Context context) {

        this.arrayOfDecks = null;
        this.arrayOfDecks = Singleton.getInstance().getDatabaseController(context).getAllDecks();
        return arrayOfDecks;

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
    public void setMode(int i){
        this.mode = i;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

}
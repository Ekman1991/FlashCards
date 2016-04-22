package se.tda367.flashcards;

/**
 * Created by ekman on 21/04/16.
 */
public class FlashCards {

    private Deck currentDeck;

    public FlashCards() {



    }

    public Deck getCurrentDeck() {
        return this.currentDeck;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

}

package se.tda367.flashcards;

import android.support.v4.media.MediaMetadataCompat;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import se.tda367.flashcards.models.Card;
import se.tda367.flashcards.models.Deck;
import se.tda367.flashcards.models.FlashCards;
import se.tda367.flashcards.services.DatabaseService;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    private Deck deck;
    private Card card1;
    private Card card2;
    private Card card3;
    private FlashCards flashcards;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {

        deck = new Deck("TestDeck");
        flashcards = new FlashCards();

        card1 = new Card("Dog", "Animal");
        card2 = new Card("Cat", "Animal");
        card3 = new Card("Parrot", "Animal");

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        flashcards.setCurrentDeck(deck);
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        deck = null;
        card1 = null;
        card2 = null;
        card3 = null;
    }

    @Test
    public void createDeck_nameIsCorrect() throws Exception {

        assertEquals(deck.getName(), "TestDeck");

    }

    @Test
    public void playRound() throws Exception {

        int counter = 0;

        while (deck.getNextCard() != null) {
            counter++;
        }

        assertEquals(counter, deck.getSize());

    }

    @Test
    public void createDeck_sizeIsCorrect() throws Exception {

        assertEquals(deck.getSize(), 3);

    }

    @Test
    public void createCard_isCorrectQuestion() throws Exception {

        assertEquals(card1.getQuestion(), "Dog");

    }

    @Test
    public void createCard_isCorrectAnswer() throws Exception {

        assertEquals(card1.getAnswer(), "Animal");

    }

    @Test
    public void createCard_difficultyIsSet() {

        assertEquals(card1.getDifficulty(), 2);

    }

    @Test
    public void createCard_hasNotBeenPlayed() {
        assertEquals(card1.hasBeenPlayedOnce(), 0);
    }

    @Test
    public void setDeck_deckIsCorrect() throws Exception {

        assertEquals(flashcards.getCurrentDeck().getName(), "TestDeck");

    }

    @Test
    public void setDeck_sizeIsCorrect() throws Exception {

        assertEquals(deck.getSize(), flashcards.getCurrentDeck().getSize());

    }

    @Test
    public void setDeck_cardsAreSame() throws Exception {

        ArrayList<Card> cards = deck.getList();
        for (int i = 0; i<deck.getSize(); i++) {
            assertEquals(cards.get(i).getAnswer(), flashcards.getCurrentDeck().getList().get(i).getAnswer());
        }
    }

    @Test
    public void setDifficulty() throws Exception {

        card1.setDifficulty(1);
        assertEquals(card1.getDifficulty(), 1);

    }

    @Test
    public void setHasBeenPlayed() throws Exception {

        card1.setHasBeenPlayedOnce(true);
        assertEquals(card1.hasBeenPlayedOnce(), 1);

    }

    @Test
    public void createDeck_timeCreated() throws Exception {
        assertEquals(deck.getMade(), System.currentTimeMillis() / 1000L);
    }
}
package se.tda367.flashcards;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.tda367.flashcards.models.Card;
import se.tda367.flashcards.models.Deck;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    private Deck deck;
    private Card card1;
    private Card card2;
    private Card card3;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {

        deck = new Deck("TestDeck");

        card1 = new Card("Dog", "Animal");
        card2 = new Card("Cat", "Animal");
        card3 = new Card("Parrot", "Animal");

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);

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

}
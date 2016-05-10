package se.tda367.flashcards.services;

import java.util.ArrayList;

import se.tda367.flashcards.models.Card;
import se.tda367.flashcards.models.Deck;

/**
 * Created by ekman on 08/05/16.
 */
public interface IPersistenceService {

    public static final String LOG = "DatabaseService";

    public static final String DATABASE_NAME = "FlashCardsDatabase.db";
    public static final int DATABASE_VERSION = 1;

    public static final String DECKS_TABLE_NAME = "decks";
    public static final String DECKS_COLUMN_ID = "id";
    public static final String DECKS_COLUMN_CREATED_AT = "created_at";
    public static final String DECKS_COLUMN_NAME = "name";
    public static final String DECKS_COLUMN_DESCRIPTION = "description";
    public static final String DECKS_COLUMN_PLAYED_SINCE = "played_since";
    public static final String DECKS_COLUMN_NBR_OF_TIMES_PLAYED = "times_played";

    public static final String CARDS_TABLE_NAME = "cards";
    public static final String CARDS_COLUMN_ID = "id";
    public static final String CARDS_COLUMN_CREATED_AT = "created_at";
    public static final String CARDS_COLUMN_QUESTION = "question";
    public static final String CARDS_COLUMN_ANSWER = "answer";
    public static final String CARDS_COLUMN_DIFFICULTY = "difficulty";
    public static final String CARDS_COLUMN_IMAGE = "image";

    public static final String DECK_CARD_TABLE_NAME = "deck_cards";
    public static final String DECK_CARD_COLUMN_ID = "id";
    public static final String DECK_CARD_COLUMN_DECK_ID = "deck_id";
    public static final String DECK_CARD_COLUMN_CARD_ID = "card_id";

    // Table Create Statements
    public static final String CREATE_TABLE_DECK = "CREATE TABLE "
            + DECKS_TABLE_NAME + "(" + DECKS_COLUMN_ID + " INTEGER PRIMARY KEY," + DECKS_COLUMN_NAME
            + " TEXT," + DECKS_COLUMN_DESCRIPTION + " TEXT," + DECKS_COLUMN_PLAYED_SINCE + " INTEGER," + DECKS_COLUMN_NBR_OF_TIMES_PLAYED + " INTEGER," +  DECKS_COLUMN_CREATED_AT
            + " INTEGER" + ")";

    public static final String CREATE_TABLE_CARD = "CREATE TABLE "
            + CARDS_TABLE_NAME + "(" + CARDS_COLUMN_ID + " INTEGER PRIMARY KEY," + CARDS_COLUMN_QUESTION
            + " TEXT," + CARDS_COLUMN_ANSWER + " TEXT," + CARDS_COLUMN_DIFFICULTY + " INTEGER," + CARDS_COLUMN_CREATED_AT
            + " DATETIME" + CARDS_COLUMN_IMAGE + " BYTESIMAGES " + ")";

    public static final String CREATE_TABLE_DECK_CARDS = "CREATE TABLE "
            + DECK_CARD_TABLE_NAME + "(" + DECK_CARD_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + DECK_CARD_COLUMN_DECK_ID + " INTEGER," + DECK_CARD_COLUMN_CARD_ID + " INTEGER"
            + ")";


    public long createDeck(Deck deck);

    public Deck getDeck(long deck_id);

    public ArrayList<Deck> getAllDecks();

    public ArrayList<Card> getCardsForDeck(Deck deck);

    public int updateDeck(Deck deck);

    public void deleteDeck(long deck_id);

    public long createCardInDeck(Card card, Deck deck);

    public int updateCard(Card card);

}

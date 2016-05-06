package se.tda367.flashcards;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by ekman on 21/04/16.
 */

public class DatabaseController extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseController";

    public static final String DATABASE_NAME = "FlashCardsDatabase.db";
    private static final int DATABASE_VERSION = 1;

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

    public static final String DECK_CARD_TABLE_NAME = "deck_cards";
    public static final String DECK_CARD_COLUMN_ID = "id";
    public static final String DECK_CARD_COLUMN_DECK_ID = "deck_id";
    public static final String DECK_CARD_COLUMN_CARD_ID = "card_id";

    // Table Create Statements
    private static final String CREATE_TABLE_DECK = "CREATE TABLE "
            + DECKS_TABLE_NAME + "(" + DECKS_COLUMN_ID + " INTEGER PRIMARY KEY," + DECKS_COLUMN_NAME
            + " TEXT," + DECKS_COLUMN_DESCRIPTION + " TEXT," + DECKS_COLUMN_PLAYED_SINCE + " DATETIME," + DECKS_COLUMN_NBR_OF_TIMES_PLAYED + " INTEGER," +  DECKS_COLUMN_CREATED_AT
            + " DATETIME" + ")";

    private static final String CREATE_TABLE_CARD = "CREATE TABLE "
            + CARDS_TABLE_NAME + "(" + CARDS_COLUMN_ID + " INTEGER PRIMARY KEY," + CARDS_COLUMN_QUESTION
            + " TEXT," + CARDS_COLUMN_ANSWER + " TEXT," + CARDS_COLUMN_DIFFICULTY + " INTEGER," + CARDS_COLUMN_CREATED_AT
            + " DATETIME" + ")";

    private static final String CREATE_TABLE_DECK_CARDS = "CREATE TABLE "
            + DECK_CARD_TABLE_NAME + "(" + DECK_CARD_COLUMN_ID + " INTEGER PRIMARY KEY,"
            + DECK_CARD_COLUMN_DECK_ID + " INTEGER," + DECK_CARD_COLUMN_CARD_ID + " INTEGER"
            + ")";

    public DatabaseController(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DECK);
        db.execSQL(CREATE_TABLE_CARD);
        db.execSQL(CREATE_TABLE_DECK_CARDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CARDS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DECKS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DECK_CARD_TABLE_NAME);

        // create new tables
        onCreate(db);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }



    public long createDeck(Deck deck) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DECKS_COLUMN_NAME, deck.getName());
        contentValues.put(DECKS_COLUMN_DESCRIPTION, deck.getDescription());
        contentValues.put(DECKS_COLUMN_NBR_OF_TIMES_PLAYED, deck.getNbrOfTimesPlayed());
        //TODO: Set played since variable
        contentValues.put(DECKS_COLUMN_CREATED_AT, getDateTime());

        return db.insert(DECKS_TABLE_NAME, null, contentValues);
    }

    public Deck getDeck(long deck_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DECKS_TABLE_NAME + " WHERE "
                + DECK_CARD_COLUMN_ID + " = " + deck_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Deck deck = new Deck();
        deck.setId(c.getInt(c.getColumnIndex(DECKS_COLUMN_ID)));
        deck.setName((c.getString(c.getColumnIndex(DECKS_COLUMN_NAME))));
        deck.setDescription(c.getString(c.getColumnIndex(DECKS_COLUMN_DESCRIPTION)));
        deck.setNbrOfTimesPlayed(c.getInt(c.getColumnIndex(DECKS_COLUMN_NBR_OF_TIMES_PLAYED)));

        //TODO: Set played since variable

        return deck;
    }

    public ArrayList<Deck> getAllDecks() {
        ArrayList<Deck> decks = new ArrayList<Deck>();
        String selectQuery = "SELECT * FROM " + DECKS_TABLE_NAME;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Deck d = new Deck();
                d.setId(c.getInt((c.getColumnIndex(DECKS_COLUMN_ID))));
                d.setName((c.getString(c.getColumnIndex(DECKS_COLUMN_NAME))));
                d.setDescription(c.getString(c.getColumnIndex(DECKS_COLUMN_DESCRIPTION)));

                d.setCardsArray(getCardsForDeck(d));

                decks.add(d);
            } while (c.moveToNext());
        }

        return decks;
    }

    public ArrayList<Card> getCardsForDeck(Deck deck) {

        ArrayList<Card> list = new ArrayList<Card>();

        String selectQuery = "SELECT * FROM " + CARDS_TABLE_NAME + " cardTable, "
                + DECKS_TABLE_NAME + " decksTable, " + DECK_CARD_TABLE_NAME + " deckCardTable WHERE decksTable."
                + DECKS_COLUMN_ID + " = '" + deck.getId() + "'" + " AND decksTable." + DECKS_COLUMN_ID
                + " = " + "deckCardTable." + DECK_CARD_COLUMN_DECK_ID + " AND cardTable." + CARDS_COLUMN_ID + " = "
                + "deckCardTable." + DECK_CARD_COLUMN_CARD_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Card card = new Card();
                card.setId(c.getInt((c.getColumnIndex(CARDS_COLUMN_ID))));
                card.setQuestion((c.getString(c.getColumnIndex(CARDS_COLUMN_QUESTION))));
                card.setAnswer(c.getString(c.getColumnIndex(CARDS_COLUMN_ANSWER)));
                card.setDifficulty(c.getInt(c.getColumnIndex(CARDS_COLUMN_DIFFICULTY)));

                list.add(card);
            } while (c.moveToNext());
        }

        return list;

    }

    public int updateDeck(Deck deck) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DECKS_COLUMN_NAME, deck.getName());
        values.put(DECKS_COLUMN_DESCRIPTION, deck.getDescription());

        return db.update(DECKS_TABLE_NAME, values, DECKS_COLUMN_ID + " = ?",
                new String[] { String.valueOf(deck.getId()) });
    }

    public void deleteDeck(long deck_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DECKS_TABLE_NAME, DECKS_COLUMN_ID + " = ?",
                new String[] { String.valueOf(deck_id) });
    }

    public long createCardInDeck(Card card, Deck deck) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CARDS_COLUMN_ANSWER, card.getAnswer());
        contentValues.put(CARDS_COLUMN_QUESTION, card.getQuestion());
        contentValues.put(CARDS_COLUMN_DIFFICULTY, card.getDifficulty());
        contentValues.put(CARDS_COLUMN_CREATED_AT, getDateTime());

        long card_id = db.insert(CARDS_TABLE_NAME, null, contentValues);

        addCardToDeck(card_id, deck.getId());

        return card_id;
    }

    private void addCardToDeck(long card_id, long deck_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DECK_CARD_COLUMN_CARD_ID, card_id);
        contentValues.put(DECK_CARD_COLUMN_DECK_ID, deck_id);

        long retValue = db.insert(DECK_CARD_TABLE_NAME, null, contentValues);

    }

    public int updateCard(Card card) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CARDS_COLUMN_QUESTION, card.getQuestion());
        values.put(CARDS_COLUMN_ANSWER, card.getAnswer());
        values.put(CARDS_COLUMN_DIFFICULTY, card.getDifficulty());

        return db.update(CARDS_TABLE_NAME, values, CARDS_COLUMN_ID + " = ?",
                new String[] { String.valueOf(card.getId()) });
    }

    //TODO: Delete cards and when deleting a deck remove the associative cards

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}

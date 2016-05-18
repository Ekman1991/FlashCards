package se.tda367.flashcards.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import se.tda367.flashcards.models.Card;
import se.tda367.flashcards.models.Deck;

/**
 * Created by ekman on 21/04/16.
 */

public class DatabaseService extends SQLiteOpenHelper implements IPersistenceService {

    public DatabaseService(Context context) {
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
        contentValues.put(DECKS_COLUMN_PLAYED_SINCE, deck.getPlayedSince());
        contentValues.put(DECKS_COLUMN_CREATED_AT, deck.getMade());
        contentValues.put(DECKS_COLUMN_CARDS_PLAYED, deck.getNbrOfCardsPlayed());
        contentValues.put(DECKS_COLUMN_TIME_PLAYED, deck.getAmountOfTimePlayed());

        return db.insert(DECKS_TABLE_NAME, null, contentValues);
    }

    public Deck getDeck(long deck_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DECKS_TABLE_NAME + " WHERE "
                + DECK_CARD_COLUMN_ID + " = " + deck_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        //TODO: Check with FindBugs
        if (c != null)
            c.moveToFirst();

        Deck deck = new Deck();
        deck.setId(c.getInt(c.getColumnIndex(DECKS_COLUMN_ID)));
        deck.setName((c.getString(c.getColumnIndex(DECKS_COLUMN_NAME))));
        deck.setDescription(c.getString(c.getColumnIndex(DECKS_COLUMN_DESCRIPTION)));
        deck.setNbrOfTimesPlayed(c.getInt(c.getColumnIndex(DECKS_COLUMN_NBR_OF_TIMES_PLAYED)));
        deck.setPlayedSince(c.getInt(c.getColumnIndex(DECKS_COLUMN_PLAYED_SINCE)));
        deck.setMade(c.getInt(c.getColumnIndex(DECKS_COLUMN_CREATED_AT)));
        deck.setAmountOfTimePlayed(c.getDouble(c.getColumnIndex(DECKS_COLUMN_TIME_PLAYED)));
        deck.setNbrOfCardsPlayed(c.getInt(c.getColumnIndex(DECKS_COLUMN_CARDS_PLAYED)));

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
                d.setNbrOfTimesPlayed(c.getInt(c.getColumnIndex(DECKS_COLUMN_NBR_OF_TIMES_PLAYED)));
                d.setPlayedSince(c.getInt(c.getColumnIndex(DECKS_COLUMN_PLAYED_SINCE)));
                d.setMade(c.getInt(c.getColumnIndex(DECKS_COLUMN_CREATED_AT)));
                d.setAmountOfTimePlayed(c.getDouble(c.getColumnIndex(DECKS_COLUMN_TIME_PLAYED)));
                d.setNbrOfCardsPlayed(c.getInt(c.getColumnIndex(DECKS_COLUMN_CARDS_PLAYED)));
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
        values.put(DECKS_COLUMN_NBR_OF_TIMES_PLAYED, deck.getNbrOfTimesPlayed());
        values.put(DECKS_COLUMN_PLAYED_SINCE, deck.getPlayedSince());
        values.put(DECKS_COLUMN_CREATED_AT, deck.getMade());
        values.put(DECKS_COLUMN_TIME_PLAYED, deck.getAmountOfTimePlayed());
        values.put(DECKS_COLUMN_CARDS_PLAYED, deck.getNbrOfCardsPlayed());

        return db.update(DECKS_TABLE_NAME, values, DECKS_COLUMN_ID + " = ?",
                new String[] { String.valueOf(deck.getId()) });
    }

    public void deleteDeck(long deck_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DECKS_TABLE_NAME, DECKS_COLUMN_ID + " = ?",
                new String[] { String.valueOf(deck_id) });
        db.delete(DECK_CARD_TABLE_NAME, DECK_CARD_COLUMN_DECK_ID + " = ?", new String[] {String.valueOf(deck_id)});
    }

    public long createCardInDeck(Card card, Deck deck) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CARDS_COLUMN_ANSWER, card.getAnswer());
        contentValues.put(CARDS_COLUMN_QUESTION, card.getQuestion());
        contentValues.put(CARDS_COLUMN_DIFFICULTY, card.getDifficulty());
        //TODO: Change to System.getTime
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

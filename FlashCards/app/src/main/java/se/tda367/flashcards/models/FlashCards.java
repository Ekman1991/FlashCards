package se.tda367.flashcards.models;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import se.tda367.flashcards.ServiceLocator;

/**
 * Created by ekman on 21/04/16.
 */
public class FlashCards {

    private Deck currentDeck;
    private ArrayList<Deck> arrayOfDecks;
    private int mode;
    private int amount;
    private String url;
    private byte[] audioByte;
    private boolean editMode;

    public FlashCards() {

    }


    public byte[] getAudioByte(){
        Log.v("AUDIO BTE ", audioByte + "");

        return this.audioByte;
    }

    public void setAudioByte(byte[] audioByte){
        Log.v("AUDIO BTE ", audioByte + "");
        this.audioByte = audioByte;
    }

    public int getSize(){
        return arrayOfDecks.size();
    }

    public Deck getCurrentDeck() {

        //Update the current deck
        /*int deck_id = currentDeck.getId();
        Deck newDeck = ServiceLocator.getInstance().getDatabaseController(context).getDeck(deck_id);
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

    public void setAmount(int amount){
        this.amount = amount;
    }
    public int getAmount(){
        return this.amount;
    }

    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl() {
        return this.url;
    }
    public Boolean hasCurrentDeck() {
        if (currentDeck == null) {
            return false;
        }   else return true;
    }
    public Boolean getEditMode(){
        return this.editMode;
    }
    public void setEditMode(Boolean b){
        editMode = b;
    }
    public void reverseEditMode(){
        editMode = !editMode;
    }

}

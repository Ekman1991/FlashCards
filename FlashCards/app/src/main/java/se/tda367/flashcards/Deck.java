package se.tda367.flashcards;

import android.os.Parcelable;
import android.os.Parcel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by ZlatanH on 2016-04-19.
 */
public class Deck{
    private int id;
    private String name;
    private String description;
    private ArrayList<Card> list;
    private int counter;
    private Calender made;
    private Calender playedSince;
    private int timesPlayed;
    private Calender c = new Calender();
    public Deck() {
        this.name = "";
        this.list = new ArrayList<Card>();
        this.counter = 0;
        this.timesPlayed = 0;
    }

    public Deck(String name) {
        this.name = name;
        this.list = new ArrayList<Card>();
        this.counter = 0;
    }
    public boolean hasNext(){
        return counter<list.size();
    }

    public int getId() {
        return this.id;
    }

    public Calender getMade(){
        return made;
    }
    public void playedDeck(){
        timesPlayed++;
    }
    public int timesPlayed(){
        return timesPlayed;
    }

    public Calender getPlayedSince(){
        return playedSince;
    }

    public void setStartDay(){
        made = c.startDay();
    }

    public void setPlayedSince(){
        playedSince = c.startDay();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void addCard(Card card){
        list.add(card);
    }

    public void setCardsArray(ArrayList<Card> cards) {
        this.list = cards;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Card getNextCard() {

        if (counter < list.size()) {
            return list.get(counter++);
        } else {
            return null;
        }

    }

    public int getSize() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    public void shuffle(){
        Random r = new Random();
        int size = list.size();
        ArrayList<Card> tmp = new ArrayList<Card>();
        for(int i = 0; i<size; i++){
            int pos = r.nextInt(list.size());
            tmp.add(list.get(pos));
            list.remove(pos);
        }
        for(int j = 0; j<tmp.size(); j++){
            list.add(tmp.get(j));
        }


    }

    //For getting the name in the listview instead of the reference
    @Override
    public String toString() {
        return getName();
    }

}
package se.tda367.flashcards.models;

import java.util.ArrayList;
import java.util.Random;

import se.tda367.flashcards.Calender;

/**
 * Created by ZlatanH on 2016-04-19.
 */
public class Deck{
    private int id;
    private String name;
    private String description;
    private ArrayList<Card> list;
    private int counter;

    private long made;
    private long playedSince;


    private int nbrOfTimesPlayed;
    private int nbrOfCardsPlayed;
    private double timePlayed;
    private Calender c = new Calender();


    public Deck() {
        this.name = "";
        this.list = new ArrayList<Card>();
        this.counter = 0;
        this.nbrOfTimesPlayed = 0;
        //-1 indicates that the deck have not been played yet.
        this.playedSince = -1;
        this.made = System.currentTimeMillis() / 1000L;
        this.nbrOfCardsPlayed = 0;
        this.timePlayed = 0;
    }

    public Deck(String name) {
        this.name = name;
        this.list = new ArrayList<Card>();
        this.counter = 0;
        this.nbrOfTimesPlayed = 0;
        //-1 indicates that the deck have not been played yet.
        this.playedSince = -1;
        this.made = System.currentTimeMillis() / 1000L;
        this.nbrOfCardsPlayed = 0;
        this.timePlayed = 0;
    }
    public boolean hasNext(){
        return counter<list.size();
    }

    public int getId() {
        return this.id;
    }

    public long getMade(){
        return made;
    }

    public void playedDeck(){
        nbrOfTimesPlayed++;
    }

    public int getNbrOfTimesPlayed(){
        return this.nbrOfTimesPlayed;
    }

    public int getNbrOfCardsPlayed() {
        return this.nbrOfCardsPlayed;
    }

    public void setNbrOfCardsPlayed(int cardsPlayed) {
        this.nbrOfCardsPlayed = cardsPlayed;
    }

    public void increaseNbrOfCardsPlayed() {
        this.nbrOfCardsPlayed++;
    }

    public double getAmountOfTimePlayed() {
        return this.timePlayed;
    }

    public void setAmountOfTimePlayed(double amountOfTime) {
        this.timePlayed = amountOfTime;
    }

    public void increaseAmountOfTimePlayed(long timePlayed) {
        this.timePlayed += timePlayed;
    }

    public void setNbrOfTimesPlayed(int nbrOfTimesPlayed) {
        this.nbrOfTimesPlayed = nbrOfTimesPlayed;
    }

    public long getPlayedSince(){
        return playedSince;
    }

    public void setPlayedSince(long time){
        this.playedSince = time;
    }

    public void setPlayedNow(){
        this.playedSince = System.currentTimeMillis() / 1000L;
    }

    public void setMade(long time){
        this.made = time;
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
    public ArrayList<Card> getList(){
        return list;
    }

    //For getting the name in the listview instead of the reference
    @Override
    public String toString() {
        return getName();
    }

}
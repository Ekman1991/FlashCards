package se.tda367.flashcards;

import android.os.Parcelable;
import android.os.Parcel;
import java.util.ArrayList;

/**
 * Created by ZlatanH on 2016-04-19.
 */
public class Deck implements Parcelable{
    private String name;
    private ArrayList<Card> list = new ArrayList<Card>();

    //Create Deck with 6 cards for testing
    protected Deck(String name){
        this.name = name;
    }

    //Needed for Parcelable which is a temporary solution
    protected Deck (Parcel in) {
        readFromParcel(in);
    }

    public String getName(){
        return this.name;
    }

    //For getting the name in the listview instead of the reference
    @Override
    public String toString() {
        return getName();
    }

    public void addCard(Card card){
        list.add(card);
    }

    //för test
    public String play(int code, int position) {
        while (position < list.size()) {
            if (code == 0) {
                String question = list.get(position).getQuestion();
                return question;
            }   else if (code == 1) {
                String answer = list.get(position).getAnswer();
                return answer;
            }
        }
        return ("Error");
    }

    public int getSize() {
        return list.size();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeTypedList(list);
    }

    //Everything below is also need to implement Parcelable
    private void readFromParcel(Parcel in) {
        name = in.readString();
        list = in.createTypedArrayList(Card.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Deck> CREATOR = new Parcelable.Creator<Deck>() {

        public Deck createFromParcel(Parcel in) {
            return new Deck(in);
        }

        public Deck[] newArray(int size) {
            return new Deck[size];
        }

    };


}
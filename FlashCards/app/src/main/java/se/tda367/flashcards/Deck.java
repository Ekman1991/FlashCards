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
        list.add(new Card("Dog", "Animal"));
        list.add(new Card("Cat", "Animal"));
        list.add(new Card("Fish", "Animal"));
        list.add(new Card("Stone", "!Animal"));
        list.add(new Card("Computer", "!Animal"));
        list.add(new Card("Giraffe", "Animal"));

    }

    //Needed for Parcelable which is a temporary solution
    protected Deck (Parcel in) {
        this(in.readString());
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
        return ("Broken");
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
        list = in.createTypedArrayList(Card.CREATOR);
        name = in.readString();
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
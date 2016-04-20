package se.tda367.flashcards;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZlatanH on 2016-04-19.
 */
public class Card implements Parcelable {
    private String question;
    private String answer;
    private int difficulty;

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.difficulty = 0;
    }

    //Needed to implement Parcelable
    public Card (Parcel in) {
        readFromParcel(in);
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    //Everything below is needed only to implement Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(question);
        out.writeString(answer);
    }

    private void readFromParcel(Parcel in) {
        question = in.readString();
        answer = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {

        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        public Card[] newArray(int size) {
            return new Card[size];
        }

    };


}

package se.tda367.flashcards;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZlatanH on 2016-04-19.
 */
public class Card {
    private String question;
    private String answer;
    private int difficulty;

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.difficulty = 0;
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

}

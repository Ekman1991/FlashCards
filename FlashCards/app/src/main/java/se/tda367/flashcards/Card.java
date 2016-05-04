package se.tda367.flashcards;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZlatanH on 2016-04-19.
 */
public class Card {
    private String question;
    private String answer;
    private int id;
    private int difficulty;

    public Card() {
        this.question = "";
        this.answer = "";
        this.difficulty = 0;
        //0 = svår, 1 = okej, 2 = lätt
    }

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;

    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }

    public int getDifficulty(){
        return difficulty;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


}

package se.tda367.flashcards.models;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by ZlatanH on 2016-04-19.
 */
public class Card extends AppCompatActivity {
    private String question;
    private String answer;
    private int id;
    private int difficulty;
    private boolean firstTimePlayed;

    public Card() {
        this.question = "";
        this.answer = "";
        this.difficulty = 2;
        this.firstTimePlayed = true;

        //0 = svår, 1 = okej, 2 = lätt
    }

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.difficulty = 2;

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

    public boolean isFirstTimePlayed() {
        return this.firstTimePlayed;
    }

    public void setPlayed(boolean b) {
        this.firstTimePlayed = b;
    }


}

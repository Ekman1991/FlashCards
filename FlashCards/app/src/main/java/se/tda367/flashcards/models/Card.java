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
    private boolean hasBeenPlayedOnce;
    private byte[] imagesByte;
    private byte[] audioByte;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (question != null ? !question.equals(card.question) : card.question != null)
            return false;
        return answer != null ? answer.equals(card.answer) : card.answer == null;

    }

    @Override
    public int hashCode() {
        int result = question != null ? question.hashCode() : 0;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }

    public Card() {
        this.question = "";
        this.answer = "";
        this.difficulty = 2;
        this.hasBeenPlayedOnce = false;
        this.imagesByte = new byte[0];
        this.audioByte = new byte[0];

        //0 = svår, 1 = okej, 2 = lätt
    }

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.difficulty = 2;
        this.hasBeenPlayedOnce = false;
        this.imagesByte = new byte[0];
        this.audioByte = new byte[0];
    }

    // if boolean is true it's a picture if boolean is false it's an audio
    public Card(String question, String answer, byte[] bytes, boolean audioPic) {
          this.question = question;
          this.answer = answer;
          this.difficulty = 2;
          this.hasBeenPlayedOnce = false;
          if (audioPic == false) {
              this.imagesByte = new byte[0];
              this.audioByte = bytes;
          } else {
              this.audioByte = new byte[0];
              this.imagesByte = bytes;
          }
    }

    public Card(String question, String answer, byte[] imagesByte, byte[] audioByte) {
          this.question = question;
          this.answer = answer;
          this.difficulty = 2;
          this.hasBeenPlayedOnce = false;
          this.imagesByte = imagesByte;
          this.audioByte = audioByte;

    }

    public byte[] getImageByte(){
        return this.imagesByte;
    }

    public byte[] getAudioByte(){
        return this.audioByte;
    }

    public void setImageByte(byte[]imagesByte){
        this.imagesByte = imagesByte;

    }

    public void setAudioByte(byte[] audioByte){
        this.audioByte = audioByte;
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

    public void setHasBeenPlayedOnce(boolean b) {
        this.hasBeenPlayedOnce = b;
    }

    //Database did not work with booleans for some reason, had to resort to integers
    public int hasBeenPlayedOnce() {
        if (hasBeenPlayedOnce == true) {
            return 1;
        }   else return 0;
    }

    public boolean hasQuestion() {
        return question.length() > 0;
    }
}

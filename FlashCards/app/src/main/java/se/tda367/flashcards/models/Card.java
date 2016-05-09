package se.tda367.flashcards.models;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

/**
 * Created by ZlatanH on 2016-04-19.
 */
public class Card extends AppCompatActivity {
    private String question;
    private String answer;
    private int id;
    private int difficulty;
    private Bitmap image;
    private byte[] imagesBytes;

    public Card() {
        this.question = "";
        this.answer = "";
        this.difficulty = 2;
        this.image = null;
        this.imagesBytes = null;

        //0 = svår, 1 = okej, 2 = lätt
    }

    public Card(String question, String answer){
        this.question = question;
        this.answer = answer;
        this.difficulty = 2;
    }

    public Card(String question, String answer, Bitmap image) {
        this.question = question;
        this.answer = answer;
        this.difficulty = 2;
        this.image = image;

    }


    public byte[] getImagesBytes(){
        imagesBytes = getBitmapAsByteArray(image);
        return imagesBytes;
    }


    public Bitmap getImage(){
        return this.image;
    }

    public void setImage(Bitmap image){
        this.image = image;
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

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

}

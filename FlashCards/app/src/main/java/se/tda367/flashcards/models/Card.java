package se.tda367.flashcards.models;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by ZlatanH on 2016-04-19.
 */
public class Card extends AppCompatActivity {
    private String question;
    private String answer;
    private int id;
    private int difficulty;
    private MediaRecorder audio;
    private byte[] audioByte;

    public Card() {
        this.question = "";
        this.answer = "";
        this.difficulty = 2;
        this.audio = null;
        this.audioByte = null;

        //0 = svår, 1 = okej, 2 = lätt
    }

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.difficulty = 2;

    }

    public Card(String question, String answer, byte[] audioByte){
        this.question = question;
        this.answer = answer;
        this.difficulty = 2;
        this.audioByte = audioByte;
    }

    public byte[] getAudioByte(){
        return this.audioByte;
    }

    public void setAudioByte(byte[] audioByte){
        this.audioByte = audioByte;
    }

    public MediaRecorder getAudio(){

        try {
            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile("kurchina", "mp3", getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(getAudioByte());
            fos.close();

            // Tried reusing instance of media player
            // but that resulted in system crashes...
            MediaPlayer mediaPlayer = new MediaPlayer();

            // Tried passing path directly, but kept getting
            // "Prepare failed.: status=0x1"
            // so using file descriptor instead
            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();

            return this.audio;
        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "No audio sorry",
                    Toast.LENGTH_SHORT).show();
        }
        return this.audio;
    }

    public void setAudio(MediaRecorder audio){
        this.audio = audio;
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

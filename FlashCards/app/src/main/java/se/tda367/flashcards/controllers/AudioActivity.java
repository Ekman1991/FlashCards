package se.tda367.flashcards.controllers;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import se.tda367.flashcards.R;
import se.tda367.flashcards.ServiceLocator;

public class AudioActivity extends AppCompatActivity {



    public MediaRecorder myAudio;
    private String outputAudioFile = null;
    private Button start, stop, play;
    private Intent intentMain;
    private byte[] fileByteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fileByteArray = new byte[0];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        start = (Button) findViewById(R.id.startButton);
        stop = (Button) findViewById(R.id.stopButton);
        play = (Button) findViewById(R.id.playButton);


        stop.setEnabled(false);
        play.setEnabled(false);
        outputAudioFile = Environment.getExternalStorageDirectory().getAbsolutePath();
        outputAudioFile += "/myrec.3gp";



    }

    public Intent getIntentMain(){
        return this.intentMain;
    }

    public void start(View v){
        myAudio = new MediaRecorder();

        myAudio.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudio.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudio.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        myAudio.setOutputFile(outputAudioFile);


        try{

            myAudio.prepare();
            myAudio.start();

        } catch(IllegalStateException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        start.setEnabled(false);
        stop.setEnabled(true);

        Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show();
    }

    public void cancel(View v){

        Intent intentMain = new Intent(AudioActivity.this ,
                CreateCardActivity.class);
        AudioActivity.this.startActivityForResult(intentMain, 0);
    }

    public void save(View v){

        if (outputAudioFile.equals(null)){
            Toast.makeText(this, "You haven't recorded anything yet", Toast.LENGTH_SHORT).show();

        }
        else {


            try {

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(outputAudioFile));

                int read;
                byte[] buff = new byte[1024];
                while ((read = in.read(buff)) > 0)
                {
                    out.write(buff, 0, read);
                }
                out.flush();
                byte[] audioBytes = out.toByteArray();


                ServiceLocator.getInstance().getFlashCards().setAudioByte(audioBytes);
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "No audio",
                        Toast.LENGTH_SHORT).show();
            }
            Intent intentMain = new Intent(AudioActivity.this,
                    CreateCardActivity.class);
            AudioActivity.this.startActivityForResult(intentMain, 0);


        }
    }



    public void stop(View v){
        myAudio.stop();
        myAudio.release();
        myAudio = null;
        stop.setEnabled(false);
        play.setEnabled(true);
        Toast.makeText(this, "Audiofile recorded with success",
                Toast.LENGTH_SHORT).show();

    }

    public void play(View v) throws IOException{
        MediaPlayer mediaplayer = new MediaPlayer();
        mediaplayer.setDataSource(outputAudioFile);
        mediaplayer.prepare();
        mediaplayer.start();
        Toast.makeText(this, "Playing audio", Toast.LENGTH_SHORT).show();

    }



}

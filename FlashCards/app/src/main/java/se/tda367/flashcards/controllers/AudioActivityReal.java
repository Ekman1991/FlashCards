package se.tda367.flashcards.controllers;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import se.tda367.flashcards.R;

public class AudioActivityReal extends AppCompatActivity {



        MediaRecorder myAudio;
        private String outputAudioFile = null;
        private Button start, stop, play;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_audio_activity_real);
            start = (Button) findViewById(R.id.startButton);
            stop = (Button) findViewById(R.id.stopButton);
            play = (Button) findViewById(R.id.playButton);


            stop.setEnabled(false);
            play.setEnabled(false);
            outputAudioFile = Environment.getExternalStorageDirectory().getAbsolutePath();
            outputAudioFile += "/myrec.3gp";



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

    public void stop(View v){
        myAudio.stop();
        myAudio.release();
        myAudio = null;
        stop.setEnabled(false);
        play.setEnabled(true);
        Toast.makeText(this, "Audiofile recorded with success", Toast.LENGTH_SHORT).show();
    }

    public void play(View v) throws IOException{
        MediaPlayer mediaplayer = new MediaPlayer();
        mediaplayer.setDataSource(outputAudioFile);
        mediaplayer.prepare();
        mediaplayer.start();
        Toast.makeText(this, "Playing audio", Toast.LENGTH_SHORT).show();

    }



}

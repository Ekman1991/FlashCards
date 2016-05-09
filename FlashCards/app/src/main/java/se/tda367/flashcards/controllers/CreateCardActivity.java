package se.tda367.flashcards.controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import se.tda367.flashcards.CardFactory;
import se.tda367.flashcards.MyNotification;
import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.Card;
import se.tda367.flashcards.models.Deck;

public class CreateCardActivity extends AppCompatActivity {
    public static final int IMAGE_GALLERY_REQUEST = 20;
    EditText question;
    EditText answer;
    CardFactory cardFactory;
    MyNotification nf;
    private ImageView imgPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        question = (EditText)findViewById(R.id.questionField);
        answer = (EditText)findViewById(R.id.answerField);
        cardFactory = new CardFactory();

        //see pic
        imgPicture = (ImageView) findViewById(R.id.imgPic);
    }
    public void notify(View v){
        nf.addNotification();
        nf.notify();
    }
    public void createCardButton(View v) {

        String questionText;
        String answerText;
        Deck currentDeck;

        //TODO: Move this to a utility class. Will be duplicated all over the codebase
        if ((question == null || question.getText().toString().trim().length() == 0) && (answer == null || answer.getText().toString().trim().length() == 0)) {
            Log.d("CreateCard", "DeckName is empty");
        } else {
            questionText = question.getText().toString();
            answerText = answer.getText().toString();
            currentDeck = Singleton.getInstance().getFlashCards().getCurrentDeck();

            Card card = new Card(questionText, answerText);
            Singleton.getInstance().getDatabaseController(getApplicationContext()).createCardInDeck(card, currentDeck);
            //TODO: Replace this, will easily be duplicates of cards. Refetch from database instead.
            currentDeck.addCard(card);


            Intent intentMain = new Intent(CreateCardActivity.this ,
                    DeckActivity.class);
            CreateCardActivity.this.startActivityForResult(intentMain, 0);
        }


    }

    public void onImageGalleryClicked(View v){

        //invoke imagegallry with intent
        Intent photoPicIntent = new Intent(Intent.ACTION_PICK);

        //where we find the data
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();

        Uri data = Uri.parse(pictureDirectoryPath);

        //get all imagestypes
        photoPicIntent.setDataAndType(data, "image/*");

        //invoke activity
        startActivityForResult(photoPicIntent, IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == IMAGE_GALLERY_REQUEST){

                //SDcard adress
                Uri imageUri = data.getData();

                //image data from sd to stream
                InputStream inputS;

                try {

                    inputS = getContentResolver().openInputStream(imageUri);

                    Bitmap bitmap = BitmapFactory.decodeStream(inputS);

                    //show picture
                    imgPicture.setImageBitmap(bitmap);

                } catch (FileNotFoundException e){
                    e.printStackTrace();
                    //the pic isnt unavible
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    public void cancelCreateCard(View v) {
        Log.d("YourTag", "YourOutput");
        Intent intentMain = new Intent(CreateCardActivity.this ,
                DeckActivity.class);
        CreateCardActivity.this.startActivityForResult(intentMain, 0);
    }
}

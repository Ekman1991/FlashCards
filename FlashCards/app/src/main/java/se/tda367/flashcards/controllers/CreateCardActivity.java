package se.tda367.flashcards.controllers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import se.tda367.flashcards.CardFactory;
import se.tda367.flashcards.R;
import se.tda367.flashcards.Singleton;
import se.tda367.flashcards.models.Card;
import se.tda367.flashcards.models.Deck;

public class CreateCardActivity extends AppCompatActivity {
    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int CAM_REQUEST = 21;
    EditText question;
    EditText answer;
    CardFactory cardFactory;
    private ImageView imgPicture;
    private byte[] imagesByte;
    Button takePhoto;
    private boolean pictureOrAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        question = (EditText)findViewById(R.id.questionField);
        answer = (EditText)findViewById(R.id.answerField);
        cardFactory = new CardFactory();

        //see pic
        imgPicture = (ImageView) findViewById(R.id.imgPic);

        takePhoto = (Button) findViewById((R.id.camera));

        takePhoto.setOnClickListener(new takePhotoClicker());
    }

    public void audio(View v){

        //start of a notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Time for you to play some Flash Cards")
                .setContentText("Learning is fun, let's do this")
                .setAutoCancel(true)
                .setColor(Color.BLUE);

        //start program when active
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

        PendingIntent content = PendingIntent.getActivity(getApplicationContext(), 1, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(content);

        // Add a notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
        //end of a notificaiton

        Intent intentMain = new Intent(CreateCardActivity.this ,
                AudioActivityReal.class);
        CreateCardActivity.this.startActivityForResult(intentMain, 0);

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
            if (imagesByte == null) {
                Card card = new Card(questionText, answerText);
                Singleton.getInstance().getDatabaseController(getApplicationContext()).createCardInDeck(card, currentDeck);
                //TODO: Replace this, will easily be duplicates of cards. Refetch from database instead.
                currentDeck.addCard(card);

                Log.v("***********************", "" + card.getAnswer());

                Intent intentMain = new Intent(CreateCardActivity.this,
                        DeckActivity.class);
                CreateCardActivity.this.startActivityForResult(intentMain, 0);
            }else {
                Card card = new Card(questionText, answerText, imagesByte, true);

                Singleton.getInstance().getDatabaseController(getApplicationContext()).createCardInDeck(card, currentDeck);
                //TODO: Replace this, will easily be duplicates of cards. Refetch from database instead.
                currentDeck.addCard(card);

                Log.v("***********************", "" + card.getImageByte());



                Intent intentMain = new Intent(CreateCardActivity.this,
                        DeckActivity.class);
                CreateCardActivity.this.startActivityForResult(intentMain, 0);

            }
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

    public void onCameraClicked(View v){

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
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST || requestCode == CAM_REQUEST){

                //SDcard adress
                Uri imageUri = data.getData();

                //image data from sd to stream
                InputStream inputS;

                try {
                    if (requestCode == IMAGE_GALLERY_REQUEST) {


                        inputS = getContentResolver().openInputStream(imageUri);

                        Bitmap bitmap = BitmapFactory.decodeStream(inputS);

                        //show picture
                        imgPicture.setImageBitmap(bitmap);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        imagesByte = stream.toByteArray();
                    }
                    else {
                        Bitmap camera = (Bitmap) data.getExtras().get("data");
                        imgPicture.setImageBitmap(camera);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        camera.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        imagesByte = stream.toByteArray();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //the pic isnt unavible
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }

            }
        }

    }

    class takePhotoClicker implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraintent, CAM_REQUEST);

        }
    }



    public void cancelCreateCard(View v) {
        Log.d("YourTag", "YourOutput");
        Intent intentMain = new Intent(CreateCardActivity.this ,
                DeckActivity.class);
        CreateCardActivity.this.startActivityForResult(intentMain, 0);
    }
}

package se.tda367.flashcards;

import android.graphics.Bitmap;
import android.media.Image;

import se.tda367.flashcards.models.Card;

/**
 * Created by ZlatanH on 2016-04-19.
 */
public class CardFactory {

    public Card createCard(String question, String answer) {
        return new Card (question, answer);
    }

    public Card createCard(String question, String answer, Bitmap image) {
        return new Card (question, answer, image);
    }



    public Card createCard() {
        //använd den här för att med GUIt få in frågan och svaret
        String question = "cat";
        String answer = "animal";
        Bitmap image = null;
        return new Card (question, answer, image);
    }
}

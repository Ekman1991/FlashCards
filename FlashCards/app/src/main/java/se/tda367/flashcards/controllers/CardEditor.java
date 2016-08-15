package se.tda367.flashcards.controllers;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import se.tda367.flashcards.R;
import se.tda367.flashcards.ServiceLocator;

/**
 * Created by Razzan on 2016-08-16.
 */
public class CardEditor extends AppCompatActivity {
    private EditText textView;
    private EditText editText;
    private Button delButton;
    public CardEditor(){


    }

    public void firstEdit(View v){
        View parent = (View)v.getParent();
        textView = (EditText) parent.findViewById(R.id.textView);
        editText = (EditText)parent.findViewById(R.id.editText);
        delButton = (Button)parent.findViewById(R.id.delButton);

        textView.clearFocus();
        textView.setFocusable(true);
        textView.setFocusableInTouchMode(true);
        textView.requestFocus();

        editText.clearFocus();
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        delButton.setVisibility(View.VISIBLE);

        ServiceLocator.getInstance().getFlashCards().reverseEditMode();
    }
    public void secondEdit(View v){
        View parent = (View)v.getParent();
        textView = (EditText) parent.findViewById(R.id.textView);
        editText = (EditText)parent.findViewById(R.id.editText);
        delButton = (Button)parent.findViewById(R.id.delButton);

        textView.setFocusable(false);
        textView.clearFocus();
        textView.setFocusableInTouchMode(false);

        editText.setFocusable(false);
        editText.clearFocus();
        editText.setFocusableInTouchMode(false);

        delButton.setVisibility(View.GONE);


        ServiceLocator.getInstance().getFlashCards().reverseEditMode();
    }
}

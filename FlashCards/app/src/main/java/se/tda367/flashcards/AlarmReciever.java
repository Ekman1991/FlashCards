package se.tda367.flashcards;

/**
 * Created by Emilia on 2016-05-04.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;

public class AlarmReciever extends BroadcastReceiver {

    @Override
        public void onReceive(Context context, Intent intent) {
                try {
                    Bundle bundle = intent.getExtras();
                    String message = bundle.getString("alarm_message");

                    Intent newIntent = new Intent(context, FlashCards.class);
                    newIntent.putExtra("message", message);
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(newIntent);
                } catch (Exception e) {
                    Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();


        }
    }
}
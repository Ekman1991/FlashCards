package se.tda367.flashcards;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;


/**
 * Created by Emilia on 2016-04-27.
 */
public class Notifications{

    public void Notifications(Context context) {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);
        //Insert icon here
        nBuilder.setSmallIcon();
        nBuilder.setContentTitle("My notification");
        nBuilder.setContentText("Hello World!");

        NotificationManager mNotifyMgr = getS

    // Sets an ID for the notification
            int mNotificationId = 001;
    // Gets an instance of the NotificationManager service
    NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
    mNotifyMgr.notify(mNotificationId,mBuilder.build());
    }
}
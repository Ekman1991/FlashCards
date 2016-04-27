package se.tda367.flashcards;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Emilia on 2016-04-27.
 *//*
public class Notifications {
    NotificationCompat.Builder mBuilder = ;
            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("My notification")
                    .setContentText("Hello World!");


    Intent resultIntent = new Intent(this, ResultActivity.class);
    ...
    // Because clicking the notification opens a new ("special") activity, there's
    // no need to create an artificial back stack.
    PendingIntent resultPendingIntent =
            PendingIntent.getActivity(
                    this,
                    0,
                    resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

    PendingIntent resultPendingIntent;
    ...
            mBuilder.setContentIntent(resultPendingIntent);

    NotificationCompat.Builder mBuilder;
    ...
    // Sets an ID for the notification
    int mNotificationId = 001;
    // Gets an instance of the NotificationManager service
    NotificationManager mNotifyMgr =
            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
    mNotifyMgr.notify(mNotificationId, mBuilder.build());
}*/
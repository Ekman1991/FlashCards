package se.tda367.flashcards;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.widget.RemoteViews;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by Emilia on 2016-04-27.
 */
public class Notifications extends Service {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotification(){
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.widget);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.ic_launcher).setContent(remoteViews);

        Intent resultIntent = new Intent(this, FlashCards.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(FlashCards.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.button1, resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(100, mBuilder.build());

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

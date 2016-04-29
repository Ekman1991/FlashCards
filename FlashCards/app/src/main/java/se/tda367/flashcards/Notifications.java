package se.tda367.flashcards;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by Emilia on 2016-04-27.
 */
public class Notifications extends Service {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotification(View v){
        Notification.Builder builder = new Notification.Builder(this);
        //Insert icon here
        builder.setSmallIcon(0);
        builder.setContentTitle("My notification");
        builder.setContentText("Hello World!");

        Notification notification = builder.build();
        notification.notify();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
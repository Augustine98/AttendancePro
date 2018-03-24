package com.example.augustine.collegeplus;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

/**
 * Created by Augustine on 3/18/2018.
 */

public class MyNewCustomIntent extends IntentService {
    private  int NOTIFICATION_ID = 4;

    public MyNewCustomIntent() {
        super("MyNewCustomTntent");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setAutoCancel(true);
        String subName = intent.getExtras().getString("subject");
        String message = intent.getExtras().getString("message");
        Long theTime = intent.getExtras().getLong("time");
        for(int i=0;i<subName.length();i++)
        {
            NOTIFICATION_ID+=subName.charAt(i)-'A';
        }
        Log.e(subName,Integer.toString(NOTIFICATION_ID));

        builder.setContentTitle(subName);
        builder.setWhen(theTime);
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        builder.setSound(uri);
        builder.setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH);
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
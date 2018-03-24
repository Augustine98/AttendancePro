package com.example.augustine.collegeplus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Augustine on 3/18/2018.
 */

public class CustomNotificationReceiver extends BroadcastReceiver {
    public CustomNotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String subName=intent.getExtras().getString("subject");
        String message=intent.getExtras().getString("message");
        Long theTime=intent.getExtras().getLong("time");


        Intent intent1 = new Intent(context, MyNewCustomIntent.class);
        intent1.putExtra("subject",subName);
        intent1.putExtra("message",message);
        intent1.putExtra("time",theTime);
        context.startService(intent1);
    }
}
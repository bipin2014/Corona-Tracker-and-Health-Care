package com.bipin.healthcare;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyAlarm extends BroadcastReceiver {
    private NotificationManagerCompat managerCompat;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm fired", Toast.LENGTH_SHORT).show();
        Log.d("Alarm", "Reminder Fired");

        managerCompat=NotificationManagerCompat.from(context);

        Notification notification=new NotificationCompat.Builder(context,App.Channel1_ID)
                .setSmallIcon(R.drawable.glass)
                .setContentTitle("Wash you Hand!")
                .setContentText("Its Time to Wash your Hand!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        managerCompat.notify(1,notification);

    }
}

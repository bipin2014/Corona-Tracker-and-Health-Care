package com.bipin.healthcare;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String Channel1_ID="Washing Hand";
    public static final String Channel2_ID="Drink Water";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();

    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel1=new NotificationChannel(
                    Channel1_ID,
                    "Washing Hand",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Notification for Washing Hand");
            NotificationChannel channel2=new NotificationChannel(
                    Channel1_ID,
                    "Drinking Water",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setDescription("This is Notification for Drinking Water");

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}

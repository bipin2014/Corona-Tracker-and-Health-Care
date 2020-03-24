package com.bipin.healthcare.Fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bipin.healthcare.MapActivity;
import com.bipin.healthcare.MyAlarm;
import com.bipin.healthcare.R;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class HandFragment extends Fragment {
    Button button,who;
    Boolean alarm;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hand,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button=view.findViewById(R.id.handNotification);
        who=view.findViewById(R.id.whoInfo);
        sharedPreferences=getContext().getSharedPreferences("DATA",MODE_PRIVATE);
        alarm=sharedPreferences.getBoolean("alarm",true);
        if (alarm){
            button.setText("Start Getting Notification");
        }else{
            button.setText("Cancel Getting Notification");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                alarm=sharedPreferences.getBoolean("alarm",true);
                if (alarm){
                    startAlert();
                    editor.putBoolean("alarm", false);
                    editor.apply();
                    button.setText("Cancel Getting Notification");
                }else{
                    cancelAlert();
                    editor.putBoolean("alarm", true);
                    editor.apply();
                    button.setText("Start Getting Notification");
                }
            }
        });
        who.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MapActivity.class);
                intent.putExtra("Who",true);
                startActivity(intent);
            }
        });
    }

    public void startAlert() {
        Intent intent = new Intent(getContext(), MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + (60*60 * 1000),3600000,pendingIntent);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (10 * 1000), pendingIntent);
        Toast.makeText(getContext(), "Reminder set in " + 1 + " hour interval", Toast.LENGTH_LONG).show();
    }

    public void cancelAlert(){
        Intent intent = new Intent(getContext(), MyAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(getContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sender);
        Toast.makeText(getContext(), "Your Reminder is Canceled", Toast.LENGTH_LONG).show();

    }
}

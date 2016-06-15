package com.example.mahmoudreda.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Mahmoud Reda on 2/27/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //fetch the extra value from the intent
        boolean alarm = intent.getExtras().getBoolean("alarm");

        //fetch the extra value from the intent
        long songId = intent.getExtras().getLong("id");

        //launch the ringtone
        //create an intent to the ringtone class
        Intent serIntent = new Intent(context, Ringtone.class);

        //put extra boolean value from main activity to the ringtone class
        serIntent.putExtra("alarm", alarm);

        //put extra songId from main activity to the ringtone class
        serIntent.putExtra("id", songId);

        //start the ringtone service
        context.startService(serIntent);

        //Log.e("alarm", "yaaah");

    }
}

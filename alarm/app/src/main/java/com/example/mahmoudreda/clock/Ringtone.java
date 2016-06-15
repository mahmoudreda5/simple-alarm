package com.example.mahmoudreda.clock;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by Mahmoud Reda on 3/1/2016.
 */
public class Ringtone extends Service {

    MediaPlayer mediaPlayer;
    boolean alarm, isPlaying = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Log.e("in the service", "start command");

        //fetch the extra value
        alarm = intent.getExtras().getBoolean("alarm");

        //fetch songId
        int songId = (int) intent.getExtras().getLong("id");

       // Log.e("song id", String.valueOf(songId));

        //make a notification to close the running alarm
        //set the notification manger object
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //set an intent to the main activity
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);

        //set an pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, mainIntent, 0);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.conan);

        //set the notification object
        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("the alarm is running, turn off ?!")
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.conan)
                .setLargeIcon(bitmap)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();


        if((!isPlaying && alarm) && songId == 0) {
            //create a random number between 1 and 3
            int min = 1;
            int max = 3;

            Random random = new Random();
            songId = random.nextInt(max - min + 1) + min;
            //Log.e("random number is", String.valueOf(songId));
        }

        //music playing(0 for negative- 1 for positive)-user button(0 for negative- 1 for positive)
        //0-1, 1-0, 0-0, 1-1
        if(!isPlaying && alarm){

            //play the selected ringtone
            switch (songId){
                case 1:
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),
                            R.raw.hunter);
                    mediaPlayer.start();

                    break;
                case 2:
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),
                            R.raw.snwat_el_daya3_1);
                    mediaPlayer.start();

                    break;
                case 3:
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),
                            R.raw.snwat_el_daya3_2);
                    mediaPlayer.start();

                    break;
                default:
                    //do nothing
            }


            //set the notification start
            notificationManager.notify(0, notification);

            isPlaying = true;

        }else if(isPlaying && !alarm){
            //end our ringtone
            mediaPlayer.stop();
            mediaPlayer.reset();

            isPlaying = false;

        }else if(!isPlaying && !alarm){
            //do nothing till now
        }else if(isPlaying && alarm){
            //do nothing till now
        }else {
            //nothing to do here
        }



        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        mediaPlayer.reset();

        isPlaying = false;
    }
}

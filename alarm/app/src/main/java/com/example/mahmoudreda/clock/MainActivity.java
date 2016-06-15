package com.example.mahmoudreda.clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //to make alarm manger
    AlarmManager alarmManager;
    TimePicker timePicker;
    Button b1, b2;
    PendingIntent pendingIntent;
    long songId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set time picker
        timePicker = (TimePicker) findViewById(R.id.tb);

        //set two buttons
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);

        //initialize alarm manger
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //create instance of Calender
        final Calendar calendar = Calendar.getInstance();

        //create an intent to the Alarm Receiver class
        final Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);


        Spinner spinner = (Spinner) findViewById(R.id.sp);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.ringtone_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //create an onClick listener to start the alarm
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set calender to the picked hours and minutes
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                int hours = timePicker.getCurrentHour();
                int minutes = timePicker.getCurrentMinute();

                //convert hours and minutes to strings
                String sHours = String.valueOf(hours);
                String sMinutes = String.valueOf(minutes);

                //format hours and minutes
                if(hours < 10) sHours  = "0" + sHours;
                if(minutes < 10) sMinutes = "0" + sMinutes;

                String pickedTime = sHours + ":" + sMinutes;

                //make toast with the picked time
                setAlarmText("Alarm set to " + pickedTime);

                //put extra string refer to the alarm on button
                intent.putExtra("alarm", true);

                //send id of the selected song to the broadcast
                intent.putExtra("id", songId);

                //create a pending intent
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent
                        , PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manger
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()
                , pendingIntent);



            }
        });


        //create an onClick listener to stop the alarm
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancel the alarm
                alarmManager.cancel(pendingIntent);

                //put extra string refer to the alarm off button
                intent.putExtra("alarm", false);

                //send id of the selected song to the broadcast
                intent.putExtra("id", songId);

                //stop the ringtone
                sendBroadcast(intent);


                setAlarmText("Alarm Turned Off!");
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // An item was selected. You can retrieve the selected item using
                        // parent.getItemAtPosition(pos)
                songId = id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setAlarmText(String alarmText){
        //make toast with the alarmText string
        Toast.makeText(getApplicationContext(), alarmText, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

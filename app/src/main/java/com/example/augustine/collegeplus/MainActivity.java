package com.example.augustine.collegeplus;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
 /*   public static void showNotification(Context context,Class<?> cls,String title,String content)
    {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notificationIntent = new Intent(context, cls);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(
                DAILY_REMINDER_REQUEST_CODE,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.setContentTitle(title)
                .setContentText(content).setAutoCancel(true)
                .setSound(alarmSound).setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(DAILY_REMINDER_REQUEST_CODE, notification);
    }
*/

    WeekSubjectDB today = new WeekSubjectDB(this);
    private AugustineAdapter adapter;

    public static String getCustomDateString(Date date) {
        SimpleDateFormat tmp = new SimpleDateFormat("MMMM d");

        String str = tmp.format(date);
        str = str.substring(0, 1).toUpperCase() + str.substring(1);

        if (date.getDate() > 10 && date.getDate() < 14)
            str = str + "th, ";
        else {
            if (str.endsWith("1")) str = str + "st, ";
            else if (str.endsWith("2")) str = str + "nd, ";
            else if (str.endsWith("3")) str = str + "rd, ";
            else str = str + "th, ";
        }

        tmp = new SimpleDateFormat("yyyy");
        str = str + tmp.format(date);

        return str;
    }


    public ArrayList<SubjectInfo> getDatat() {
        ArrayList<SubjectInfo> theTempList = new ArrayList<SubjectInfo>();
        ArrayList<String> current = new ArrayList<>();
        ArrayList<String> elsey = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();


        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                current = today.getthedamnList(0);
                break;
            case Calendar.TUESDAY:
                current = today.getthedamnList(1);
                break;
            case Calendar.WEDNESDAY:
                current = today.getthedamnList(2);
                break;
            case Calendar.THURSDAY:
                current = today.getthedamnList(3);
                break;
            case Calendar.FRIDAY:
                current = today.getthedamnList(4);
                break;
            default:
                current = elsey;
                break;
        }


        for (int i = 0; i < current.size(); i++) {
            SubjectInfo _current = new SubjectInfo();

            _current._id = i;
            _current._subjectname = current.get(i);
            theTempList.add(_current);

        }
        return theTempList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //CardView myCard=(CardView)findViewById(R.id.homeSubjectCard);
        RecyclerView myRecycler = (RecyclerView) findViewById(R.id.homeRecycle);

        adapter = new AugustineAdapter(this, getDatat());
        myRecycler.setAdapter(adapter);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        TextView dayText = (TextView) findViewById(R.id.dayTitle);

        Calendar calendar = Calendar.getInstance();
        Date today = new Date();

        String theDamnText = getCustomDateString(today);


        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                dayText.setText("Monday");
                break;
            case Calendar.TUESDAY:
                dayText.setText("Tuesday");
                break;
            case Calendar.WEDNESDAY:
                dayText.setText("Wednesday");
                break;
            case Calendar.THURSDAY:
                dayText.setText("Thursday");
                break;
            case Calendar.FRIDAY:
                dayText.setText("Friday");
                break;
            case Calendar.SATURDAY:
                dayText.setText("Saturday");
                break;
            case Calendar.SUNDAY:
                dayText.setText("Sunday");
                break;
        }


        TextView dateText = (TextView) findViewById(R.id.dateTitle);

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {

            case Calendar.SATURDAY:
                dateText.setText("Matiyao Be");
                break;
            case Calendar.SUNDAY:
                dateText.setText("Matiyao Be");
                break;

            default:
                dateText.setText(theDamnText);
                break;


        }

        //TODO: Add Shared Preferences
       /* SharedPreferences CurrentDay = getSharedPreferences("theCurrentDay", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = CurrentDay.edit();
        prefEditor.putString("theDate", theDamnText);


        if(!CurrentDay.contains("theCurrentDay")||(!Objects.equals(CurrentDay.getString("theCurrentDay", ""), theDamnText))){


            prefEditor.putString("theDate", theDamnText);


            prefEditor.putString("myDefString", "wowsaBowsa");

            prefEditor.commit();
        }



        /*
        String value = settings.getString("key", "");// Retrieving from the shared Preferen c s
         */


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Calendar theCalendar = Calendar.getInstance();
        theCalendar.setTimeInMillis(System.currentTimeMillis());
        theCalendar.set(Calendar.HOUR_OF_DAY, 19);
        theCalendar.set(Calendar.MINUTE, 0);
        theCalendar.set(Calendar.SECOND, 1);


        Intent notifyIntent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, theCalendar.getTimeInMillis(),// this is where the time goes
                1000 * 60 * 60 * 24, pendingIntent);


    }
    public void alarmButtonClicked(final MenuItem item)
    {
        LayoutInflater inflater2 = LayoutInflater.from(this);//LayoutInflater.from(AttendanceSettingsActivity.this);

        final View view = inflater2.inflate(R.layout.dialog_alarm, null);

        final TimePicker myTime = (TimePicker) view.findViewById(R.id.timePicker);
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());

        builder1.setView(view);
        builder1.setMessage("Set the time to notify for this subject");
        builder1.setTitle("Set Time");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        final Calendar theCalendar = Calendar.getInstance();

                        final long theFutureTime = ((myTime.getHour() - theCalendar.get(Calendar.HOUR_OF_DAY)) * 60 * 60 * 1000 + (myTime.getMinute() - theCalendar.get(Calendar.MINUTE)) * 60 * 1000);
                        final int theFutureHour = myTime.getHour() - theCalendar.get(Calendar.HOUR_OF_DAY);
                        final int theFutureMinute = theFutureHour * 60 + (myTime.getMinute() - theCalendar.get(Calendar.MINUTE));



                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.setTimeInMillis(System.currentTimeMillis());
                        myCalendar.set(Calendar.HOUR_OF_DAY, myTime.getHour());
                        myCalendar.set(Calendar.MINUTE, myTime.getMinute());
                        myCalendar.set(Calendar.SECOND, 1);


                        Intent notifyIntent = new Intent(view.getContext(), CustomNotificationReceiver.class);
                        notifyIntent.putExtra("subject", "Reminder");
                        notifyIntent.putExtra("time", System.currentTimeMillis() + theFutureTime);

                        notifyIntent.putExtra("message", "Reminder to attend this class");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast
                                (view.getContext(), 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(),// this is where the time goes
                                0, pendingIntent);


                        Toast.makeText(view.getContext(), "Class" + " due in " + String.valueOf(theFutureMinute / 60) + " hour(s) and " + String.valueOf(theFutureMinute % 60) + " minute(s)", Toast.LENGTH_SHORT).show();


                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(view.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.attend_settings) {

            Intent settingsIntent = new Intent(this, AttendanceSettingsActivity.class);
            startActivity(settingsIntent);

        } else if (id == R.id.attendance_info) {
            Intent infoIntent = new Intent(this, AttendanceInfo.class);
            startActivity(infoIntent);
        } else if (id == R.id.exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.attendance_manager) {

            Intent attendance = new Intent(this, AttendanceActivity.class);
            startActivity(attendance);


        } else if (id == R.id.nav_people) {

            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.settings) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.bug_report) {

            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

package com.example.augustine.collegeplus;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Augustine on 3/11/2018.
 */

public class AugustineAdapter extends RecyclerView.Adapter<AugustineAdapter.MyfuckingHolder> {

    ArrayList<SubjectInfo> someDamnList = new ArrayList<>();
    Context theContext;
    private LayoutInflater myInflater;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;


    public AugustineAdapter(Context context, ArrayList<SubjectInfo> data) {
        myInflater = LayoutInflater.from(context);
        someDamnList = data;
        theContext = context;


    }


   /* public void mydelete(int position) {
        someDamnList.remove(position);
        notifyItemRemoved(position);
    }*/

    @Override
    public MyfuckingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row_view = myInflater.inflate(R.layout.card_subj, parent, false);
        MyfuckingHolder holder = new MyfuckingHolder(row_view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final MyfuckingHolder holder, int position) {

        SubjectInfo current = someDamnList.get(position);
        holder.title.setText(current._subjectname);
        // holder.desc.setText(current._subjectname);

        final DBTools theDB = new DBTools(theContext);


        final String theSubjectname = holder.title.getText().toString();
        Log.e("DemErrors", theSubjectname);
        int currentAttendance = theDB.getTheDamnAttendance(theSubjectname);
        Integer currentBunk = theDB.getTheDamnBunk(theSubjectname);
        Double currentPercent;
        if ((currentAttendance + currentBunk) != 0) {
            currentPercent = Double.valueOf((currentAttendance * 100) / (currentAttendance + currentBunk));
        } else
            currentPercent = 0.0;


        String theAttendance = Integer.toString(currentAttendance);
        String theBunk = Integer.toString(currentBunk);
        String thePercent = Double.toString(currentPercent);
        Log.e("DemErrors", theSubjectname + " " + theAttendance + " " + theBunk + " " + thePercent);
        holder.attendNumber.setText(theAttendance);
        holder.bunkNumber.setText(theBunk);
        holder.percentNumber.setText(thePercent + "%");
        holder.myBar.setProgress(currentPercent.intValue());


        holder.attendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                // attend Button action
// Attend Button has been clicked
                String theSubjectname = holder.title.getText().toString();
                Log.e("DemErrors", theSubjectname);
                int currentAttendance = theDB.getTheDamnAttendance(theSubjectname) + 1;
                Integer currentBunk = theDB.getTheDamnBunk(theSubjectname);
                Double currentPercent;
                if ((currentAttendance + currentBunk) != 0) {
                    currentPercent = Double.valueOf((currentAttendance * 100) / (currentAttendance + currentBunk));
                } else
                    currentPercent = 0.0;


                String theAttendance = Integer.toString(currentAttendance);
                String theBunk = Integer.toString(currentBunk);
                String thePercent = Double.toString(currentPercent);
                Log.e("DemErrors", theSubjectname + " " + theAttendance + " " + theBunk + " " + thePercent);
                holder.attendNumber.setText(theAttendance);
                holder.bunkNumber.setText(theBunk);
                holder.percentNumber.setText(thePercent + "%");
                holder.myBar.setProgress(currentPercent.intValue());


                theDB.updateDB(theSubjectname, theAttendance, theBunk, thePercent);

            }
        });
        holder.bunkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bunk button action
                String theSubjectname = holder.title.getText().toString();
                Integer currentAttendance = theDB.getTheDamnAttendance(theSubjectname);
                Integer currentBunk = theDB.getTheDamnBunk(theSubjectname) + 1;
                Double currentPercent;
                if ((currentAttendance + currentBunk) != 0) {
                    currentPercent = Double.valueOf((currentAttendance * 100) / (currentAttendance + currentBunk));
                } else
                    currentPercent = 0.0;

                String theAttendance = Integer.toString(currentAttendance);
                String theBunk = Integer.toString(currentBunk);
                String thePercent = Double.toString(currentPercent);
                holder.attendNumber.setText(theAttendance);
                holder.bunkNumber.setText(theBunk);
                holder.percentNumber.setText(thePercent + "%");
                holder.myBar.setProgress(currentPercent.intValue());


                theDB.updateDB(theSubjectname, theAttendance, theBunk, thePercent);

            }
        });

        holder.alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                LayoutInflater inflater2 = LayoutInflater.from(theContext);//LayoutInflater.from(AttendanceSettingsActivity.this);

                final View rootView = inflater2.inflate(R.layout.dialog_alarm, null);

                final TimePicker myTime = (TimePicker) rootView.findViewById(R.id.timePicker);
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                builder1.setView(rootView);
                builder1.setMessage("Set the time to notify for this subject");
                builder1.setTitle("Set Time");
                builder1.setCancelable(true);
                final Calendar theCalendar = Calendar.getInstance();

                Date now = theCalendar.getTime();


                builder1.setPositiveButton(
                        "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                             /*   Log.e("ZIGMA",String.valueOf(myTime.getHour()));
                                Log.e("ZIGMA",String.valueOf(myTime.getMinute()));
                                Log.e("ZIGMA",String.valueOf(theCalendar.get(Calendar.HOUR_OF_DAY)));
                                Log.e("ZIGMA",String.valueOf(theCalendar.get(Calendar.MINUTE)));
*/

                                final long theFutureTime = ((myTime.getHour() - theCalendar.get(Calendar.HOUR_OF_DAY)) * 60 * 60 * 1000 + (myTime.getMinute() - theCalendar.get(Calendar.MINUTE)) * 60 * 1000);
                                final int theFutureHour = myTime.getHour() - theCalendar.get(Calendar.HOUR_OF_DAY);
                                final int theFutureMinute = theFutureHour * 60 + (myTime.getMinute() - theCalendar.get(Calendar.MINUTE));
                             /*   NotificationCompat.Builder myNotifiaction = new NotificationCompat.Builder(theContext);
                                myNotifiaction.setAutoCancel(true);
                                myNotifiaction.setContentTitle(theSubjectname);
                                myNotifiaction.setContentText("Reminder to attend this class");
                                myNotifiaction.setTicker("You have a notification");
                                myNotifiaction.setWhen(System.currentTimeMillis() + theFutureTime);
                                myNotifiaction.setSmallIcon(R.drawable.ic_launcher_foreground);
                                Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                                myNotifiaction.setSound(uri);
                                Intent theIntent = new Intent(theContext, MainActivity.class);
                                PendingIntent thePendingIntent = PendingIntent.getActivity(theContext, 0, theIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                myNotifiaction.setContentIntent(thePendingIntent);

                                Notification notificationCompat = myNotifiaction.build();
                                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(theContext);
                                managerCompat.notify(191, notificationCompat);


                              /*  Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.set(Calendar.HOUR_OF_DAY, myTime.getHour());
                                calendar.set(Calendar.MINUTE,myTime.getMinute());

// With setInexactRepeating(), you have to use one of the AlarmManager interval
// constants--in this case, AlarmManager.INTERVAL_DAY.
                                alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                        AlarmManager.INTERVAL_DAY, alarmIntent);
*/


                                Calendar myCalendar = Calendar.getInstance();
                                myCalendar.setTimeInMillis(System.currentTimeMillis());
                                myCalendar.set(Calendar.HOUR_OF_DAY, myTime.getHour());
                                myCalendar.set(Calendar.MINUTE, myTime.getMinute());
                                myCalendar.set(Calendar.SECOND, 1);


                                Intent notifyIntent = new Intent(theContext, CustomNotificationReceiver.class);
                                notifyIntent.putExtra("subject", theSubjectname);
                                notifyIntent.putExtra("time", System.currentTimeMillis() + theFutureTime);

                                notifyIntent.putExtra("message", "Reminder to attend this class");
                                PendingIntent pendingIntent = PendingIntent.getBroadcast
                                        (theContext, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                AlarmManager alarmManager = (AlarmManager) theContext.getSystemService(Context.ALARM_SERVICE);
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(),// this is where the time goes
                                        0, pendingIntent);


                                Toast.makeText(v.getContext(), theSubjectname + " due in " + String.valueOf(theFutureMinute / 60) + " hour(s) and " + String.valueOf(theFutureMinute % 60) + " minute(s)", Toast.LENGTH_SHORT).show();


                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(v.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });


                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return someDamnList.size();


    }

    public class MyfuckingHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView attendNumber;
        TextView bunkNumber;
        TextView percentNumber;
        //TextView desc;
        Button cancelButton;
        Button attendButton;
        Button bunkButton;
        ProgressBar myBar;
        ImageView alarmButton;


        public MyfuckingHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.subjectTitle);
            attendNumber = (TextView) itemView.findViewById(R.id.attendNumber);
            bunkNumber = (TextView) itemView.findViewById(R.id.bunknumber);
            percentNumber = (TextView) itemView.findViewById(R.id.percentNumber);

            //desc=(TextView)itemView.findViewById(R.id.row_desc);

            attendButton = (Button) itemView.findViewById(R.id.attendButton);
            bunkButton = (Button) itemView.findViewById(R.id.bunkButton);
            /*cancelButton = (Button) itemView.findViewById(R.id.cancelButton);
            cancelButton.setOnClickListener(this);*/

            myBar = (ProgressBar) itemView.findViewById(R.id.determinateBar);
            alarmButton = (ImageView) itemView.findViewById(R.id.alarmButton);

        }
/*
        @Override
        public void onClick(View v) {
            mydelete(getLayoutPosition());

        }*/
    }
}

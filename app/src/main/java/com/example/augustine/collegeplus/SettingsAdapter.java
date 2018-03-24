package com.example.augustine.collegeplus;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Augustine on 3/11/2018.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.MyfuckingHolder> {

    ArrayList<String> someDamnList = new ArrayList<>();
    private LayoutInflater myInflater;
    Context theContext;


    public SettingsAdapter(Context context, ArrayList<String> data) {
        myInflater = LayoutInflater.from(context);
        someDamnList = data;
        theContext=context;


    }

    @Override
    public MyfuckingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row_view = myInflater.inflate(R.layout.card_attendance_settings, parent, false);
        MyfuckingHolder holder = new MyfuckingHolder(row_view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final MyfuckingHolder holder, final int position) {


        String current = someDamnList.get(position);
        holder.TitleText.setText(current);

        final DBTools theDB = new DBTools(theContext);


        final String theSubjectname = holder.TitleText.getText().toString();
        Log.e("DemErrors", theSubjectname);
        int currentAttendance = theDB.getTheDamnAttendance(theSubjectname);
        Integer currentBunk = theDB.getTheDamnBunk(theSubjectname);
        Double currentPercent;
        if ((currentAttendance + currentBunk) != 0) {
            currentPercent = Double.valueOf((currentAttendance * 100) / (currentAttendance + currentBunk));
        } else
            currentPercent = 0.0;


        final String theAttendance = Integer.toString(currentAttendance);
        final String theBunk = Integer.toString(currentBunk);
        final String thePercent = Double.toString(currentPercent);
        Log.e("DemErrors", theSubjectname + " " + theAttendance + " " + theBunk + " " + thePercent);
        holder.attendText.setText(theAttendance);
        holder.bunkText.setText(theBunk);
        holder.percentText.setText(thePercent + "%");

        holder.vertIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                LayoutInflater inflater2 = LayoutInflater.from(theContext);//LayoutInflater.from(AttendanceSettingsActivity.this);
                final View rootView = inflater2.inflate(R.layout.dialog_empty, null);
                final DBTools mydb = new DBTools(v.getContext());
                final WeekSubjectDB theOnlyDB= new WeekSubjectDB(v.getContext());
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                builder1.setView(rootView);
                builder1.setMessage("Are you sure you want to delete this subject ?");
                builder1.setTitle("Confirm Delete");
                builder1.setCancelable(true);



                builder1.setPositiveButton(
                        "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                theDB.deleteData( theSubjectname);


                                for(int i=0;i<5;i++)
                                {
                                 theOnlyDB.deleteData(theSubjectname,i);
                                }
                                someDamnList.remove(theSubjectname);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());

                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "NO",
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

        TextView attendText;
        TextView bunkText;
        TextView percentText;
        TextView TitleText;
        ImageView vertIcon;


        public MyfuckingHolder(View itemView) {
            super(itemView);
            attendText = (TextView) itemView.findViewById(R.id.attended);
            bunkText = (TextView) itemView.findViewById(R.id.bunked);
            percentText = (TextView) itemView.findViewById(R.id.percent);
            TitleText = (TextView) itemView.findViewById(R.id.subjectTitle);
            vertIcon=(ImageView)itemView.findViewById(R.id.verticon);



        }
    }
}

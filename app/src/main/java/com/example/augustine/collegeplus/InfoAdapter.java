package com.example.augustine.collegeplus;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Augustine on 3/11/2018.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.MyfuckingHolder> {

    ArrayList<String> someDamnList = new ArrayList<>();
    private LayoutInflater myInflater;
    Context theContext;


    public InfoAdapter(Context context, ArrayList<String> data) {
        myInflater = LayoutInflater.from(context);
        someDamnList = data;
        theContext=context;


    }


    @Override
    public MyfuckingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row_view = myInflater.inflate(R.layout.card_attendance, parent, false);
        MyfuckingHolder holder = new MyfuckingHolder(row_view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final MyfuckingHolder holder, int position) {


        String current = someDamnList.get(position);
        holder.TitleText.setText(current);
        // holder.desc.setText(current._subjectname);

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
        holder.infoBar.setProgress(currentPercent.intValue());

        holder.vertIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                LayoutInflater inflater2 = LayoutInflater.from(theContext);//LayoutInflater.from(AttendanceSettingsActivity.this);
                final View rootView = inflater2.inflate(R.layout.info_edit_dialog, null);
                final DBTools mydb = new DBTools(v.getContext());
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                builder1.setView(rootView);
                builder1.setMessage("Edit  "+ theSubjectname);
                builder1.setCancelable(true);
                final EditText attendEdit=(EditText)rootView.findViewById(R.id.attendinfoedit);

                final EditText bunkEdit=(EditText)rootView.findViewById(R.id.bunkinfoedit);
                attendEdit.setText(theAttendance);
                bunkEdit.setText(theBunk);

                builder1.setPositiveButton(
                        "SAVE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String newAttend=attendEdit.getText().toString();
                               String newBunk= bunkEdit.getText().toString();

                                theDB.updateDB(theSubjectname,newAttend,newBunk,thePercent);

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
                                attendEdit.setText(newAttend);
                                bunkEdit.setText(newBunk);
                                holder.infoBar.setProgress(currentPercent.intValue());



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

        TextView attendText;
        TextView bunkText;
        TextView percentText;
        TextView TitleText;
        ImageView vertIcon;
        ProgressBar infoBar;


        public MyfuckingHolder(View itemView) {
            super(itemView);
            attendText = (TextView) itemView.findViewById(R.id.attended);
            bunkText = (TextView) itemView.findViewById(R.id.bunked);
            percentText = (TextView) itemView.findViewById(R.id.percent);
            TitleText = (TextView) itemView.findViewById(R.id.subjectTitle);
            vertIcon=(ImageView)itemView.findViewById(R.id.verticon);
            infoBar=(ProgressBar)itemView.findViewById(R.id.infoProgress);



        }
    }
}

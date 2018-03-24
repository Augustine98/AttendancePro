package com.example.augustine.collegeplus;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Augustine on 3/11/2018.
 */

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.MyfuckingHolder> {

    ArrayList<String> someDamnList = new ArrayList<>();
    private LayoutInflater myInflater;
    Context theContext;
    int dayNum;


    public DayAdapter(Context context, ArrayList<String> data,int dayNumber) {
        myInflater = LayoutInflater.from(context);
        someDamnList = data;
        theContext=context;
        dayNum=dayNumber;


    }


    @Override
    public MyfuckingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row_view = myInflater.inflate(R.layout.card_day, parent, false);
        MyfuckingHolder holder = new MyfuckingHolder(row_view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final MyfuckingHolder holder, final int position) {


        String current = someDamnList.get(position);
        holder.TitleText.setText(current);

        final WeekSubjectDB theDB = new WeekSubjectDB(theContext);


        final String theSubjectname = holder.TitleText.getText().toString();
        holder.vertIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                LayoutInflater inflater2 = LayoutInflater.from(theContext);//LayoutInflater.from(AttendanceSettingsActivity.this);
                final View rootView = inflater2.inflate(R.layout.dialog_empty, null);
                final DBTools mydb = new DBTools(rootView.getContext());
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(rootView.getContext());
                builder1.setView(rootView);
                builder1.setMessage("Are you sure you want to delete this subject ?");
                builder1.setTitle("Confirm Delete");
                builder1.setCancelable(true);



                builder1.setPositiveButton(
                        "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                theDB.deleteData( theSubjectname,dayNum);
                                someDamnList.remove(theSubjectname);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                                Log.e(theSubjectname,String.valueOf(position));
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(rootView.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
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


        TextView TitleText;
        ImageView vertIcon;



        public MyfuckingHolder(View itemView) {
            super(itemView);

            TitleText = (TextView) itemView.findViewById(R.id.subjectTitle);
            vertIcon =(ImageView)itemView.findViewById(R.id.verticon);



        }
    }
}

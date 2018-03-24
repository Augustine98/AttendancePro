package com.example.augustine.collegeplus;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Augustine on 3/9/2018.
 */

public class AttendanceSettingsActivity extends AppCompatActivity {

    final Context context = this;
    SQLiteDatabase subjectsDB = null;
    SettingsAdapter adapter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_settings);
        View view=View.inflate(this,R.layout.attendance_settings,null);//

        Toolbar settingToolBar =(Toolbar)findViewById(R.id.toolbar_settings);
        setSupportActionBar(settingToolBar);
        getSupportActionBar().setTitle("Edit Subjects");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        final DBTools mydb= new DBTools(this);




       ArrayList<String> subarray = new ArrayList<>();

       subarray=mydb.getthedamnList();


        final Context damnContext=this;


        RecyclerView infoRecycle = (RecyclerView) findViewById(R.id.subjectList);
if(subarray.size()!=0) {
    adapter = new SettingsAdapter(this, mydb.getthedamnList());
    infoRecycle.setAdapter(adapter);
    infoRecycle.setLayoutManager(new LinearLayoutManager(this));
}




        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_attendance_settings);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {



                final DBTools myownDB = new DBTools(getApplicationContext());

                LayoutInflater inflater = getLayoutInflater();
                final View rootView=inflater.inflate(R.layout.dialog_layout, null);




                AlertDialog.Builder builder1 = new AlertDialog.Builder(rootView.getContext());
                builder1.setView(rootView);
                builder1.setTitle("Enter the Subject Name");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "ADD",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                EditText subjectName =(EditText)rootView.findViewById(R.id.subject_name);

                                String subjectNameString = subjectName.getText().toString();
                                if(!Objects.equals(subjectNameString, "")) {
                                    myownDB.insertData(subjectNameString);
                                }
                                //onCreate(savedInstanceState);
                                ArrayList<String> subarray = new ArrayList<>();

                                subarray=myownDB.getthedamnList();
                                RecyclerView infoRecycle = (RecyclerView) findViewById(R.id.subjectList);

                                adapter = new SettingsAdapter(view.getContext(), mydb.getthedamnList());
                                infoRecycle.setAdapter(adapter);
                                infoRecycle.setLayoutManager(new LinearLayoutManager(view.getContext()));



                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(),"Cancelled", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();



            }
        });



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }


        return super.onOptionsItemSelected(item);
    }





    @Override
    protected void onDestroy() {

        //subjectsDB.close();

        super.onDestroy();
    }

}

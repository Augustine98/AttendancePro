package com.example.augustine.collegeplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class AttendanceInfo extends AppCompatActivity {


    InfoAdapter adapter;
    DBTools ourDB = new DBTools(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_info);


        ArrayList<String> subarray = new ArrayList<>();

        subarray = ourDB.getthedamnList();

        RecyclerView infoRecycle = (RecyclerView) findViewById(R.id.info_recycle);

        if (subarray.size() != 0) {
            adapter = new InfoAdapter(this, subarray);
            infoRecycle.setAdapter(adapter);
            infoRecycle.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}

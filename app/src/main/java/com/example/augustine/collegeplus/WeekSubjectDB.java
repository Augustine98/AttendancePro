package com.example.augustine.collegeplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Augustine on 3/11/2018.
 */

public class WeekSubjectDB extends SQLiteOpenHelper {
    String tableNames[] = {"monday", "tuesday", "wednesday", "thursday", "friday"};
    Integer n = 5;

    public WeekSubjectDB(Context context) {
        super(context, "weekdatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        for (Integer p = 0; p < n; p++) {
            String query = "CREATE TABLE " + tableNames[p] + " (subjectname TEXT )";
            db.execSQL(query);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for (int i = 0; i < n; i++) {
            String query = "DROP TABLE IF EXISTS " + tableNames[i];
            db.execSQL(query);

        }
        onCreate(db);

    }

    public void insertData(String mydamnString,int dayNumber)
    {
        SQLiteDatabase crap =this.getWritableDatabase();
        if(crap==null)
        {
            String query = "CREATE TABLE " + tableNames[dayNumber] + " (subjectname TEXT )";
            crap.execSQL(query);
        }
        ContentValues values = new ContentValues();
        values.put("subjectname",mydamnString);

        // Inserting Row
        crap.insert(tableNames[dayNumber], null, values);
        crap.close();
    }

    public void deleteData(String theSubject,int dayNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableNames[dayNumber], "subjectname" + " = ?", new String[]{theSubject});
        db.close();
    }


    public ArrayList<String> getthedamnList(int dayNumber){
        ArrayList<String> myfuckingList =new ArrayList<String>();

        SQLiteDatabase myfuckingdatabase= this.getReadableDatabase();

        String myfuckingQuery="SELECT * FROM "+tableNames[dayNumber];
        Cursor cursor = myfuckingdatabase.rawQuery(myfuckingQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                myfuckingList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        myfuckingdatabase.close();

        // returning lables

        return myfuckingList;


//MDRXB450
    }

    public void deleteTable(int dayNumber)
    {
        SQLiteDatabase db= this.getReadableDatabase();

        db.execSQL("delete from "+ tableNames[dayNumber]);
    }




}

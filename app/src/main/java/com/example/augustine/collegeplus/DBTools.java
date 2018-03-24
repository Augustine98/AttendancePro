package com.example.augustine.collegeplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Augustine on 3/3/2018.
 */

public class DBTools extends SQLiteOpenHelper {

    String FieldValues[] = {"subjectname", "attended", "bunked", "percent"};
    Integer n = 4;
    String dataBaseName = "subjectsDB";

    public DBTools(Context context) {
        super(context, "subjectsDB", null, 1);
        //dataBaseName = name;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + dataBaseName + " (subjectname TEXT PRIMARY KEY , attended TEXT, bunked TEXT, percent TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS " + dataBaseName;
        db.execSQL(query);
        onCreate(db);

    }

    /* void insertData(HashMap<String, String> queryValues) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (int i = 0; i < 1; i++) {
            values.put(FieldValues[i], queryValues.get((FieldValues[i])));
        }
        db.insert(dataBaseName, null, values);
        db.close();


    }*/
    public void insertData(String mydamnString) {
        SQLiteDatabase crap = this.getWritableDatabase();
        if (crap == null) {
            String query = "CREATE TABLE " + dataBaseName + " (subjectname TEXT PRIMARY KEY , attended TEXT, bunked TEXT, percent TEXT)";
            crap.execSQL(query);
        }
        ContentValues values = new ContentValues();
        values.put("subjectname", mydamnString);
        values.put("attended", "0");
        values.put("bunked", "0");
        values.put("percent", "0.0");

        // Inserting Row
        crap.insert("subjectsDB", null, values);
        crap.close();
    }

    public void deleteData(String theSubject) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("subjectsDB", "subjectname" + " = ?", new String[]{theSubject});
        db.close();
    }


    public ArrayList<HashMap<String, String>> getTheDamnData() {


        ArrayList<HashMap<String, String>> subjectArrayList;

        subjectArrayList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM subjectsDB";


        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                HashMap<String, String> subjectMap = new HashMap<String, String>();
                for (Integer i = 0; i < n; i++) {
                    subjectMap.put(FieldValues[i], cursor.getString(i));
                }
                subjectArrayList.add(subjectMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row

            cursor.close();
        }

        return subjectArrayList;
    }// Mydialog.onSrehauhe(


    public ArrayList<String> getthedamnList() {
        ArrayList<String> myfuckingList = new ArrayList<String>();

        SQLiteDatabase myfuckingdatabase = this.getReadableDatabase();

        String myfuckingQuery = "SELECT * FROM subjectsDB";
        Cursor cursor = myfuckingdatabase.rawQuery(myfuckingQuery, null);

        // looping through all rows and adding to list
        if (cursor!=null&&cursor.moveToFirst()) {
            do {
                myfuckingList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        myfuckingdatabase.close();

        // returning lables

        return myfuckingList;


    }


    public void updateDB(String theSubject, String theNewAttendance, String theNewBunk, String theNewPercent) {
        SQLiteDatabase database = this.getWritableDatabase();


        database.delete(dataBaseName, "subjectname" + " = ?", new String[]{theSubject});


        ContentValues values = new ContentValues();
        values.put("subjectname", theSubject);
        values.put("attended", theNewAttendance);
        values.put("bunked", theNewBunk);
        values.put("percent", theNewPercent);

        // Inserting Row
        database.insert("subjectsDB", null, values);
        database.close();


    }

    public Integer getTheDamnAttendance(String theSubject) {

        SQLiteDatabase myFuckingDatabase = this.getReadableDatabase();
        String myFuckingQuery = "SELECT * FROM subjectsDB WHERE subjectname=?";
        Integer myDamnInteger = 0;
        Cursor cursor = myFuckingDatabase.rawQuery(myFuckingQuery, new String[]{theSubject});

        if (cursor!=null&&cursor.moveToFirst()) {
            do {
                //myDamnInteger = Integer.getInteger(cursor.getString(1));

                myDamnInteger = cursor.getInt(cursor.getColumnIndex("attended"));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return myDamnInteger;


    }

    public Integer getTheDamnBunk(String theSubject) {

        SQLiteDatabase myFuckingDatabase = this.getReadableDatabase();
        String myFuckingQuery = "SELECT * FROM subjectsDB WHERE subjectname=?";
        Integer myDamnInteger = 0;
        Cursor cursor = myFuckingDatabase.rawQuery(myFuckingQuery, new String[]{theSubject});

        if (cursor!=null&&cursor.moveToFirst()) {
            do {
                myDamnInteger = cursor.getInt(cursor.getColumnIndex("bunked"));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return myDamnInteger;


    }

    public Double getTheDamnPercent(String theSubject) {

        SQLiteDatabase myFuckingDatabase = this.getReadableDatabase();
        String myFuckingQuery = "SELECT * FROM subjectsDB WHERE subjectname=?";
        Double myDamnDouble = 0.0;
        Cursor cursor = myFuckingDatabase.rawQuery(myFuckingQuery, new String[]{theSubject});

        if (cursor!=null&&cursor.moveToFirst()) {
            do {
                myDamnDouble = cursor.getDouble(cursor.getColumnIndex("double"));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return myDamnDouble;


    }


    public void deleteTable() {
        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL("delete from " + dataBaseName);
    }


}
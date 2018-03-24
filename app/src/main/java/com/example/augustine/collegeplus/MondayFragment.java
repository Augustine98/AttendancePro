package com.example.augustine.collegeplus;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Augustine on 3/7/2018.
 */

public class MondayFragment extends Fragment {

    DayAdapter adapter;


    ArrayList<String> MondaySubjects = new ArrayList<>();


    public void refresh(){
        FragmentTransaction ft= getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();



    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
        final String[] clickedString = {""};
        final ViewGroup mycontainer = container;
        final LayoutInflater myinflater = inflater;


        View rootView = inflater.inflate(R.layout.monday, container, false);
        final WeekSubjectDB myWeekDB = new WeekSubjectDB(rootView.getContext());
        MondaySubjects = myWeekDB.getthedamnList(0);


        RecyclerView infoRecycle = (RecyclerView) rootView.findViewById(R.id.mondayList);

        adapter = new DayAdapter(rootView.getContext(), myWeekDB.getthedamnList(0),0);
        infoRecycle.setAdapter(adapter);
        infoRecycle.setLayoutManager(new LinearLayoutManager(rootView.getContext()));



        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabmonday);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DBTools mydb = new DBTools(getContext());
                ArrayList<String> subarray;
                subarray = mydb.getthedamnList();



                if(subarray.size()==0) {
                    Snackbar.make(view, "Add Subjects first!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    LayoutInflater inflater2 = getLayoutInflater();//LayoutInflater.from(AttendanceSettingsActivity.this);
                    final View rootView = inflater2.inflate(R.layout.edit_dialog, null);

                    Spinner myspinner = (Spinner) rootView.findViewById(R.id.spinner_edit);

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item, subarray);
                    myspinner.setAdapter(spinnerAdapter);
                    myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            clickedString[0] = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(rootView.getContext());
                    builder1.setView(rootView);
                    builder1.setTitle("Choose the Subject");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "ADD",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    if (!clickedString[0].equals("")) {
                                        WeekSubjectDB WeekDB = new WeekSubjectDB(rootView.getContext());
                                        String temp = clickedString[0];


                                        WeekDB.insertData(temp, 0);
                                        MondaySubjects.add(temp);


                                    }
                                    MondaySubjects = myWeekDB.getthedamnList(0);

                                    View rootView = myinflater.inflate(R.layout.monday, mycontainer, false);

                                    RecyclerView infoRecycle = (RecyclerView) rootView.findViewById(R.id.mondayList);

                                    adapter = new DayAdapter(rootView.getContext(), myWeekDB.getthedamnList(0),0);

                                    infoRecycle.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
                                    infoRecycle.setAdapter(adapter);
                                    dialog.cancel();
                                 refresh();

                                }
                            });

                    builder1.setNegativeButton(
                            "CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();


                                  /*  WeekSubjectDB WeekDB = new WeekSubjectDB(rootView.getContext());

                                    String subjectNameString = clickedString[0];
                                    WeekDB.deleteData(subjectNameString,0);
                                    ArrayList<String> subarray = new ArrayList<>();


                                    MondaySubjects = myWeekDB.getthedamnList(0);
                                    RecyclerView infoRecycle = (RecyclerView) rootView.findViewById(R.id.mondayList);

                                    adapter = new DayAdapter(rootView.getContext(), myWeekDB.getthedamnList(0),0);
                                    infoRecycle.setAdapter(adapter);
                                    infoRecycle.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

*/


                                    dialog.cancel();
                                }
                            });



                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
            }
        });


        return rootView;


    }


    public ArrayList<String> getMondaySubjects() {
        return MondaySubjects;
    }
}

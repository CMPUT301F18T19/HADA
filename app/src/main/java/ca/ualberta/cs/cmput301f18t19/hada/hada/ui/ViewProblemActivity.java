/*
 * CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 11/11/18 12:41 PM
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 * Last modified 11/11/18 12:41 PM
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.media.Image;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;

/**
 * Activity for viewing a specific problem from a patient's list.
 *
 * @author Jason, Austin
 * @see Problem
 * @see Patient
 */
public class ViewProblemActivity extends AppCompatActivity {

    private ListView recordsList;
    private ArrayList<Record> records;
    private Problem oldProblem;
    private int position;

    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);

        Intent intent = getIntent();
        position = (int) intent.getSerializableExtra("Position");
        String LoggedInUser = LoggedInSingleton.getInstance().getLoggedInID();
        final ArrayList<Problem> problems = new ProblemController().getProblemList(LoggedInUser);
        oldProblem = problems.get(position);
        records = oldProblem.getRecords();

        recordsList = (ListView) findViewById(R.id.viewProblemList);
        ImageButton viewMap = (ImageButton) findViewById(R.id.viewProblemMapButton);
        ImageButton addRecord = (ImageButton) findViewById(R.id.viewProblemAddRecordButton);
        ImageButton viewSlideshow = (ImageButton) findViewById(R.id.viewProblemSlideshowButton);

        recordsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int recordPosition, long id) {
                Intent intent = new Intent(ViewProblemActivity.this, ViewRecordActivity.class);
                intent.putExtra("ProblemPosition", position);
                intent.putExtra("RecordPosition", recordPosition);
                //record = records.get(recordPosition);
                //intent.putExtra("Records", records);
                startActivity(intent);

            }

        });

        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Go to map
                 */

            }
        });

        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProblemActivity.this, AddRecordActivity.class);
                intent.putExtra("problem", position);
                startActivity(intent);

            }
        });

        viewSlideshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Go to slideshow
                 */

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //Displays the list of records

        /**
         * As 'position' is taken from the previous intent as a way to get the record list, the
         * 'position' variable can't be used again after a new record is added. PatientController
         * can return the problem list from the user's id, but there is currently no way to return
         * the recordlist for a problem from the RecordController
         *
         *
         */

        ListView listView = findViewById(R.id.viewProblemList);
        ArrayList<Record> records = oldProblem.getRecords();

        ArrayAdapter<Record> recordArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        listView.setAdapter(recordArrayAdapter);
        recordArrayAdapter.notifyDataSetChanged();

    }

}

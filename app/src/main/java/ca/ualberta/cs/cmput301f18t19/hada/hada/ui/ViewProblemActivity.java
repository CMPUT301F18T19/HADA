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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import android.widget.TextView;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * Activity for viewing a specific problem from a patient's list.
 *
 * @author Jason, Austin
 * @see Problem
 * @see Patient
 */
public class ViewProblemActivity extends AppCompatActivity {
    String LoggedInUser = LoggedInSingleton.getInstance().getLoggedInID();
    private ListView recordsList;
    private Problem oldProblem;
    private int position;

    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);
        Intent intent = getIntent();
        position = (int) intent.getSerializableExtra("Position");

        TextView titleText = (TextView) findViewById(R.id.patientProblemCommentTitle);
        recordsList = (ListView) findViewById(R.id.viewProblemList);
        ImageButton viewMap = (ImageButton) findViewById(R.id.viewProblemMapButton);
        ImageButton addRecord = (ImageButton) findViewById(R.id.viewProblemAddRecordButton);
        ImageButton viewSlideshow = (ImageButton) findViewById(R.id.viewProblemSlideshowButton);

        //Setting title to display the problem title
        titleText.setText(new UserController().getPatient(LoggedInSingleton.getInstance().getLoggedInID())
                .getProblem(position).getTitle());
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

        ListView listView = findViewById(R.id.viewProblemList);
        ArrayList<Problem> problems = new ProblemController().getProblemList(LoggedInUser);
        oldProblem = problems.get(position);
        ArrayList<Record> records = oldProblem.getRecords();
        ArrayAdapter<Record> recordArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        listView.setAdapter(recordArrayAdapter);
        recordArrayAdapter.notifyDataSetChanged();

    }

}

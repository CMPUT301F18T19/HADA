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
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * Activity for viewing a specific problem from a patient's list.
 *
 * @author Jason, Austin, Joseph Potentier
 * @see Problem
 * @see Patient
 */
public class ViewProblemActivity extends AppCompatActivity {
    /**
     * The Logged in user.
     */

    private String problemFileId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);
        Intent intent = getIntent();
        problemFileId = intent.getStringExtra("problemFileId");

        TextView titleText = findViewById(R.id.patientProblemCommentTitle);

        //Setting title to display the problem title
        final Problem problem = new ProblemController().getProblem(problemFileId);
        titleText.setText(problem.getTitle());


        //Will go to a map view of all record locations
        ImageButton viewMap = findViewById(R.id.viewProblemMapButton);
        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Go to map
                 */

            }
        });

        //Goes to AddRecordActivity
        ImageButton addRecord = findViewById(R.id.viewProblemAddRecordButton);
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProblemActivity.this, AddRecordActivity.class);
                intent.putExtra("problemFileId", problem.getFileId());
                startActivity(intent);

            }
        });

        //Will open SlideShowMode
        ImageButton viewSlideshow = findViewById(R.id.viewProblemSlideshowButton);
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
            ListView recordsList = findViewById(R.id.viewProblemRecordsList);

            final ArrayList<Record> records = new RecordController().getRecordList(problemFileId);
            ArrayAdapter<Record> recordArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
            recordsList.setAdapter(recordArrayAdapter);
            recordArrayAdapter.notifyDataSetChanged();

            //Goes to ViewRecordActivity
            recordsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ViewProblemActivity.this, ViewRecordActivity.class);
                    intent.putExtra("recordFileId", records.get(position).getFileId());
                    startActivity(intent);
                }
            });

            //Goes to EditRecordActivity
            recordsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    //Go to EditRecordActivity
                    return true;
                }
            });
    }

}
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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;

/**
 * Activity for viewing a specific problem from a patient's list.
 *
 * @author Jason, Austin
 * @see Problem
 * @see Patient
 */
public class ViewProblemActivity extends AppCompatActivity {

    private ListView problemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);

        Intent intent = getIntent();
        int position = (int) intent.getSerializableExtra("Position");
        String LoggedInUser = LoggedInSingleton.getInstance().getLoggedInID();
        final ArrayList<Problem> problems = new ProblemController().getProblemList(LoggedInUser);
        final Problem oldProblem = problems.get(position);

        problemsList = (ListView) findViewById(R.id.viewPatientProblemsList);
        ImageButton viewMap = (ImageButton) findViewById(R.id.viewProblemMapButton);
        ImageButton addRecord = (ImageButton) findViewById(R.id.viewProblemAddRecordButton);
        ImageButton viewSlideshow = (ImageButton) findViewById(R.id.viewProblemSlideshowButton);

        problemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =  new Intent(ViewProblemActivity.this, ViewRecordActivity.class);
                Problem selected_problem = problems.get(position);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}

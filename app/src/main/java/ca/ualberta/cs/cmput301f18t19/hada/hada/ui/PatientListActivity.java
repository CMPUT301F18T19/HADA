/*
 * CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 11/11/18 12:42 PM
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 * Last modified 11/11/18 12:41 PM
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;

/**
 * The type Patient list activity.
 */
public class PatientListActivity extends AppCompatActivity {
    /**
     * The Logged in user.
     */
    String loggedInUser = LoggedInSingleton.getInstance().getLoggedInID();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        //Set title at top to custom name
        TextView titleTextView = findViewById(R.id.patientListTextView);
        String titleText = loggedInUser + "'s Patients";
        titleTextView.setText(titleText);



        //Opens addPatient activity
        FloatingActionButton addPatient = findViewById(R.id.patientListFloatingActionButton);
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientListActivity.this, AddPatientActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        //Displays the list of problems
        ListView listView = findViewById(R.id.patientListListView);
        ArrayList<Patient> patients = new UserController().getPatientList(loggedInUser);
        ArrayAdapter<Patient> patientArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, patients);
        listView.setAdapter(patientArrayAdapter);
        patientArrayAdapter.notifyDataSetChanged();
    }




}

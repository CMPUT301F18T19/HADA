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
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;

/**
 * Activity for viewing list of patients.
 *
 * @author Joe
 * @see ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider
 * @see Patient
 *
 */
public class PatientListActivity extends AppCompatActivity {
    /**
     * The userID of the user currently logged in.
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

        //Opens EditUserSettings activity
        ImageButton settings = findViewById(R.id.patientListUserSettingsButton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientListActivity.this, EditUserSettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        //Displays the list of problems
        ListView listView = findViewById(R.id.patientListListView);
        final ArrayList<Patient> patients = new UserController().getPatientList();
        ArrayAdapter<Patient> patientArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, patients);
        listView.setAdapter(patientArrayAdapter);
        patientArrayAdapter.notifyDataSetChanged();

        //Goes to ViewPatientProblemsActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PatientListActivity.this, ViewPatientProblemsActivity.class);
                String patientUserId = patients.get(position).getUserID();
                intent.putExtra("patientUserId", patientUserId);
                startActivity(intent);
            }
        });
    }




}

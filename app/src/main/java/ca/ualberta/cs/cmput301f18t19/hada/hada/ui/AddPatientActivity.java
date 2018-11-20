/*
 * CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 11/12/18 1:39 PM
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 * Last modified 11/12/18 1:11 PM
 */



package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;


/**
 * Activity responsible for enabling a Care Provider to add a patient to their list of patients
 * via their UserID.
 *
 * @author AndersJ
 * @version 1.0
 * @see , PatientListActivityCareProvider, Patient
 */
public class AddPatientActivity extends AppCompatActivity {
    /**
     * The Logged in user.
     */
    String loggedInUser = LoggedInSingleton.getInstance().getLoggedInID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        //Set title at top to custom name
        TextView titleTextView = findViewById(R.id.addPatientTitle);
        String titleText = "Adding patients to " + loggedInUser;
        titleTextView.setText(titleText);


        //TODO Check if patient is already on the CP's list of patients
        //Save button
        Button saveButton = findViewById(R.id.addPatientSavePatientButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText testInput = findViewById(R.id.addPatientTextInput);
                String userId = testInput.getText().toString();
                Boolean success = new UserController().addPatientToCareProvider(userId);
                if(success){
                    Toast.makeText(AddPatientActivity.this, "Patient Added", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(AddPatientActivity.this, "Patient does not exist. Check spelling?", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}

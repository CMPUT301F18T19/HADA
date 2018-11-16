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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ElasticSearchUserController;

public class NewUserActivity extends AppCompatActivity {

    private EditText username;
    private EditText phonenumber;
    private EditText email;
    private ArrayList<Patient> patientList = new ArrayList<Patient>();
    private ArrayList<CareProvider> careProviderList = new ArrayList<CareProvider>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        username = (EditText) findViewById(R.id.newUserEnterUsername);
        phonenumber = (EditText) findViewById(R.id.newUserEnterPhone);
        email = (EditText) findViewById(R.id.newUserEnterEmail);
        Button frontImage = findViewById(R.id.newUserAddFrontImageButton);
        Button backImage = findViewById(R.id.newUserAddBackImageButton);
        Button confirm = findViewById(R.id.newUserConfirm);
        Button cancel = findViewById(R.id.newUserCancel);
        final RadioButton patient = findViewById(R.id.newUserPatientRadioButton);
        final RadioButton doctor = findViewById(R.id.newUserDoctorRadioButton);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean newPatient = false;
                boolean newCareProvider = false;
                if(patient.isChecked()){
                    newPatient = true;
                }
                if(doctor.isChecked()){
                    newCareProvider = true;
                }
                if(!newPatient && !newCareProvider){
                    //TODO: Add toast message to strings xml (prevent hardcoding)
                    Toast.makeText(NewUserActivity.this,
                            "Please select a user type.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(newPatient){

                        String patientID = username.getText().toString();
                        String patientPhone = phonenumber.getText().toString();
                        String patientEmail = email.getText().toString();
                        Patient patient = new Patient(patientID, patientPhone, patientEmail);
                        new ElasticSearchUserController.AddPatientTask().execute(patient);



                    }
                }
            }
        });

    }
}

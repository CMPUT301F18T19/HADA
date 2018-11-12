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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;

public class NewUserActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText name;
    private ArrayList<Patient> patientList = new ArrayList<Patient>();
    private ArrayList<CareProvider> careProviderList = new ArrayList<CareProvider>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        username = (EditText) findViewById(R.id.newUserEnterUsername);
        password = (EditText) findViewById(R.id.newUserEnterPassword);
        name = (EditText) findViewById(R.id.newUserEnterName);
        Button frontImage = findViewById(R.id.newUserAddFrontImageButton);
        Button backImage = findViewById(R.id.newUserAddBackImageButton);
        Button confirm = findViewById(R.id.newUserConfirm);
        Button cancel = findViewById(R.id.newUserCancel);
        RadioButton patient = findViewById(R.id.newUserPatientRadioButton);
        RadioButton doctor = findViewById(R.id.newUserDoctorRadioButton);



    }
}

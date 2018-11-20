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
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ESUserManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;

/**
 * Activity for adding new users (patient or care provider).
 * @author Alex, Anders Johnson
 * @version 1.1
 * @see Patient, CareProvider
 * @date 2018-11-15
 * @TODO Needs to be able to save to cache. Currently saves directly to ElasticSearch.
 */
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

        username = findViewById(R.id.newUserEnterUsername);
        phonenumber = findViewById(R.id.newUserEnterPhone);
        email = findViewById(R.id.newUserEnterEmail);
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

                String userID = username.getText().toString();
                String userPhone = phonenumber.getText().toString();
                String userEmail = email.getText().toString();

                if(patient.isChecked()){
                    newPatient = true;
                }
                if(doctor.isChecked()){
                    newCareProvider = true;
                }

                if(!newPatient && !newCareProvider){
                    Toast.makeText(NewUserActivity.this,
                            getString(R.string.NewUserActivity_SelectUserType), Toast.LENGTH_SHORT).show();
                }
                else if(userID.equals("") || userPhone.equals("") || userEmail.equals("")){
                    Toast.makeText(NewUserActivity.this,
                            getString(R.string.NewUserActivity_EnterAllFields),
                            Toast.LENGTH_SHORT).show();
                }
                else if(userID.length() < 8){
                    Toast.makeText(NewUserActivity.this, getString(R.string.NewUserActivity_UserIdMin), Toast.LENGTH_SHORT).show();
                }
                else if(userID.contains(" ")){
                    Toast.makeText(NewUserActivity.this, getString(R.string.NewUserActivity_UserIdSpaces), Toast.LENGTH_SHORT).show();
                }
                else if(new UserController().userExists(userID)){
                    Toast.makeText(NewUserActivity.this,getString(R.string.NewUserActivity_userid_in_use), Toast.LENGTH_SHORT).show();
                }
                else{
                    if(newPatient){
                        new UserController().addPatient(userID, userPhone, userEmail);
                        Toast.makeText(NewUserActivity.this, getString(R.string.NewUserActivity_PatientSaved), Toast.LENGTH_SHORT).show();
                        finish();

                    }
                    if(newCareProvider){
                        new UserController().addCareProvider(userID, userPhone, userEmail);
                        Toast.makeText(NewUserActivity.this, getString(R.string.NewUserActivity_CareProviderSaved), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}

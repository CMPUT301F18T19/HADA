/*
 * CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 10/27/18 12:42 PM
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 * Last modified 11/11/18 10:08 AM
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ElasticSearchUserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ListManagerPatient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private EditText usernameInfo;
    private ArrayList<Patient> patientList = new ArrayList<Patient>();
    private ArrayList<CareProvider> careProviderList = new ArrayList<CareProvider>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInfo = (EditText) findViewById(R.id.mainActivityUsernameText);
        Button patientLogin = findViewById(R.id.mainActivityPatientLogin);
        Button careProviderLogin = findViewById(R.id.mainActivityDoctorLogin);
        Button createUser = findViewById(R.id.mainActivityCreateUser);


        patientLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String username = usernameInfo.getText().toString();
                ElasticSearchUserController.GetPatientTask patientTask = new ElasticSearchUserController.GetPatientTask();
                patientTask.execute(username);

                try {
                    Patient patient = patientTask.get();
                    if(patient != null){
                        Log.d("Username logged in", patient.getUserID());
                        Intent intent = new Intent(MainActivity.this, ProblemListActivity.class);
                        intent.putExtra("User that is logged in", patient.getUserID());
                        startActivity(intent);
                    }
                    else{Toast.makeText(MainActivity.this, "Username does not exist. Create a new user instead!?", Toast.LENGTH_SHORT).show();}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        });

        careProviderLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String username = usernameInfo.getText().toString();
                ElasticSearchUserController.GetCareProviderTask careProviderTask = new ElasticSearchUserController.GetCareProviderTask();
                careProviderTask.execute(username);

                try {
                    CareProvider careProvider = careProviderTask.get();
                    if(careProvider != null){
                        Log.d("Username logged in", careProvider.getUserID());
                        Intent intent = new Intent(MainActivity.this, PatientListActivity.class);
                        intent.putExtra("User that is logged in", careProvider.getUserID());
                        startActivity(intent);
                    }
                    else{Toast.makeText(MainActivity.this, "Username does not exist. Create a new user instead!?", Toast.LENGTH_SHORT).show();}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

        });

        createUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, NewUserActivity.class);
                startActivity(intent);
            }
        });

    }

}
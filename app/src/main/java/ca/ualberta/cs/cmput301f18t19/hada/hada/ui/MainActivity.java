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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ESUserManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private EditText usernameInfo;
    private ArrayList<Patient> patientList = new ArrayList<Patient>();
    private ArrayList<CareProvider> careProviderList = new ArrayList<CareProvider>();
    private Patient patient;
    private CareProvider careProvider;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInfo = (EditText) findViewById(R.id.mainActivityUsernameText);
        final Button patientLogin = findViewById(R.id.mainActivityPatientLogin);
        Button careProviderLogin = findViewById(R.id.mainActivityDoctorLogin);
        Button createUser = findViewById(R.id.mainActivityCreateUser);



        patientLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String username = usernameInfo.getText().toString();
                Patient patient = new UserController().getPatient(username);

                if(patient != null){
                    //Sets the user to be patient and it's userId
                    Log.d("Username logged in", patient.getUserID());
                    LoggedInSingleton instance = LoggedInSingleton.getInstance();
                    instance.setLoggedInID(patient.getUserID());
                    instance.setIsCareProvider(false);

                    Intent intent = new Intent(MainActivity.this, ProblemListActivity.class);
                    startActivity(intent);
                }
                else{Toast.makeText(MainActivity.this, "Username does not exist. Create a new user instead!?", Toast.LENGTH_SHORT).show();}

            }
        });

        careProviderLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String username = usernameInfo.getText().toString();
                ESUserManager.GetCareProviderTask careProviderTask = new ESUserManager.GetCareProviderTask();
                careProviderTask.execute(username);

                try {
                    CareProvider careProvider = careProviderTask.get();

                    if(careProvider != null){
                        //Sets the user to be CP and it's userId
                        Log.d("Username logged in", careProvider.getUserID());
                        LoggedInSingleton instance = LoggedInSingleton.getInstance();
                        instance.setLoggedInID(careProvider.getUserID());
                        instance.setIsCareProvider(true);

                        Intent intent = new Intent(MainActivity.this, PatientListActivity.class);
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
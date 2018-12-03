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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.BuildConfig;
import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.SyncManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESUserManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ContextSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.User;

/**
 * Main Acitivity of app. Login screen for users.
 *
 * @author Joe, Austin
 * @see UserController
 */
public class MainActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        installListener();
        final EditText usernameInfo = findViewById(R.id.mainActivityUsernameText);
        Button patientLogin = findViewById(R.id.mainActivityPatientLogin);
        //Button careProviderLogin = findViewById(R.id.mainActivityDoctorLogin);
        Button createUser = findViewById(R.id.mainActivityCreateUser);
        final Switch shortCode = findViewById(R.id.mainActivityShortCodeRadioButton);


        patientLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = usernameInfo.getText().toString();
                if(shortCode.isChecked()){
                    loginShortCode(username);
                }else{
                    loginUser(username);
                }

            }
        });

        createUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, NewUserActivity.class);
                startActivity(intent);
            }
        });
        
        shortCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shortCode.isChecked()){
                    usernameInfo.setHint(R.string.generic_shortcode);
                }else{
                    usernameInfo.setHint(R.string.enter_username);
                }
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        ContextSingleton.getInstance().setContext(this);
    }
    @Override
    protected void onDestroy(){
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }


    private void installListener() {
        //based on https://stackoverflow.com/a/3142590

        if (broadcastReceiver == null) {

            broadcastReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    Bundle extras = intent.getExtras();

                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");

                    NetworkInfo.State state = info.getState();
                    Log.d("InternalBroadcastReceiver", info.toString() + " "
                            + state.toString());

                    if (state == NetworkInfo.State.CONNECTED) {
                        //INSERT SYNC FUNCTION HERE
                        new SyncManager().syncDB2ES();
                        Log.d("networktest", "Network connected!");

                    } else {

                        Log.d("networktest", "Network disconnected!");

                    }

                }
            };

            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    public void loginUser(String username){
        Boolean isPatient = new UserController().isPatient(username);
        if(!new UserController().userExists(username)){
            //Case where user does not exist
            Toast.makeText(MainActivity.this, getString(R.string.login_error_message), Toast.LENGTH_SHORT).show();
        }
        else {
            if (isPatient) {
                Patient patient = new UserController().getPatient(username);
                //Sets the user to be patient and it's userId
                Log.d("Username logged in", patient.getUserID());
                LoggedInSingleton instance = LoggedInSingleton.getInstance();
                instance.setLoggedInID(patient.getUserID());
                instance.setIsCareProvider(false);
                Intent intent = new Intent(MainActivity.this, ProblemListActivity.class);
                startActivity(intent);

            } else {
                //Care Provider Login
                CareProvider careProvider = new UserController().getCareProvider(username);

                //Sets the user to be CP and it's userId
                Log.d("Username logged in", careProvider.getUserID());
                LoggedInSingleton instance = LoggedInSingleton.getInstance();
                instance.setLoggedInID(careProvider.getUserID());
                instance.setIsCareProvider(true);
                Intent intent = new Intent(MainActivity.this, PatientListActivity.class);
                startActivity(intent);

            }

        }
    }

    public void loginShortCode(String shortCode){
        Boolean shortCodeExists = new UserController().shortCodeExists(shortCode);
        if(!shortCodeExists){
            Toast.makeText(this, "Shortcode not valid. Try again.", Toast.LENGTH_SHORT).show();
        }else {
            Patient patient = new UserController().getPatientWithShortCode(shortCode);
            LoggedInSingleton instance = LoggedInSingleton.getInstance();
            instance.setLoggedInID(patient.getUserID());
            instance.setIsCareProvider(false);
            Intent intent = new Intent(MainActivity.this, ProblemListActivity.class);
            startActivity(intent);
        }
    }
}
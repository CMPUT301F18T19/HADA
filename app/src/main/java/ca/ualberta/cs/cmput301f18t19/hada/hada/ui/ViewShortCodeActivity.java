/*
 *  CMPUT 301 - Fall 2018
 *
 *  ViewShortCodeActivity.java
 *
 *  12/1/18 10:40 AM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ContextSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;

/**
 * Activity for viewing the ShortCode of a patient.
 *
 * @author AndersJ
 * @version 1.0
 * @see Patient
 */
public class ViewShortCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_short_code);

        TextView shortCodeView = findViewById(R.id.viewShortCodeMainTextView);

        Patient patient = new UserController().getPatient(LoggedInSingleton.getInstance().getLoggedInID());
        String shortCode = patient.getShortCode();
        shortCodeView.setText(shortCode);

    }
    @Override
    protected void onResume(){
        super.onResume();
        ContextSingleton.getInstance().setContext(this);
    }
}

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

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;


/**
 * Activity responsible for enabling a Care Provider to add a patient to their list of patients
 * via their UserID.
 *
 * @author AndersJ
 * @see PatientListActivity, CareProvider, Patient
 * @version 1.0
 */
public class AddPatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
    }
}

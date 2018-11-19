/* CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 2018-10-29
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */
package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.utility.Listener;

/**
 * @author Joseph Potentier, Chris Penner
 *
 * @version 0.1
 *
 */
public class UserController {

    /*
    This class should do any logic that is related to editing patient in any manor.
    This can include but is not limited to:
    - Adding/removing patients to ES/local storage by calling on the ESUserManager
    - Editing patient contact info
    - Adding patients to the CareProviders list of patients

    If you don't see a function that does what you want, feel free to add it. The
      activity should not be creating patients etc. We should pass the info into here and
      create the patient then pass it to ESUserManager.

    */

    public UserController(){}


    //Adds user types to ES and/or memory
    public void addPatient(String userID, String userPhone, String userEmail){
        Patient patient = new Patient(userID, userPhone, userEmail);
        new ESUserManager.AddPatientTask().execute(patient);
    }

    public void addCareProvider(String userID, String userPhone, String userEmail){
        CareProvider careProvider = new CareProvider(userID, userPhone, userEmail);
        new ESUserManager.AddCareProviderTask().execute(careProvider);
    }


    //Retrieves Patient or Care Provider
    public Patient getPatient(String userId){
        ESUserManager.GetPatientTask patientTask = new ESUserManager.GetPatientTask();
        patientTask.execute(userId);
        try {
            Patient patient = patientTask.get();
            return patient;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CareProvider getCareProvider(String userId){
        ESUserManager.GetCareProviderTask CareProviderTask = new ESUserManager.GetCareProviderTask();
        CareProviderTask.execute(userId);
        try {
            CareProvider careProvider = CareProviderTask.get();
            return careProvider;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Adds listener to patient listeners
    public void addListener(Patient patient, Listener listener) {
        new ESUserManager.AddPatientTask().execute(patient);
    }



    //Adds problem to list of problems
    public void addProblemToList(Problem problem){
        Patient patient = getPatient(LoggedInSingleton.getInstance().getLoggedInID());
        Log.d("problem", problem.getDate().toString());
        patient.addProblem(problem);
        new ESUserManager.AddPatientTask().execute(patient);
    }

    public boolean addPatientToCareProvider(String userId){
        Patient patient = getPatient(userId);
        if(patient != null){
            CareProvider careProvider = getCareProvider(LoggedInSingleton.getInstance().getLoggedInID());
            careProvider.addPatient(patient);
            new ESUserManager.AddCareProviderTask().execute(careProvider);
            return true;
        }
        else {return false;}
    }

    //Edits a given patients email address and updates it by overriding current ES index
    public void editPatientEmail(Patient patient, String email){
        patient.setEmailAdress(email);
        new ESUserManager.AddPatientTask().execute(patient);
    }

    //Edits a given patients contact number and updates it by overriding current ES index
    public void editPatientContactNumber(Patient patient, String phoneNumber){
        patient.setPhoneNumber(phoneNumber);
        new ESUserManager.AddPatientTask().execute(patient);
    }

    //Edits a given care providers email address and updates it by overriding current ES index
    public void editCareProviderEmail(CareProvider careProvider, String email){
        careProvider.setEmailAdress(email);
        new ESUserManager.AddCareProviderTask().execute(careProvider);
    }

    //Edits a given care providers contact number and updates it by overriding current ES index
    public void editCareProviderContactNumber(CareProvider careProvider, String phoneNumber){
        careProvider.setPhoneNumber(phoneNumber);
        new ESUserManager.AddCareProviderTask().execute(careProvider);
    }

    /**
     * Adds a record to the problem of the logged in user located at the index given
     * @param record
     * @param index
     */
    public void addRecord(Record record, int index){
        Patient patient = this.getPatient(LoggedInSingleton.getInstance().getLoggedInID());
        patient.getProblemList().get(index).getRecords().add(record);
        new ESUserManager.AddPatientTask().execute(patient);
    }

    //Gets a list of patients for a given CareProvider
    public ArrayList<Patient> getPatientList(String userId){
        CareProvider careProvider = getCareProvider(userId);
        return careProvider.getPatients();
    }




}

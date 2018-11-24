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
package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESUserManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;


/**
 * A controller object for Patients and CareProviders.
 *
 * @author Joseph Potentier, Chris Penner
 * @version 0.1
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

    /**
     * Instantiates a new User controller.
     */
    public UserController(){}

    /**
     * Adds a patient to ElasticSearch when given the appropriate information.
     *
     * @param userID    the user id
     * @param userPhone the user phone
     * @param userEmail the user email
     */
    public Boolean addNewUser(Context context, String userID, String userPhone, String userEmail, Boolean newPatient, Boolean newCareProvider){
        if(!newPatient && !newCareProvider){
            Toast.makeText(context,
                    context.getString(R.string.NewUserActivity_SelectUserType), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(userID.equals("") || userPhone.equals("") || userEmail.equals("")){
            Toast.makeText(context,
                    context.getString(R.string.NewUserActivity_EnterAllFields), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(userID.length() < 8){
            Toast.makeText(context, context.getString(R.string.NewUserActivity_UserIdMin), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(userID.contains(" ")){
            Toast.makeText(context, context.getString(R.string.NewUserActivity_UserIdSpaces), Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(new UserController().userExists(userID)){
            Toast.makeText(context,context.getString(R.string.NewUserActivity_userid_in_use), Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            Log.d("addNewUser", "All tests passed");
            return true;

        }
    }
//Adds user types to ES and/or memory
    public void addPatient(String userID, String userPhone, String userEmail){
        Patient patient = new Patient(userID, userPhone, userEmail);
        new ESUserManager.AddPatientTask().execute(patient);
    }

    /**
     * Adds a CareProvider to ElasticSearch when given the appropriate information.
     *
     * @param userID    the user id
     * @param userPhone the user phone
     * @param userEmail the user email
     */
    public void addCareProvider(String userID, String userPhone, String userEmail){
        CareProvider careProvider = new CareProvider(userID, userPhone, userEmail);
        new ESUserManager.AddCareProviderTask().execute(careProvider);
    }


    /**
     * Given a userID, returns the patient associated with the ID (if it exists).
     *
     * @param userId the user id
     * @return the patient
     */
//Retrieves Patient or Care Provider
    public Patient getPatient(String userId){
        try {
            Patient patient = new ESUserManager.GetAPatientTask().execute(userId).get();
            return patient;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Given a userID, returns the CareProvider associated with the ID (if it exists).
     *
     * @param userId the user id
     * @return the care provider
     */
    public CareProvider getCareProvider(String userId){
        try {
            CareProvider careProvider = new ESUserManager.GetACareProviderTask().execute(userId).get();
            return careProvider;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * User exists boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    public boolean userExists(String userId){
        Patient patient = getPatient(userId);
        CareProvider careProvider = getCareProvider(userId);
        if(patient == null && careProvider == null){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Sets the patients parentId to that of the currently logged in Care Provider
     * This occurs when the CareProvider adds the patient to their assignee list.
     *
     * @param userIdOfPatient self descriptive
     * @return the boolean
     */
    public Boolean setParentOfPatient(String userIdOfPatient){
        //TODO remove boolean dependency
        Patient patient = getPatient(userIdOfPatient);
        if(patient != null){
            CareProvider careProvider = getCareProvider(LoggedInSingleton.getInstance().getLoggedInID());
            patient.setParentId(careProvider.getUserID());
            new ESUserManager.AddPatientTask().execute(patient);
            return true;
        }
        else{Log.d("setParentOfPatient: ", "Failed to set patient parent.");
        return false;}
    }

    /**
     * Get patient list array list based on currently logged in Care Provider.
     *
     * @return the array list
     */
    //Gets a list of patients for a given CareProvider
    public ArrayList<Patient> getPatientList(){
        String careProviderId = LoggedInSingleton.getInstance().getLoggedInID();
        try {
            ArrayList<Patient> patients = new ESUserManager.GetPatientListTask().execute(careProviderId).get();
            return patients;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Edit patient email.
     *
     * @param patient the patient
     * @param email   the email
     */
//Edits a given patients email address and updates it by overriding current ES index
    public void editPatientEmail(Patient patient, String email){
        patient.setEmailAddress(email);
        new ESUserManager.AddPatientTask().execute(patient);
    }

    /**
     * Edit patient contact number.
     *
     * @param patient     the patient
     * @param phoneNumber the phone number
     */
//Edits a given patients contact number and updates it by overriding current ES index
    public void editPatientContactNumber(Patient patient, String phoneNumber){
        patient.setPhoneNumber(phoneNumber);
        new ESUserManager.AddPatientTask().execute(patient);
    }

    /**
     * Edit care provider email.
     *
     * @param careProvider the care provider
     * @param email        the email
     */
//Edits a given care providers email address and updates it by overriding current ES index
    public void editCareProviderEmail(CareProvider careProvider, String email){
        careProvider.setEmailAddress(email);
        new ESUserManager.AddCareProviderTask().execute(careProvider);
    }

    /**
     * Edit care provider contact number.
     *
     * @param careProvider the care provider
     * @param phoneNumber  the phone number
     */
//Edits a given care providers contact number and updates it by overriding current ES index
    public void editCareProviderContactNumber(CareProvider careProvider, String phoneNumber){
        careProvider.setPhoneNumber(phoneNumber);
        new ESUserManager.AddCareProviderTask().execute(careProvider);
    }

}

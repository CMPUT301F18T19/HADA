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

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESProblemManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESUserManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;


/**
 * A controller object for Patients and CareProviders.
 *
 * @author Joseph Potentier, Chris Penner, Anders J.
 * @version 2.0
 */
public class UserController {
    /**
     * Short code length variable.
     */
    private int shortCodeLength = 6;
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
     *
     * @
     */
    public UserController() {
    }

    /**
     * Checks given new user info and returns true if all fields are valid.
     *
     * @param context         the context
     * @param userID          the user id
     * @param userPhone       the user phone
     * @param userEmail       the user email
     * @param newPatient      the new patient
     * @param newCareProvider the new care provider
     * @return the boolean
     */
    public Boolean addNewUser(Context context, String userID, String userPhone, String userEmail, Boolean newPatient, Boolean newCareProvider) {
        if (!newPatient && !newCareProvider) {
            Toast.makeText(context,
                    context.getString(R.string.NewUserActivity_SelectUserType), Toast.LENGTH_SHORT).show();
            return false;
        } else if (userID.equals("") || userPhone.equals("") || userEmail.equals("")) {
            Toast.makeText(context,
                    context.getString(R.string.NewUserActivity_EnterAllFields), Toast.LENGTH_SHORT).show();
            return false;
        } else if (userID.length() < 8) {
            Toast.makeText(context, context.getString(R.string.NewUserActivity_UserIdMin), Toast.LENGTH_SHORT).show();
            return false;
        } else if (userID.contains(" ")) {
            Toast.makeText(context, context.getString(R.string.NewUserActivity_UserIdSpaces), Toast.LENGTH_SHORT).show();
            return false;
        } else if (new UserController().userExists(userID)) {
            Toast.makeText(context, context.getString(R.string.NewUserActivity_userid_in_use), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Log.d("addNewUser", "All tests passed");
            return true;

        }
    }

    /**
     * Adds a Patient to ElasticSearch when given the appropriate information.
     *
     * @param userID    the user id
     * @param userPhone the user phone
     * @param userEmail the user email
     */
//Adds user types to ES and/or memory
    public void addPatient(String userID, String userPhone, String userEmail) {
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
    public void addCareProvider(String userID, String userPhone, String userEmail) {
        CareProvider careProvider = new CareProvider(userID, userPhone, userEmail);
        new ESUserManager.AddCareProviderTask().execute(careProvider);
    }


    public void deletePatient(String userId) {
        new ESUserManager.DeletePatientTask().execute(userId);
        ArrayList<Problem> problemsToDelete = new ProblemController().getListOfProblems(userId);
        for (Problem problem : problemsToDelete) {
            new ProblemController().deleteProblem(problem.getFileId());
        }
    }

    public void deleteCareProvider(String userId) {
        new ESUserManager.DeleteCareProviderTask().execute(userId);
        //TODO: Delete care provider comments?

    }

    /**
     * Given a userID, returns the patient associated with the ID (if it exists).
     *
     * @param userId the user id
     * @return the patient
     */
//Retrieves Patient or Care Provider
    public Patient getPatient(String userId) {
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

    public Patient getPatientWithShortCode(String shortCode) {
        try {
            Patient patient = new ESUserManager.GetPatientWithShortCodeTask().execute(shortCode).get();
            if(patient == null){
                return null;
            }
            String userId = patient.getUserID();
            String newShortCode = RandomStringUtils.random(shortCodeLength, true, true);
            if (shortCodeExists(newShortCode)) {
                newShortCode = RandomStringUtils.random(shortCodeLength, true, true);
            }
            patient.setShortCode(newShortCode);
            new ESUserManager.AddPatientTask().execute(patient);
            return new ESUserManager.GetAPatientTask().execute(userId).get();
        } catch (InterruptedException e) {
            Log.d("getPatientWithShortCode", "Could not retieve patient from ES. INTERRUPTED");
            e.printStackTrace();
            return null;
        }catch (ExecutionException e){
            Log.d("getPatientWithShortCode", "Could not retieve patient from ES. EXECUTION");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Given a userID, returns the CareProvider associated with the ID (if it exists).
     *
     * @param userId the user id
     * @return the care provider
     */
    public CareProvider getCareProvider(String userId) {
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
    public boolean userExists(String userId) {
        Patient patient = getPatient(userId);
        CareProvider careProvider = getCareProvider(userId);
        if (patient == null && careProvider == null) {
            return false;
        } else {
            return true;
        }
    }


    public boolean shortCodeExists(String shortCode) {
        try{
            Patient patient = new ESUserManager.GetPatientWithShortCodeTask().execute(shortCode).get();
            if(patient != null){
                return true;
            }
        }catch (Exception e){
            Log.d("shortCodeExists" ,"Failed to load patient from ES");
        }
        return false;
    }

    /**
     * Sets the patients parentId to that of the currently logged in Care Provider
     * This occurs when the CareProvider adds the patient to their assignee list.
     *
     * @param userIdOfPatient self descriptive
     * @return the boolean
     */
    public Boolean setParentOfPatient(String userIdOfPatient) {
        //TODO remove boolean dependency
        Patient patient = getPatient(userIdOfPatient);
        if (patient != null) {
            CareProvider careProvider = getCareProvider(LoggedInSingleton.getInstance().getLoggedInID());
            patient.setParentId(careProvider.getUserID());
            new ESUserManager.AddPatientTask().execute(patient);
            return true;
        } else {
            Log.d("setParentOfPatient: ", "Failed to set patient parent.");
            return false;
        }
    }

    public Boolean setParentOfPatientWithShortCode(String shortCode) {
        Patient patient = getPatientWithShortCode(shortCode);
        if (patient != null) {
            CareProvider careProvider = getCareProvider(LoggedInSingleton.getInstance().getLoggedInID());
            patient.setParentId(careProvider.getUserID());
            String newShortCode = RandomStringUtils.random(shortCodeLength, true, true);
            if (shortCodeExists(newShortCode)) {
                newShortCode = RandomStringUtils.random(shortCodeLength, true, true);
            }
            patient.setShortCode(newShortCode);
            new ESUserManager.AddPatientTask().execute(patient);
            return true;
        } else {
            Log.d("setParentOfPatient: ", "Failed to set patient parent.");
            return false;
        }
    }

    /**
     * Get patient list array list based on currently logged in Care Provider.
     *
     * @return the array list
     */
//Gets a list of patients for a given CareProvider
    public ArrayList<Patient> getPatientList() {
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
    public void editPatientEmail(Patient patient, String email) {
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
    public void editPatientContactNumber(Patient patient, String phoneNumber) {
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
    public void editCareProviderEmail(CareProvider careProvider, String email) {
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
    public void editCareProviderContactNumber(CareProvider careProvider, String phoneNumber) {
        careProvider.setPhoneNumber(phoneNumber);
        new ESUserManager.AddCareProviderTask().execute(careProvider);
    }


    public Boolean isPatient(String userId) {
        try {
            Patient patient = new ESUserManager.GetAPatientTask().execute(userId).get();
            if (patient != null) {
                return true;
            }
        } catch (Exception e) {
            Log.d("isPatient", "Failed checking if it was a patient");
            e.printStackTrace();
        }
        return false;
    }
}

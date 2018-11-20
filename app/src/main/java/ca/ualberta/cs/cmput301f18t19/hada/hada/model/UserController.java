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

    /**
     * Given a userID, returns the CareProvider associated with the ID (if it exists).
     *
     * @param userId the user id
     * @return the care provider
     */
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
     * Add problem to the logged in patient's problems list.
     *
     * @param problem the problem
     */
//Adds problem to list of problems
    public void addProblemToPatient(Problem problem){
        Patient patient = getPatient(LoggedInSingleton.getInstance().getLoggedInID());
        Log.d("problem", problem.getDate().toString());
        patient.addProblem(problem);
        new ESUserManager.AddPatientTask().execute(patient);
    }

    /**
     * Set problem of patient at a given index.
     *
     * @param problem the problem
     * @param index   the index
     */
    public void setProblemOfPatient(Problem problem, int index){
        Patient patient = getPatient(LoggedInSingleton.getInstance().getLoggedInID());
        patient.setProblem(index, problem);
        new ESUserManager.AddPatientTask().execute(patient);
    }

    /**
     * Removes a problem from the patient's problems list.
     *
     * @param problem the problem
     */
    public void removeProblemOfPatient(Problem problem){
        Patient patient = getPatient(LoggedInSingleton.getInstance().getLoggedInID());
        patient.removeProblem(problem);
        new ESUserManager.AddPatientTask().execute(patient);
    }

    /**
     * Checks if patient is in the list of patients associated with the logged in CareProvider.
     * Adds them to the list if they are not null, and returns a boolean associated with the
     * success or failure of the operation.
     *
     * @param userId the user id
     * @return the boolean
     */
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

    /**
     * Get patient list array list.
     *
     * @param userId the user id
     * @return the array list
     */
//Gets a list of patients for a given CareProvider
    public ArrayList<Patient> getPatientList(String userId){
        CareProvider careProvider = getCareProvider(userId);
        return careProvider.getPatients();
    }

    /**
     * Edit patient email.
     *
     * @param patient the patient
     * @param email   the email
     */
//Edits a given patients email address and updates it by overriding current ES index
    public void editPatientEmail(Patient patient, String email){
        patient.setEmailAdress(email);
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
        careProvider.setEmailAdress(email);
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

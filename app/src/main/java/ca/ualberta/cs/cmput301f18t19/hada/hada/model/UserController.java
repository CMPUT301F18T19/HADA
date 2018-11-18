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

    private ArrayList<Patient> patientList;
    protected ArrayList<Listener> listeners;

    public UserController() {
        patientList = new ArrayList<Patient>();
    }

    //public Patient getPatient(int index) {
        //return patientList.get(index);
    //}

    /**@deprecated **/
    //public void addPatient(Patient patient) {
        //patientList.add(patient);
    //}

    public boolean inList(Patient patient) {
        return patientList.contains(patient);
    }

    public void insertPatient(int index, Patient patient) {
        patientList.add(index, patient);
    }

    public void deletePatient(Patient patient) {
        patientList.remove(patient);
    }

    public boolean isEmpty() {
        return patientList.isEmpty();
    }

    public int getSize() {
        return patientList.size();
    }

    public int getPos(Patient patient) {
        return patientList.indexOf(patient);
    }

    public void notifyListeners(){
        for (Listener listener : listeners){
            if(listener != null){
                listener.update();}
        }
    }
    public void addListener(Listener l){
        listeners.add(l);
    }
    public void removeListener(Listener l){
        listeners.remove(l);
    }

    //List stuff above may be deprocated, new  methods below:


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


}

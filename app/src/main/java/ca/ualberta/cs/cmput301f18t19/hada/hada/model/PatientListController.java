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

import ca.ualberta.cs.cmput301f18t19.hada.hada.utility.Listener;

/**
 * @author Christopher Penner
 * @see
 * @version 0.1
 */
public class PatientListController {

    private ArrayList<Patient> patientList;
    protected ArrayList<Listener> listeners;

    PatientListController() {
        patientList = new ArrayList<Patient>();
    }

    public Patient getPatient(int index) {
        return patientList.get(index);
    }

    public void addPatient(Patient patient) {
        patientList.add(patient);
    }

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
}

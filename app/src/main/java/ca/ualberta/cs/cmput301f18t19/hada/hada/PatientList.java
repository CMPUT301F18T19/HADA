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
package ca.ualberta.cs.cmput301f18t19.hada.hada;

import java.util.ArrayList;

/**
 * @author Christopher Penner
 * @see
 * @version 0.1
 */
public class PatientList {

    private ArrayList<Patient> patientList;

    PatientList() {
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

    public void deletePatient(int index) {
        Patient patient = patientList.get(index);
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
}

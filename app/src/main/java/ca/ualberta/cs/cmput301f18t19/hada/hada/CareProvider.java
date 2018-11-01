/* CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 2018-10-27
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */
package ca.ualberta.cs.cmput301f18t19.hada.hada;


/**
 * @author Joseph Potentier-Neal
 * @see
 * @version
 */
public class CareProvider extends User {

    private PatientList patients = new PatientList();

    public void setPatients(PatientList patients){
        this.patients = patients;
    }
    public PatientList getPatients(){
        return this.patients;
    }
}

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
package ca.ualberta.cs.cmput301f18t19.hada.hada.model;


import java.util.ArrayList;

/**
 * @author Joseph Potentier-Neal
 * @see
 * @version
 */
public class CareProvider extends User {

    private ArrayList<Patient> patients = new ArrayList<Patient>();

    public CareProvider(){
        super();
    }
    public CareProvider(String userID, String phoneNumber, String emailAddress){
        super(userID, phoneNumber, emailAddress);
    }

    public void setPatients(ArrayList<Patient> patients){
        this.patients = patients;
    }
    public ArrayList<Patient> getPatients(){
        return this.patients;
    }
}

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


/**
 * Object which represents a user of type patient.
 *
 * @author Joseph Potentier-Neal
 * @see User
 */
public class Patient extends User {

    private String parentId;

    /**
     * Instantiates a new Patient with no attributes set.
     */
    public Patient(){
        super();
    }

    /**
     * Instantiates a new Patient given a userID, phone number and email address.
     *
     * @param userID       the user id
     * @param phoneNumber  the phone number
     * @param emailAddress the email address
     */
    public Patient(String userID, String phoneNumber, String emailAddress){
        super(userID, phoneNumber, emailAddress);
    }

    //setters

    /**
     * Set parent id.
     *
     * @param parentId id of the parent object
     */
    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    //getters

    /**
     * Get parent id string.
     *
     * @return the parents id
     */
    public String getParentId(){
        return this.parentId;
    }

    //extras

    /**
     * Overrides the toString() so that listView prints the object correctly.
     *
     * @return A human readable version of the object
     */
    @Override
    public String toString(){
        return this.getUserID();
    }

}

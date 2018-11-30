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


import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * Object which represents a user of type patient.
 *
 * @author Joseph Potentier-Neal
 * @see User
 */
public class Patient extends User {

    private String parentId;
    private String shortCode = RandomStringUtils.random(6, true, true);


    /**
     * Instantiates a new Patient with no attributes set.
     */
    public Patient()
    {
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

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getShortCode() {
        return shortCode;
    }

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

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


import io.searchbox.annotations.JestId;

/**
 * An abstract class with commonalities for both Patients and CareProviders to draw from.
 *
 * @author Joseph Potentier-Neal
 * @see Patient
 * @see CareProvider
 */
public abstract class User {

    /**
     * JestId allows JestDroid to recognize that we use the userID as an ID tag.
     */
    @JestId
    private String userID;
    private String phoneNumber;
    private String emailAddress;

    /**
     * Instantiates a new User with no parameters set.
     */
    public User(){}

    /**
     * Instantiates a new User with a given userID, phone number, and email address.
     *
     * @param userID       the user id
     * @param phoneNumber  the phone number
     * @param emailAddress the email address
     */
    public User(String userID, String phoneNumber, String emailAddress){
        this.userID = userID;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    /**
     * Sets the userID of the user.
     *
     * @param newID the new id
     */
    public void setUserID(String newID){
        this.userID = newID;
    }

    /**
     * Returns the userID of the user.
     *
     * @return the string
     */
    public String getUserID(){
        return this.userID;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param newPhoneNumber the new phone number
     */
    public void setPhoneNumber(String newPhoneNumber){
        this.phoneNumber = newPhoneNumber;
    }

    /**
     * Returns the phone number of the user.
     *
     * @return the string
     */
    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    /**
     * Sets the email address of the user.
     *
     * @param newEmail the new email
     */
    public void setEmailAdress(String newEmail){
        this.emailAddress = newEmail;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the string
     */
    public String getEmailAddress(){
        return this.emailAddress;
    }


}


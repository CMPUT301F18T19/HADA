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
public abstract class User {

    private String userID;
    private String phoneNumber;
    private String emailAddress;

    public void setUserID(String newID){
        this.userID = newID;
    }

    public String getUserID(){
        return this.userID;
    }

    public void setPhoneNumber(String newPhoneNumber){
        this.phoneNumber = newPhoneNumber;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setEmailAdress(String newEmail){
        this.emailAddress = newEmail;
    }

    public String getEmailAddress(){
        return this.emailAddress;
    }

    abstract public String getType();

}


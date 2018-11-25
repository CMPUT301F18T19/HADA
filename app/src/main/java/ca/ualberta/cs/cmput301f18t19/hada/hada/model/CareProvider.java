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
 * The type Care provider.
 *
 * @author Joseph Potentier-Neal
 * @see
 */
public class CareProvider extends User {
    /**
     * Instantiates a new Care Provider with no parameters.
     */
    public CareProvider(){
        super();
    }

    /**
     * Instantiates a new Care Provider when given parameters.
     *
     * @param userID       the user id
     * @param phoneNumber  the phone number
     * @param emailAddress the email address
     */
    public CareProvider(String userID, String phoneNumber, String emailAddress){
        super(userID, phoneNumber, emailAddress);
    }
}

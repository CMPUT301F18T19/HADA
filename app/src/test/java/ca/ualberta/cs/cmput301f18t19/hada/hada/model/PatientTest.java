/*
 *  CMPUT 301 - Fall 2018
 *
 *  PatientTest.java
 *
 *  11/27/18 3:25 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.support.annotation.NonNull;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests for the class Patient.
 *
 * @see Patient
 * @author Christopher Penner
 */
public class PatientTest {

    /**
     * Setup creates a pre defined Patient to test getters
     */
    @NonNull
    private Patient setup() {
        return new Patient("TestUID",
                "7809991234",
                "test@ualberta.ca");
    }

    /**
     * Test set user id.
     */
    @Test
    public void testSetUserID(){
        Patient patient = setup();
        patient.setUserID("UserID123");
        assertEquals("UserID123",patient.getUserID());
    }

    /**
     * Test get user id.
     */
    @Test
    public void testGetUserID(){
        Patient patient = setup();
        String returnedID = patient.getUserID();
        assertEquals("TestUID",returnedID);
    }


    /**
     * Test set phone number.
     */
    @Test
    public void testSetPhoneNumber(){
        Patient patient = setup();
        patient.setPhoneNumber("1809994466");
        assertEquals("1809994466", patient.getPhoneNumber());
    }

    /**
     * Test get phone number.
     */
    @Test
    public void testGetPhoneNumber(){
        Patient patient = setup();
        String returnedPhoneNumber = patient.getPhoneNumber();
        assertEquals("7809991234", returnedPhoneNumber);
    }

    /**
     * Test set email address.
     */
    @Test
    public void testSetEmailAddress(){
        Patient patient = setup();
        patient.setEmailAddress("testSet@ualberta.ca");
        assertEquals("testSet@ualberta.ca", patient.getEmailAddress());
    }

    /**
     * Test get email address.
     */
    @Test
    public void testGetEmailAddress(){
        Patient patient = setup();
        String returnedEmailAddress = patient.getEmailAddress();
        assertEquals("test@ualberta.ca", returnedEmailAddress);
    }
}

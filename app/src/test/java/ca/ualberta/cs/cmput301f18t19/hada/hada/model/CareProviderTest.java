/*
 *  CMPUT 301 - Fall 2018
 *
 *  CareProviderTest.java
 *
 *  11/27/18 3:30 PM
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
 * Tests for the class CareProvider.
 *
 * @see CareProvider
 * @author Christopher Penner
 */
public class CareProviderTest {

    /**
     * Setup creates a pre defined CareProvider to test getters
     */
    @NonNull
    private CareProvider setup() {
        return new CareProvider("TestCID",
                "7801234567",
                "test@ualberta.ca");
    }

    /**
     * Test set user id.
     */
    @Test
    public void testSetUserID(){
        CareProvider careProvider = setup();
        careProvider.setUserID("Test CP User ID");
        assertEquals("Test CP User ID", careProvider.getUserID());
    }

    /**
     * Test get user id.
     */
    @Test
    public void testGetUserID(){
        CareProvider careProvider = setup();
        String returnedID = careProvider.getUserID();
        assertEquals("TestCID",returnedID);
    }


    /**
     * Test set phone number.
     */
    @Test
    public void testSetPhoneNumber(){
        CareProvider careProvider = setup();
        careProvider.setPhoneNumber("1809994466");
        assertEquals("1809994466", careProvider.getPhoneNumber());
    }

    /**
     * Test get phone number.
     */
    @Test
    public void testGetPhoneNumber(){
        CareProvider careProvider = setup();
        String returnedPhoneNumber = careProvider.getPhoneNumber();
        assertEquals("7801234567", returnedPhoneNumber);
    }

    /**
     * Test set email address.
     */
    @Test
    public void testSetEmailAddress(){
        CareProvider careProvider = setup();
        careProvider.setEmailAddress("testSet@ualberta.ca");
        assertEquals("testSet@ualberta.ca", careProvider.getEmailAddress());
    }

    /**
     * Test get email address.
     */
    @Test
    public void testGetEmailAddress(){
        CareProvider careProvider = setup();
        String returnedEmailAddress = careProvider.getEmailAddress();
        assertEquals("test@ualberta.ca", returnedEmailAddress);
    }
}

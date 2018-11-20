/*
 * CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 11/19/18 10:52 PM
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 * Last modified 11/19/18 10:52 PM
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoggedInSingletonTest {

    @Test
    public void testSetLoggedInID() {
        LoggedInSingleton instance = LoggedInSingleton.getInstance();
        assertNotEquals(null, instance);
        instance.setLoggedInID("UID");
        assertEquals("UID", instance.getLoggedInID());
    }

    @Test
    public void testGetLoggedInID() {
        LoggedInSingleton instance = LoggedInSingleton.getInstance();
        assertNotEquals("UID1", instance.getLoggedInID());
        instance.setLoggedInID("UID1");
        assertEquals("UID1", instance.getLoggedInID());
        assertNotEquals("uid", instance.getLoggedInID());
    }

    @Test
    public void testSetIsCareProvider() {
        LoggedInSingleton instance = LoggedInSingleton.getInstance();
        instance.setIsCareProvider(true);
        assertTrue(instance.getIsCareProvider());
    }

    @Test
    public void testGetIsCareProvider() {
        LoggedInSingleton instance = LoggedInSingleton.getInstance();
        assertFalse(instance.getIsCareProvider());
        instance.setIsCareProvider(true);
    }
}
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

import android.content.Intent;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the class LoggedInSingleton.
 *
 * @author Christopher Penner
 * @see LoggedInSingleton
 */
public class LoggedInSingletonTest {

    private LoggedInSingleton instance;

    /**
     * Setup creates a instance of LoggedInSingleton to test getters
     */
    @Before
    public void setup() {
        this.instance = LoggedInSingleton.getInstance();
        instance.setLoggedInID("TestUID");
        instance.setIsCareProvider(false);
    }

    /**
     * Test set logged in id.
     */
    @Test
    public void testSetLoggedInID() {
        assertNotEquals(null, instance);
        instance.setLoggedInID("OverrideUID");
        assertEquals("OverrideUID", instance.getLoggedInID());
    }

    /**
     * Test get logged in id.
     */
    @Test
    public void testGetLoggedInID() {
        assertEquals("TestUID", instance.getLoggedInID());
        assertNotEquals("OverrideUID", instance.getLoggedInID());
    }

    /**
     * Test set is care provider.
     */
    @Test
    public void testSetIsCareProvider() {
        instance.setIsCareProvider(true);
        assertTrue(instance.getIsCareProvider());
    }

    /**
     * Test get is care provider.
     */
    @Test
    public void testGetIsCareProvider() {
        assertFalse(instance.getIsCareProvider());
        instance.setIsCareProvider(true);
        assertTrue(instance.getIsCareProvider());
    }
}
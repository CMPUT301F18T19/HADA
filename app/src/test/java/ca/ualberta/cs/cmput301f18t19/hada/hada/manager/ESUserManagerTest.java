/*
 * CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 11/20/18 3:21 PM
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 * Last modified 11/20/18 2:57 PM
 */
package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

import static org.junit.Assert.*;

/**
 * Tests for ESUserManager class.
 *
 * @author Christopher Penner
 * @see ESUserManager
 */
public class ESUserManagerTest {

    /**
     * Test get patient task.
     */
    @Test
    public void TestAGetPatientTask() {
        ESUserManager.GetAPatientTask getPatientTask = new ESUserManager.GetAPatientTask();
        assertNotNull(getPatientTask);
    }

    /**
     * Test add patient task.
     */
    @Test
    public void TestAddPatientTask() {
        ESUserManager.AddPatientTask addPatientTask = new ESUserManager.AddPatientTask();
        assertNotNull(addPatientTask);
    }

    /**
     * Test get care provider task.
     */
    @Test
    public void TestGetACareProviderTask() {
        ESUserManager.GetACareProviderTask getACareProviderTask
                = new ESUserManager.GetACareProviderTask();
        assertNotNull(getACareProviderTask);
    }

    /**
     * Test add care provider task.
     */
    @Test
    public void TestAddCareProviderTask() {
        ESUserManager.AddCareProviderTask addCareProviderTask
                = new ESUserManager.AddCareProviderTask();
        assertNotNull(addCareProviderTask);
    }
}

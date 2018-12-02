/*
 *  CMPUT 301 - Fall 2018
 *
 *  ESBodyLocationManagerTest.java
 *
 *  12/2/18 11:28 AM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESUserManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests for ESBodyLocationManager class.
 *
 * @author Christopher Penner
 * @see ESBodyLocationManager
 */
public class ESBodyLocationManagerTest {

    /**
     * Test add body location task.
     */
    @Test
    public void TestAddBodyLocationTask() {
        ESBodyLocationManager.AddBodyLocationTask addBodyLocationTask
                = new ESBodyLocationManager.AddBodyLocationTask();
        assertNotNull(addBodyLocationTask);
    }

    /**
     * Test get record list task.
     */
    @Test
    public void TestGetRecordListTask() {
        ESBodyLocationManager.GetRecordListTask getRecordListTask
                = new ESBodyLocationManager.GetRecordListTask();
        assertNotNull(getRecordListTask);
    }

    /**
     * Test get a body location task.
     */
    @Test
    public void TestGetABodyLocationTask() {
        ESBodyLocationManager.GetABodyLocationTask getABodyLocationTask
                = new ESBodyLocationManager.GetABodyLocationTask();
        assertNotNull(getABodyLocationTask);
    }

    /**
     * Test delete a body location task.
     */
    @Test
    public void TestDeleteABodyLocationTask() {
        ESBodyLocationManager.DeleteABodyLocationTask deleteABodyLocationTask
                = new ESBodyLocationManager.DeleteABodyLocationTask();
        assertNotNull(deleteABodyLocationTask);
    }
}

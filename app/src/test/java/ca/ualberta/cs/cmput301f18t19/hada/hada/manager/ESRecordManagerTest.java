/*
 *  CMPUT 301 - Fall 2018
 *
 *  ESRecordManagerTest.java
 *
 *  11/27/18 7:48 AM
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
 * Tests for ESRecordManager class.
 *
 * @author Christopher Penner
 * @see ESRecordManager
 */
public class ESRecordManagerTest {

    /**
     * Test add record task.
     */
    @Test
    public void TestAddRecordTask() {
        ESRecordManager.AddRecordTask addRecordTask = new ESRecordManager.AddRecordTask();
        assertNotNull(addRecordTask);
    }

    /**
     * Test delete a record task.
     */
    @Test
    public void TestDeleteARecordTask() {
        ESRecordManager.DeleteARecordTask deleteARecordTask
                = new ESRecordManager.DeleteARecordTask();
        assertNotNull(deleteARecordTask);
    }

    /**
     * Test get a record task.
     */
    @Test
    public void TestGetARecordTask() {
        ESRecordManager.GetARecordTask getARecordTask = new ESRecordManager.GetARecordTask();
        assertNotNull(getARecordTask);
    }

    /**
     * Test get record list task.
     */
    @Test
    public void TestGetRecordListTask() {
        ESRecordManager.GetRecordListTask getRecordListTask
                = new ESRecordManager.GetRecordListTask();
        assertNotNull(getRecordListTask);
    }
}

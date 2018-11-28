/*
 *  CMPUT 301 - Fall 2018
 *
 *  ESProblemManagerTest.java
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
 * Tests for ESProblemManager class.
 *
 * @author Christopher Penner
 * @see ESProblemManager
 */
public class ESProblemManagerTest {

    /**
     * Test add problem task.
     */
    @Test
    public void TestAddProblemTask() {
        ESProblemManager.AddProblemTask addProblemTask = new ESProblemManager.AddProblemTask();
        assertNotNull(addProblemTask);
    }

    /**
     * Test get problem list task.
     */
    @Test
    public void TestGetProblemListTask() {
        ESProblemManager.GetProblemListTask getProblemListTask
                = new ESProblemManager.GetProblemListTask();
        assertNotNull(getProblemListTask);
    }

    /**
     * Test get a problem task.
     */
    @Test
    public void TestGetAProblemTask() {
        ESProblemManager.GetAProblemTask getAProblemTask = new ESProblemManager.GetAProblemTask();
        assertNotNull(getAProblemTask);
    }

    /**
     * Test delete problem task.
     */
    @Test
    public void TestDeleteProblemTask() {
        ESProblemManager.DeleteProblemTask deleteProblemTask
                = new ESProblemManager.DeleteProblemTask();
        assertNotNull(deleteProblemTask);
    }
}

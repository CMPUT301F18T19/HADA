package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESUserManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

// TODO fix ProblemControllerTest
public class ProblemControllerTest {
    private final String TAG = "ProblemControllerTest";
    private ProblemController problemController;
    private LoggedInSingleton mockLogIn;

    /**
     * set up subject and mock objects needed for the test.
     */
    @Before
    public void setUp() throws Exception {
        problemController = new ProblemController();
        mockLogIn = mock(LoggedInSingleton.class);
    }

    @Test
    public void testIsEmpty() {
        Problem problem = new Problem();
        problem.setTitle("test problem");
        String userID = "isEmptyTest";
        String userPhone = "1234567890";
        String userEmail = "testPatient@testing.test";
        assertNotEquals(null, problemController);
        assertEquals(true, problemController.isEmpty());
        try{
            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
            getPatientTask.execute(userID);
            Patient patient = getPatientTask.get();
            if (patient != null) {
                Log.d(TAG, "patient exists");
                assertFalse(true);
            } else {
                problemController.addProblem(problem);
                assertFalse(problemController.isEmpty());
            }
        } catch (Exception e) {}

    }

    @Test
    public void testAddProblem() {
        Problem problem = new Problem();
        problem.setTitle("test problem");
        String userID = "addProblemTest";
        String userPhone = "1234567890";
        String userEmail = "testPatient@testing.test";
        try{
            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
            getPatientTask.execute(userID);
            Patient patient = getPatientTask.get();
            if (patient != null) {
                Log.d(TAG, "patient exists");
            } else {
                assertTrue(problemController.isEmpty());
                problemController.addProblem(problem);
                assertFalse(problemController.isEmpty());
            }
        } catch (Exception e) {}
    }

    @Test
    public void testGetProblem() {
        Problem problem = new Problem();
        problem.setTitle("test problem");
        String userID = "getProblemTest";
        String userPhone = "1234567890";
        String userEmail = "testPatient@testing.test";
        try{
            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
            getPatientTask.execute(userID);
            Patient patient = getPatientTask.get();
            if (patient != null) {
                Log.d(TAG, "patient exists");
            } else {
                problemController.addProblem(problem);
                assertEquals(problem, problemController.getProblem(0));
                assertEquals(problem.getTitle(), problemController.getProblem(0).getTitle());
            }
        } catch (Exception e) {}

    }

    @Test
    public void testGetSize() {
        Problem problem = new Problem();
        problem.setTitle("test problem");
        String userID = "getSizeTest";
        String userPhone = "1234567890";
        String userEmail = "testPatient@testing.test";
        try{
            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
            getPatientTask.execute(userID);
            Patient patient = getPatientTask.get();
            if (patient != null) {
                Log.d(TAG, "patient exists");
            } else {
                assertEquals(0, problemController.getSize());
                problemController.addProblem(problem);
                assertEquals(1, problemController.getSize());
            }
        } catch (Exception e) {}
    }

    @Test
    public void testDeleteProblem() {
        Problem problem1 = new Problem();
        Problem problem2 = new Problem();
        String userID = "getSizeTest";
        String userPhone = "1234567890";
        String userEmail = "testPatient@testing.test";
        try{
            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
            getPatientTask.execute(userID);
            Patient patient = getPatientTask.get();
            if (patient != null) {
                Log.d(TAG, "patient exists");
            } else {
                problemController.addProblem(problem1);
                problemController.addProblem(problem2);
                assertEquals(2, problemController.getSize());
                problemController.deleteProblem(problem1);
                assertEquals(1, problemController.getSize());
                assertEquals(problem2, problemController.getProblem(0));
            }
        } catch (Exception e) {}
    }

    @Test
    public void testGetPosition() {
        Problem problem1 = new Problem();
        Problem problem2 = new Problem();
        String userID = "getPositionTest";
        String userPhone = "1234567890";
        String userEmail = "testPatient@testing.test";
        try{
            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
            getPatientTask.execute(userID);
            Patient patient = getPatientTask.get();
            if (patient != null) {
                Log.d(TAG, "patient exists");
            } else {
                problemController.addProblem(problem1);
                problemController.addProblem(problem2);
                assertEquals(1, problemController.getPos(problem2));
                problemController.deleteProblem(problem2);
                assertEquals(-1, problemController.getPos(problem2));
            }
        } catch (Exception e) {}
    }

    @Test
    public void testInsertProblem() {
        Problem problem1 = new Problem();
        Problem problem2 = new Problem();
        String userID = "insertProblemTest";
        String userPhone = "1234567890";
        String userEmail = "testPatient@testing.test";
        try{
            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
            getPatientTask.execute(userID);
            Patient patient = getPatientTask.get();
            if (patient != null) {
                Log.d(TAG, "patient exists");
            } else {
                problemController.addProblem(problem1);
                problemController.insertProblem(0, problem2);
                assertEquals(0, problemController.getPos(problem2));
                assertEquals(problem2, problemController.getProblem(0));
            }
        } catch (Exception e) {}
    }

    @Test
    public void testProblemInList() {
        Problem problem1 = new Problem();
        Problem problem2 = new Problem();
        Problem problem3 = new Problem();
        String userID = "insertProblemTest";
        String userPhone = "1234567890";
        String userEmail = "testPatient@testing.test";
        try{
            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
            getPatientTask.execute(userID);
            Patient patient = getPatientTask.get();
            if (patient != null) {
                Log.d(TAG, "patient exists");
            } else {
                problemController.addProblem(problem1);
                problemController.addProblem(problem2);
                assertTrue(problemController.inList(problem1));
                assertFalse(problemController.inList(problem3));
                problemController.addProblem(problem3);
                assertTrue(problemController.inList(problem3));
            }
        } catch (Exception e) {}
    }
}

package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESUserManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

// TODO fix everything
// TODO add stub method
/**
 * The type Problem controller test.
 */
//public class ProblemControllerTest {
//    private final String TAG = "ProblemControllerTest";
//    private ProblemController problemController;
//    private LoggedInSingleton mockLogIn;
//
//    /**
//     * set up subject and mock objects needed for the test.
//     */
//    @Before
//    public void setUp() throws Exception {
//        problemController = new ProblemController();
//        mockLogIn = mock(LoggedInSingleton.class);
//    }
//
//    private void stub() {
//
//    }
//
//    /**
//     * Test add problem.
//     */
//    @Test
//    public void testAddProblem() {
//        Problem problem = new Problem();
//        problem.setTitle("test problem");
//        String userID = "addProblemTest";
//        String userPhone = "1234567890";
//        String userEmail = "testPatient@testing.test";
//        try{
//            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
//            getPatientTask.execute(userID);
//            Patient patient = getPatientTask.get();
//            if (patient != null) {
//                Log.d(TAG, "patient exists");
//            } else {
//                problemController.addProblem(problem);
//            }
//        } catch (Exception e) {}
//    }
//
//    /**
//     * Test get problem.
//     */
//    @Test
//    public void testGetProblem() {
//        Problem problem = new Problem();
//        problem.setTitle("test problem");
//        String userID = "getProblemTest";
//        String userPhone = "1234567890";
//        String userEmail = "testPatient@testing.test";
//        try{
//            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
//            getPatientTask.execute(userID);
//            Patient patient = getPatientTask.get();
//            if (patient != null) {
//                Log.d(TAG, "patient exists");
//            } else {
//                problemController.addProblem(problem);
//                assertEquals(problem, problemController.getProblem(0));
//                assertEquals(problem.getTitle(), problemController.getProblem(0).getTitle());
//            }
//        } catch (Exception e) {}
//
//    }
//
//    /**
//     * Test delete problem.
//     */
//    @Test
//    public void testDeleteProblem() {
//        Problem problem1 = new Problem();
//        Problem problem2 = new Problem();
//        String userID = "getSizeTest";
//        String userPhone = "1234567890";
//        String userEmail = "testPatient@testing.test";
//        try{
//            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
//            getPatientTask.execute(userID);
//            Patient patient = getPatientTask.get();
//            if (patient != null) {
//                Log.d(TAG, "patient exists");
//            } else {
//                problemController.addProblem(problem1);
//                problemController.addProblem(problem2);
//                problemController.deleteProblem(problem1);
//                assertEquals(problem2, problemController.getProblem(0));
//            }
//        } catch (Exception e) {}
//    }
//
//    /**
//     * Test get position.
//     */
//    @Test
//    public void testGetPosition() {
//        Problem problem1 = new Problem();
//        Problem problem2 = new Problem();
//        String userID = "getPositionTest";
//        String userPhone = "1234567890";
//        String userEmail = "testPatient@testing.test";
//        try{
//            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
//            getPatientTask.execute(userID);
//            Patient patient = getPatientTask.get();
//            if (patient != null) {
//                Log.d(TAG, "patient exists");
//            } else {
//                problemController.addProblem(problem1);
//                problemController.addProblem(problem2);
//                assertEquals(1, problemController.getPos(problem2));
//                problemController.deleteProblem(problem2);
//                assertEquals(-1, problemController.getPos(problem2));
//            }
//        } catch (Exception e) {}
//    }
//
//    /**
//     * Test problem in list.
//     */
//    @Test
//    public void testProblemInList() {
//        Problem problem1 = new Problem();
//        Problem problem2 = new Problem();
//        Problem problem3 = new Problem();
//        String userID = "insertProblemTest";
//        String userPhone = "1234567890";
//        String userEmail = "testPatient@testing.test";
//        try{
//            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
//            getPatientTask.execute(userID);
//            Patient patient = getPatientTask.get();
//            if (patient != null) {
//                Log.d(TAG, "patient exists");
//            } else {
//                problemController.addProblem(problem1);
//                problemController.addProblem(problem2);
//                assertTrue(problemController.inList(problem1));
//                assertFalse(problemController.inList(problem3));
//                problemController.addProblem(problem3);
//                assertTrue(problemController.inList(problem3));
//            }
//        } catch (Exception e) {}
//    }
//}

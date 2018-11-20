/* CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 2018-11-20
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */
package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESUserManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for userController. Tests are passing for now, it needs rework
 * once controllers responsibilities are set in stone. This class is not done,
 * there are a few more methods need to be tested.
 *
 * @author Alex Li
 * @version 0.1
 */
public class UserControllerTest {
    private final String TAG = "userControllerTest";
    private UserController userController;
    private LoggedInSingleton mockLogIn;

    /**
     * set up subject and mock objects needed for the test.
     */
    @Before
    public void setUp() throws Exception {
        userController = new UserController();
        mockLogIn = mock(LoggedInSingleton.class);
    }

    /**
     * test method for addPatient()
     */
    @Test
    public void addPatient() {
        String userID = "addPatientTest";
        String userPhone = "1234567890";
        String userEmail = "testPatient@testing.test";
        try {
            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
            getPatientTask.execute(userID);
            Patient patient = getPatientTask.get();
            if (patient != null) {
                Log.d(TAG, "patient exists");
            } else {
                // call addPatient then retrieve with getPatientTask to assert
                userController.addPatient(userID, userPhone, userEmail);
                getPatientTask.execute("addPatientTest");
                patient = getPatientTask.get();
                assertEquals(userID, patient.getUserID());
                assertEquals(userPhone, patient.getPhoneNumber());
                assertEquals(userID, patient.getEmailAddress());
            }
        } catch (Exception e) {}
    }

    /**
     * test method for addCareProvider()
     */
    @Test
    public void addCareProvider() {
        String userID = "addCareProviderTest";
        String userPhone = "1234567890";
        String userEmail = "testCareProvider@testing.test";
        try {
            ESUserManager.GetCareProviderTask getCareProviderTask = new ESUserManager.GetCareProviderTask();
            getCareProviderTask.execute("addCareProviderTest");
            CareProvider CareProvider = getCareProviderTask.get();
            if (CareProvider != null) {
                Log.d(TAG, "CareProvider exists");
            } else {
                // call addCareProvider then retrieve with getCareProviderTask to assert
                userController.addCareProvider(userID, userPhone, userEmail);
                getCareProviderTask.execute("addCareProviderTest");
                CareProvider = getCareProviderTask.get();
                assertEquals(userID, CareProvider.getUserID());
                assertEquals(userPhone, CareProvider.getPhoneNumber());
                assertEquals(userID, CareProvider.getEmailAddress());
            }
        } catch (Exception e) {}
    }

    /**
     * test method for getPatient()
     */
    @Test
    public void getPatient() {
        String userID = "getPatientTest";
        String userPhone = "1234567890";
        String userEmail = "testPatient@testing.test";
        try{
            userController.addPatient(userID, userPhone, userEmail);
            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
            getPatientTask.execute("addPatientTest");
            Patient patient = getPatientTask.get();
            assertEquals(userID, patient.getUserID());
            assertEquals(userPhone, patient.getPhoneNumber());
            assertEquals(userID, patient.getEmailAddress());
        }catch (Exception e){}
    }

    /**
     * test method for getCareProvider()
     */
    @Test
    public void getCareProvider() {
        String userID = "getCareProviderTest";
        String userPhone = "1234567890";
        String userEmail = "testCareProvider@testing.test";
        try{
            userController.addCareProvider(userID, userPhone, userEmail);
            ESUserManager.GetCareProviderTask getCareProviderTask = new ESUserManager.GetCareProviderTask();
            getCareProviderTask.execute("addCareProviderTest");
            CareProvider CareProvider = getCareProviderTask.get();
            assertEquals(userID, CareProvider.getUserID());
            assertEquals(userPhone, CareProvider.getPhoneNumber());
            assertEquals(userID, CareProvider.getEmailAddress());
        }catch (Exception e){}
    }

    /**
     * test method for addPatientToCareProvider()
     */
    @Test
    public void addPatientToCareProvider() {
        String careProviderID = "testCareProvider";
        String patientID = "testPatient";
        mockLogIn.setLoggedInID(careProviderID);
        mockLogIn.setIsCareProvider(true);

        try {
            //
            ESUserManager.GetCareProviderTask getCareProviderTask = new ESUserManager.GetCareProviderTask();
            getCareProviderTask.execute(careProviderID);
            CareProvider careProvider = getCareProviderTask.get();
            ArrayList<Patient> patientList = careProvider.getPatients();
            if (patientList.size() != 0) {
                Log.d(TAG, "CareProvider already have patients");
            } else {
                userController.addPatientToCareProvider(patientID);
                patientList = getCareProviderTask.get().getPatients();
                assertEquals(1, patientList.size());
            }
        } catch (Exception e) {}
    }

    /**
     * test method for editPatientEmail()
     */
    @Test
    public void editPatientEmail() {
        String userID = "editPatientEmailTest";
        String userPhone = "1234567890";
        String userEmail = "testPatient@testing.test";
        String newEmail = "new@email.test";
        try {
            ESUserManager.GetPatientTask getPatientTask = new ESUserManager.GetPatientTask();
            getPatientTask.execute(userID);
            Patient patient = getPatientTask.get();
            if (patient != null) {
                Log.d(TAG, "patient exists");
            } else {
                // add the patient and call editPatitentEmail
                userController.addPatient(userID, userPhone, userEmail);
                userController.editPatientEmail(patient, newEmail);

                // assert email is the new email
                patient = userController.getPatient(userID);
                assertEquals(newEmail, patient.getEmailAddress());
            }
        } catch (Exception e) {}
    }
}
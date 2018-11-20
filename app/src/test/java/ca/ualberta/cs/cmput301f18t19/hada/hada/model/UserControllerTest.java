package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserControllerTest {

    @Test
    public void testAddPatient() {
        UserController userList = new UserController();
        assertFalse(userList.userExists("1"));
        userList.addPatient("1", "1-800-TEST","patient1@email.com");
        assertTrue(userList.userExists("1"));

    }

    @Test
    public void testAddCareProvider() {
        UserController userList = new UserController();
        assertFalse(userList.userExists("10"));
        userList.addCareProvider("10", "1-800-TEST",
                "provider1@email.com");
        assertTrue(userList.userExists("10"));
    }

    @Test
    public void testGetPatient() {
        UserController userList = new UserController();
        Patient patient = new Patient("1", "1-800-TEST",
                "provider1@email.com");
        assertFalse(userList.userExists("1"));
        userList.addPatient("1", "1-800-TEST","patient1@email.com");
        assertEquals(patient, userList.getPatient("1"));
    }

    @Test
    public void testGetCareProvider() {
        UserController userList = new UserController();
        Patient patient = new Patient("10", "1-800-TEST",
                "patient1@email.com");
        assertFalse(userList.userExists("10"));
        userList.addPatient("10", "1-800-TEST",
                "provider1@email.com");
        assertEquals(patient, userList.getPatient("10"));
    }

    @Test
    public void testUserExists() {
        UserController userList = new UserController();
        assertFalse(userList.userExists("1"));
        userList.addPatient("1", "1-800-TEST","patient1@email.com");
        assertTrue(userList.userExists("1"));
        assertFalse(userList.userExists("1"));
        userList.addCareProvider("1", "1-800-TEST",
                "provider1@email.com");
        assertTrue(userList.userExists("1"));
    }

    @Test
    public void testAddProblemToList() {
        UserController userList = new UserController();
        userList.addPatient("1", "1-800-TEST","patient1@email.com");
        Problem problem = new Problem();
        userList.addProblemToList(problem);

    }
    // TODO public void testAddPatientToCareProvider() {}
    // TODO public void testEditPatientEmail() {}
    // TODO public void testEditPatientContactNumber() {}
    // TODO public void testEditCareProviderEmail() {}
    // TODO public void testEditCareProviderContactNumber() {}
    // TODO public void testAddRecord() {}
    // TODO public void testGetPatientList() {}

}

package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PatientControllerTest {

    @Test
    public void testIsEmpty() {
        UserController patientList = new UserController();
        assertTrue(patientList.isEmpty());
    }

    @Test
    public void testAddPatient() {
        UserController patientList = new UserController();
        Patient patient = new Patient();
        assertTrue(patientList.isEmpty());
        patientList.addPatient(patient);
        assertFalse(patientList.isEmpty());
    }

    @Test
    public void testGetPatient() {
        UserController patientList = new UserController();
        Patient patient = new Patient();
        patientList.addPatient(patient);
        assertEquals(patient, patientList.getPatient(0));
    }

    @Test
    public void testGetSize() {
        UserController patientList = new UserController();
        Patient patient = new Patient();
        assertEquals(0, patientList.getSize());
        patientList.addPatient(patient);
        assertEquals(1, patientList.getSize());
    }

    @Test
    public void testPatientInList() {
        UserController patientList = new UserController();
        Patient patient1 = new Patient();
        Patient patient2 = new Patient();
        assertFalse(patientList.inList(patient1));
        patientList.addPatient(patient1);
        patientList.addPatient(patient2);
        assertTrue(patientList.inList(patient1));
        assertTrue(patientList.inList(patient2));
    }

    @Test
    public void testGetPosition() {
        UserController patientList = new UserController();
        Patient patient1 = new Patient();
        Patient patient2 = new Patient();
        Patient patient3 = new Patient();
        patientList.addPatient(patient2);
        patientList.addPatient(patient1);
        patientList.addPatient(patient3);
        assertEquals(patient1, patientList.getPatient(1));
        assertEquals(1, patientList.getPos(patient1));
    }

    @Test
    public void testDeletePatient() {
        UserController patientList = new UserController();
        Patient patient1 = new Patient();
        Patient patient2 = new Patient();
        Patient patient3 = new Patient();
        patientList.addPatient(patient1);
        patientList.addPatient(patient2);
        patientList.addPatient(patient3);
        assertTrue(patientList.inList(patient2));
        patientList.deletePatient(patient2);
        assertFalse(patientList.inList(patient2));
    }

    @Test
    public void testInsertPatient() {
        UserController patientList = new UserController();
        Patient patient1 = new Patient();
        Patient patient2 = new Patient();
        Patient patient3 = new Patient();
        patientList.addPatient(patient1);
        patientList.addPatient(patient2);
        assertFalse(patientList.inList(patient3));
        patientList.insertPatient(1, patient3);
        assertTrue(patientList.inList(patient3));
        assertEquals(1, patientList.getPos(patient3));
    }
}

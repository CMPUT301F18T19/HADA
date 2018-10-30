package ca.ualberta.cs.cmput301f18t19.hada.hada;

import org.junit.Test;

import static org.junit.Assert.*;

public class PatientListTest {

    @Test
    public void testEmpty() {
        PatientList patientList = new PatientList();
        assertTrue(patientList.isEmpty());
    }

    @Test
    public void testAddPatient() {
        PatientList patientList = new PatientList();
        Patient patient = new Patient();
        assertTrue(patientList.isEmpty());
        patientList.addPatient(patient);
        assertFalse(patientList.isEmpty());
    }

    @Test
    public void testGetPatient() {
        PatientList patientList = new PatientList();
        Patient patient = new Patient();
        patientList.addPatient(patient);
        assertEquals(patient, patientList.getPatient(0));
    }

    @Test
    public void testGetSize() {
        PatientList patientList = new PatientList();
        Patient patient = new Patient();
        assertEquals(0, patientList.getSize());
        patientList.addPatient(patient);
        assertEquals(1, patientList.getSize());
    }

    @Test
    public void testPatientInList() {
        PatientList patientList = new PatientList();
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
        PatientList patientList = new PatientList();
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
        PatientList patientList = new PatientList();
        Patient patient1 = new Patient();
        Patient patient2 = new Patient();
        Patient patient3 = new Patient();
        patientList.addPatient(patient1);
        patientList.addPatient(patient2);
        patientList.addPatient(patient3);
        assertTrue(patientList.inList(patient2));
        patientList.deletePatient(patientList.getPos(patient2));
        assertFalse(patientList.inList(patient2));
    }

    @Test
    public void testInsertPatient() {
        PatientList patientList = new PatientList();
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

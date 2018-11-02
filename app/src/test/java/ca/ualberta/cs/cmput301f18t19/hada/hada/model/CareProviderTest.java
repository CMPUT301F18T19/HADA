package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import org.junit.Test;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.PatientListController;

import static org.junit.Assert.*;

public class CareProviderTest {

    @Test
    public void testSetUserID(){
        CareProvider careProvider = new CareProvider();
        careProvider.setUserID("Test CP User ID");
        assertEquals("Test CP User ID", careProvider.getUserID());
    }

    @Test
    public void testGetUserID(){
        CareProvider careProvider = new CareProvider();
        careProvider.setUserID("Test User Id");
        String returnedID = careProvider.getUserID();
        assertEquals("Test User Id",returnedID);
    }


    @Test
    public void testSetPhoneNumber(){
        CareProvider careProvider = new CareProvider();
        careProvider.setPhoneNumber("7801234567");
        assertEquals("7801234567", careProvider.getPhoneNumber());
    }

    @Test
    public void testGetPhoneNumber(){
        CareProvider careProvider = new CareProvider();
        careProvider.setPhoneNumber("7801234567");
        String returnedPhoneNumber = careProvider.getPhoneNumber();
        assertEquals("7801234567", returnedPhoneNumber);
    }

    @Test
    public void testSetEmailAddress(){
        CareProvider careProvider = new CareProvider();
        careProvider.setEmailAdress("test@ualberta.ca");
        assertEquals("test@ualberta.ca", careProvider.getEmailAddress());
    }

    @Test
    public void testGetEmailAddress(){
        CareProvider careProvider = new CareProvider();
        careProvider.setEmailAdress("test@ualberta.ca");
        String returnedEmailAddress = careProvider.getEmailAddress();
        assertEquals("test@ualberta.ca", returnedEmailAddress);
    }

    @Test
    public void testSetPatients(){
        CareProvider careProvider = new CareProvider();
        PatientListController patients = new PatientListController();
        Patient patient = new Patient();
        patients.addPatient(patient);
        careProvider.setPatients(patients);
        assertEquals("Should return the PatientListController we added", patients, careProvider.getPatients());
    }

    @Test
    public void testGetPatients(){
        CareProvider careProvider = new CareProvider();
        PatientListController patients = new PatientListController();
        Patient patient = new Patient();
        patients.addPatient(patient);
        careProvider.setPatients(patients);
        assertEquals("Should return the PatientListController we added", patients, careProvider.getPatients());
    }

}
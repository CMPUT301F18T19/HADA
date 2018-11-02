package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import org.junit.Test;

import java.util.ArrayList;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ProblemListController;
import static org.junit.Assert.*;

public class PatientTest {

    @Test
    public void testSetUserID(){
        Patient patient = new Patient();
        patient.setUserID("Test User Id");
        assertEquals("Test User Id",patient.getUserID());
    }

    @Test
    public void testGetUserID(){
        Patient patient = new Patient();
        patient.setUserID("Test User Id");
        String returnedID = patient.getUserID();
        assertEquals("Test User Id",returnedID);
    }


    @Test
    public void testSetPhoneNumber(){
        Patient patient = new Patient();
        patient.setPhoneNumber("7801234567");
        assertEquals("7801234567", patient.getPhoneNumber());
    }

    @Test
    public void testGetPhoneNumber(){
        Patient patient = new Patient();
        patient.setPhoneNumber("7801234567");
        String returnedPhoneNumber = patient.getPhoneNumber();
        assertEquals("7801234567", returnedPhoneNumber);
    }

    @Test
    public void testSetEmailAddress(){
        Patient patient = new Patient();
        patient.setEmailAdress("test@ualberta.ca");
        assertEquals("test@ualberta.ca", patient.getEmailAddress());
    }

    @Test
    public void testGetEmailAddress(){
        Patient patient = new Patient();
        patient.setEmailAdress("test@ualberta.ca");
        String returnedEmailAddress = patient.getEmailAddress();
        assertEquals("test@ualberta.ca", returnedEmailAddress);
    }

    @Test
    public void testSetProblems(){
        Patient patient = new Patient();
        ArrayList<Problem> problems  = new ArrayList<Problem>();
        Problem problem = new Problem();
        patient.setProblems(problems);
        assertEquals("Should be the ProblemListController we set it to", problems, patient.getProblems());
    }

    @Test
    public void testGetProblems(){
        Patient patient = new Patient();
        ArrayList<Problem> problems = new ArrayList<Problem>();
        Problem problem = new Problem();
        patient.setProblems(problems);
        patient.setProblems(problems);
        assertEquals("Should be the ProblemListController we set it to", problems, patient.getProblems());
    }
}
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
    public void testSetProblemList(){
        Patient patient = new Patient();
        ArrayList<Problem> problemList  = new ArrayList<Problem>();
        problemList.add(new Problem());
        patient.setProblemList(problemList);
        assertEquals("Should be the ProblemListController we set it to",
                problemList, patient.getProblemList());
    }

    @Test
    public void testGetProblemList(){
        Patient patient = new Patient();
        ArrayList<Problem> problemList = new ArrayList<Problem>();
        problemList.add(new Problem());
        patient.setProblemList(problemList);
        assertEquals("Should be the ProblemListController we set it to", problemList, patient.getProblemList());
    }

    @Test
    public void testGetProblem(){
        Patient patient = new Patient();
        ArrayList<Problem> problemList  = new ArrayList<Problem>();
        Problem newProblem = new Problem();
        problemList.add(newProblem);
        patient.setProblemList(problemList);       // setProblemList works since it's tested
        assertEquals("The first item in problemList should be the same as newProblem",
                newProblem, patient.getProblem(0));
    }


    @Test
    public void testAddProblem(){
        Patient patient = new Patient();
        Problem newProblem = new Problem();
        patient.addProblem(newProblem);
        assertEquals("The only problem in problemList should equal to the new problem" +
                " that was just added", newProblem, patient.getProblem(0));

    }

    @Test
    public void testSetProblem(){
        Patient patient = new Patient();
        Problem oldProblem = new Problem(); Problem newProblem = new Problem();
        patient.addProblem(oldProblem);
        patient.setProblem(0, newProblem);
        assertEquals("The problem at index 0 should equal to the newProblem just got set",
                newProblem, patient.getProblem(0));

    }

    @Test
    public void testRemoveProblem(){
        Patient patient = new Patient();
        Problem newProblem = new Problem();
        patient.addProblem(newProblem);
        patient.removeProblem(newProblem);
        ArrayList<Problem> emptyList = patient.getProblemList();
        assertTrue(emptyList.isEmpty());
    }
}
package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.utility.Listener;

/**
 * Controller for a given list of records.
 */
public class RecordController {

    private ArrayList<Record> recordList;

    public Boolean addRecord(Record record, int index){
        Patient patient = new UserController().getPatient(LoggedInSingleton.getInstance().getLoggedInID());
        patient.getProblemList().get(index).getRecords().add(record);
        new ESUserManager.AddPatientTask().execute(patient);
        return true;
    }
  
    public ArrayList<Record> getRecordList(int index) {
        ArrayList<Problem> problems = new ProblemController().getProblemList(LoggedInSingleton.getInstance().getLoggedInID());
        Problem problem = problems.get(index);
        recordList = problem.getRecords();

        return recordList;
}
    /**
     * A very specific fundtion for adding comment records to a patient as a CP
     * @author Joe Potentier
    */
    public Boolean addCommentRecord(int patientIndex, int problemIndex, String comment){
        UserController userController = new UserController();
        ArrayList<Patient> patients = userController.getPatientList(LoggedInSingleton.getInstance().getLoggedInID());
        Patient patient = userController.getPatient(patients.get(patientIndex).getUserID());
        Record record = new Record();
        String commentFormatted = "Care provider comment: " + comment;
        record.setTitle(commentFormatted);
        patient.getProblemList().get(problemIndex).getRecords().add(record);
        new ESUserManager.AddPatientTask().execute(patient);
        return true;

    }
}

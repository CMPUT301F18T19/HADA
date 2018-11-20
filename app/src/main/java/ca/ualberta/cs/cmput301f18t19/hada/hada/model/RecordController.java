package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.utility.Listener;

/**
 * Controller for a given list of records.
 */
public class RecordController {

    public Boolean addRecord(Record record, int index){
        Patient patient = new UserController().getPatient(LoggedInSingleton.getInstance().getLoggedInID());
        patient.getProblemList().get(index).getRecords().add(record);
        new ESUserManager.AddPatientTask().execute(patient);
        return true;
    }
}

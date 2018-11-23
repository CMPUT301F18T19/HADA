package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESRecordManager;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * Controller for a given list of records.
 */
public class RecordController {

    public Boolean addRecord(Record record, String parentId){
        new ESRecordManager.AddRecordTask().execute(record);
        return true;
    }

    public ArrayList<Record> getRecordList() {
        ArrayList<Record> recordList = new ArrayList<>();
        recordList.add(new Record());
        return recordList;
}
    /**
     * A very specific fundtion for adding comment records to a patient as a CP
     * @author Joe Potentier
    */
    public Boolean addCommentRecord(int patientIndex, int problemIndex, String comment){

        return true;

    }
}

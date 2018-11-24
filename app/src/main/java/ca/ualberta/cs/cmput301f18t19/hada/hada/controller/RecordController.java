package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;


import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESRecordManager;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * Controller for a given list of records.
 */
public class RecordController {

    public Record getRecord(String fileId){
        try{
            Record record = new ESRecordManager.GetARecordTask().execute(fileId).get();
            return record;
        }catch (Exception e){
            Log.d("getRecord","Couldn't retrieve record from ES");
            e.printStackTrace();
        }
        return null;
    }

    public void addRecord(Record record, String parentId){
        new ESRecordManager.AddRecordTask().execute(record);
    }

    public ArrayList<Record> getRecordList(String parentId) {
        try {
            return new ESRecordManager.GetRecordListTask().execute(parentId).get();
        } catch (Exception e) {
            Log.d("getRecordList","Couldn't retrieve record list from ES");
            e.printStackTrace();
        }
        return null;
    }
    /**
     * A very specific fundtion for adding comment records to a patient as a CP
     * @author Joe Potentier
    */
    public void addCommentRecord(int patientIndex, int problemIndex, String comment){
        //TODO: Refactor record to better support comments from care provider
    }

    public void deleteRecord(String fileId){
        new ESRecordManager.DeleteARecordTask().execute(fileId);
    }
}

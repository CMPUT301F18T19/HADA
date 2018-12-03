package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;


import android.location.Location;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.DBRecordManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESBodyLocationManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESProblemManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESRecordManager;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.SyncManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.BodyLocation;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ContextSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * Controller for a given list of records.
 *
 * @author Anders J.
 * @version 2.0
 */
public class RecordController {

    SyncManager syncManager;

    public RecordController(){
        syncManager = new SyncManager();
        syncManager.syncDB2ES();
    }
    /**
     * Get record for given fileId.
     *
     * @param fileId the file id
     * @return the record
     */
    public Record getRecord(String fileId){
        if(syncManager.isConnectedINET()){
            try{
                Record record = new ESRecordManager.GetARecordTask().execute(fileId).get();
                return record;
            }catch (Exception e){
                Log.d("getRecord","Couldn't retrieve record from ES");
                e.printStackTrace();
            }
            return null;
        }else{
            return new DBRecordManager(ContextSingleton.getInstance().getContext()).getRecord(fileId);
        }

    }

    /**
     * Add a record.
     *
     * @param record   the record
     * @param parentId the parent id
     */
    public void addRecord(Record record, String parentId){
        record.setParentId(parentId);
        new DBRecordManager(ContextSingleton.getInstance().getContext()).addRecord(record);
        syncManager.syncDB2ES();
        //new ESRecordManager.AddRecordTask().execute(record);
    }

    /**
     * Gets record list from given parentId.
     *
     * @param parentId the parent id
     * @return the record list
     */
    public ArrayList<Record> getRecordList(String parentId) {
        if(syncManager.isConnectedINET()){
            try {
                return new ESRecordManager.GetRecordListTask().execute(parentId).get();
            } catch (Exception e) {
                Log.d("getRecordList","Couldn't retrieve record list from ES");
                e.printStackTrace();
            }
            return null;
        }else{
            return new DBRecordManager(ContextSingleton.getInstance().getContext()).getRecordList(parentId);
        }

    }
    /**
     * Add comment record for care provider use.
     *
     * @param parentId the parent id
     * @param comment  the comment
     */
    public void addCommentRecord(String parentId, String comment){
        Record commentRecord = new Record();
        commentRecord.setTitle("-- Care Provider Comment --");
        commentRecord.setComment(comment);
        commentRecord.setParentId(parentId);
        new DBRecordManager(ContextSingleton.getInstance().getContext()).addRecord(commentRecord);
        syncManager.syncDB2ES();
        //new ESRecordManager.AddRecordTask().execute(commentRecord);
    }

    /**
     * Delete record for given fileId.
     *
     * @param fileId the file id
     */
    public void deleteRecord(String fileId){
        new DBRecordManager(ContextSingleton.getInstance().getContext()).deleteRecord(fileId);
        syncManager.syncDB2ES();
        //new ESRecordManager.DeleteARecordTask().execute(fileId);
    }

    /**
     * Edit record title.
     *
     * @param record the record
     * @param title  the title
     */
    public void editRecordTitle(Record record, String title){
        String recordId = record.getFileId();
        new DBRecordManager(ContextSingleton.getInstance().getContext()).editRecordTitle(recordId, title);
        syncManager.syncDB2ES();

        //new ESRecordManager.AddRecordTask().execute(record);
    }

    /**
     * Edit record comment.
     *
     * @param record  the record
     * @param comment the comment
     */
    public void editRecordComment(Record record, String comment){
        String recordId = record.getFileId();
        new DBRecordManager(ContextSingleton.getInstance().getContext()).editRecordComment(recordId, comment);
        //new ESRecordManager.AddRecordTask().execute(record);
    }

    /**
     * Edit record photos.
     */
    public void editRecordPhotos(){
        //TODO: Stub! Implement once we know what we're doing for photos
    }

    /**
     * Edit record body location.
     */
    public void editRecordBodyLocation(){
        //TODO: Stub! Implement once we know what we're doing for bodyLocation
    }

    /**
     * Edit record geo location.
     *
     * @param record      the record
     * @param geoLocation the geo location
     */
    public void editRecordGeoLocation(Record record, LatLng geoLocation){
        String recordId = record.getFileId();
        new DBRecordManager(ContextSingleton.getInstance().getContext()).editRecordGeoLocation(recordId, geoLocation);
        //new ESRecordManager.AddRecordTask().execute(record);
    }

    public ArrayList<Record> searchRecordsWithKeywords(String parentId, String keyword){
        if(syncManager.isConnectedINET()){
            try {
                return new ESRecordManager.SearchUsingKeywordTask().execute(parentId, keyword).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }else{
            return new DBRecordManager(ContextSingleton.getInstance().getContext()).searchRecordWtihKeyword(parentId, keyword);
        }

    }

    public ArrayList<Record> searchRecordsWithGeo(String parentId, String distance, LatLng location){
        if(syncManager.isConnectedINET()){
            String lat = Double.toString(location.latitude);
            String lng = Double.toString(location.longitude);
            try {
                return new ESRecordManager.SearchUsingGeoLocationTask().execute(parentId, distance, lat, lng).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }else{
            return new DBRecordManager(ContextSingleton.getInstance().getContext()).searchRecordWtihGeo(parentId, distance, location);
        }

    }

    public ArrayList<Record> searchRecordsWithBodyLocation(String parentId, String bodyLocation){
        ArrayList<Record> records = new RecordController().getRecordList(parentId);
        ArrayList<Record> validRecords = new ArrayList<>();
        for(Record record : records){
            BodyLocation returnBodyLocation = new BodyLocationController().getABodyLocation(record.getFileId());
            if(returnBodyLocation != null){
                Log.d("searchRecordsWithBodyLocation returnedBodyLoc type", returnBodyLocation.getBodyLocation() + " vs. " + bodyLocation);
                if(returnBodyLocation.getBodyLocation().equals(bodyLocation)){
                    validRecords.add(record);
                }
            }
        }
        return validRecords;
    }
}

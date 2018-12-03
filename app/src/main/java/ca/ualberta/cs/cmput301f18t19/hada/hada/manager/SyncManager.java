package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import java.io.IOException;
import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ContextSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * Manager class responsible for syncing local data to the ElasticSearch server, also contains
 * a method for checking internet connection
 *
 * @author Alex
 * @see DBUserManager
 * @see DBProblemManager
 * @see DBRecordManager
 */
public class SyncManager {
    private DBUserManager DBUserManager;
    private DBProblemManager DBProblemManager;
    private DBRecordManager DBRecordManager;
    private DBPhotoManager DBPhotoManager;



    /**
     * SyncManager constructor
     */
    public SyncManager(){}

    /**
     * instantiates each database manager and sync each un-synced object from database
     * to ElasticSearch server
     */
    public void syncDB2ES() {
        // if it's not connected to internet, no need to sync to server
        if (!isConnectedINET())
            return;

        DBUserManager = new DBUserManager(ContextSingleton.getInstance().getContext());
        DBProblemManager = new DBProblemManager(ContextSingleton.getInstance().getContext());
        DBRecordManager = new DBRecordManager(ContextSingleton.getInstance().getContext());
        DBPhotoManager = new DBPhotoManager(ContextSingleton.getInstance().getContext());

        syncCareProviderTable();
        syncPatientTable();
        syncProblemTable();
        syncRecordTable();
        syncPhotoTable();

    }

    /**
     * sync each un-synced careProvider from database to ElasticSearch server, and set their
     * syncFlag in database to true
     */
    private void syncCareProviderTable(){
        ArrayList<CareProvider> unSyncedList = DBUserManager.getUnSyncedCareProviders();
        for (CareProvider careProvider:unSyncedList) {
            // sync each un-synced careProvider to ES server
            // then set their syncFlag to true(1 in DB)
            new ESUserManager.AddCareProviderTask().execute(careProvider);
            DBUserManager.setCareProviderSyncFlag(careProvider.getUserID(), true);
        }
    }

    /**
     * sync each un-synced patient from database to ElasticSearch server, and set their
     * syncFlag in database to true
     */
    private void syncPatientTable(){
        ArrayList<Patient> unSyncedList = DBUserManager.getUnSyncedPatients();
        for (Patient patient:unSyncedList) {
            // sync each un-synced patient to ES server
            // then set their syncFlag to true(1 in DB)
            new ESUserManager.AddPatientTask().execute(patient);
            DBUserManager.setPatientSyncFlag(patient.getUserID(), true);
        }
    }

    /**
     * sync each un-synced problem from database to ElasticSearch server, and set their
     * syncFlag in database to true
     */
    private void syncProblemTable(){
        ArrayList<Problem> unSyncedList = DBProblemManager.getUnSyncedProblems();
        for (Problem problem:unSyncedList) {
            // sync each un-synced patient to ES server
            // then set their syncFlag to true(1 in DB)
            new ESProblemManager.AddProblemTask().execute(problem);
            DBProblemManager.setProblemSyncFlag(problem.getFileId(), true);
        }
    }

    /**
     * sync each un-synced record from database to ElasticSearch server, and set their
     * syncFlag in database to true
     */
    private void syncRecordTable(){
        ArrayList<Record> unSyncedList = DBRecordManager.getUnSyncedRecords();
        for (Record record:unSyncedList) {
            // sync each un-synced patient to ES server
            // then set their syncFlag to true(1 in DB)
            new ESRecordManager.AddRecordTask().execute(record);
            DBRecordManager.setRecordSyncFlag(record.getFileId(), true);
        }
    }

    /**
     * sync each un-synced photo from database to ElasticSearch server, and set their
     * syncFlag in database to true
     */
    private void syncPhotoTable(){
        ArrayList<Photos> unSyncedList = DBPhotoManager.getUnSyncedPhotos();
        for (Photos photo:unSyncedList) {
            // sync each un-synced patient to ES server
            // then set their syncFlag to true(1 in DB)
            new ESPhotoManager.AddPhotosTask().execute(photo);
            DBPhotoManager.setPhotosSyncFlag(photo.getFileID(), true);
        }
    }


    /**
     * check if the app have internet connection by pinging(send 1 ECHO_REQUEST packet)
     * google's public DNS server, return true if there's a response, false otherwise.
     * Referenced code from
     * https://stackoverflow.com/questions/17717749/check-for-active-internet-connection-android
     * by Musculaa
     */
    public boolean isConnectedINET() {
        Runtime runtime = Runtime.getRuntime();
        try {
            // ES server's respond to ping is too slow, pinging google instead.
            // Process ping = runtime.exec("ping -c 1 cmput301.softwareprocess.es");
            // With timeout set to 1 sec
            // Process ping = runtime.exec("ping -W 1 -c 1 cmput301.softwareprocess.es");
            Process ping = runtime.exec("ping -c 1 8.8.8.8");
            int exitValue = ping.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

}

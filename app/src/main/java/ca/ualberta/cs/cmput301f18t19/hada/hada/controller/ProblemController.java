/* CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 2018-10-29
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */
package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.DBProblemManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESProblemManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.SyncManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ContextSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * A controller that is used when managing problems
 *
 * @author Joseph Potentier, Christopher Penner
 * @version 2.0
 * @see Problem
 */
public class ProblemController {
    SyncManager syncManager;
    /**
     * Instantiates a new Problem controller with an empty list of problems.
     */
    public ProblemController() {
        syncManager = new SyncManager();
    }

    /**
     * Add a problem.
     *
     * @param title       problem title
     * @param date        problem date
     * @param description problem description
     * @param parentId    problem parent id
     */
    public void addProblem(String title, LocalDateTime date, String description, String parentId) {
        Problem problem = new Problem(title, date, description);
        problem.setParentId(parentId);
        new DBProblemManager(ContextSingleton.getInstance().getContext()).addProblem(problem);
        syncManager.syncDB2ES();
    //  new ESProblemManager.AddProblemTask().execute(problem);
    }

    /**
     * Returns a problem given the fileId.
     *
     * @param fileId the file Id of the file to retrieve
     * @return a problem
     */
    public Problem getProblem(String fileId) {
        if(syncManager.isConnectedINET()){
            try {
                Problem problem = new ESProblemManager.GetAProblemTask().execute(fileId).get();
                return problem;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }else{
            return new DBProblemManager(ContextSingleton.getInstance().getContext()).getProblem(fileId);
        }
    }

    /**
     * Get list of problems for given parentId.
     *
     * @param parentId the parent id
     * @return arrayList of problems
     */
    public ArrayList<Problem> getListOfProblems(String parentId){
        if(syncManager.isConnectedINET()){
            try {
                ArrayList<Problem> problems = new ESProblemManager.GetProblemListTask().execute(parentId).get();
                return problems;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }else{
            return new DBProblemManager(ContextSingleton.getInstance().getContext()).getProblemList(parentId);
        }

    }

    /**
     * Deletes a problem given a fileId.
     *
     * @param fileId the problem
     */
    public void deleteProblem(String fileId) {
        new DBProblemManager(ContextSingleton.getInstance().getContext()).deleteProblem(fileId);
        //new ESProblemManager.DeleteProblemTask().execute(fileId);
        //Getting records to delete based on the given parentId.

        //TODO: WE NEED DBRECORDMANAGER TO DO THIS
//        List<Record> recordsToDelete = new RecordController().getRecordList(fileId);
//        for(Record record: recordsToDelete){
//            new RecordController().deleteRecord(record.getFileId());
//        }
        syncManager.syncDB2ES();
    }

    /**
     * Edit problem title.
     *
     * @param problem the problem
     * @param title   the title
     */
    public void editProblemTitle(Problem problem, String title){
        String fileId = problem.getFileId();
        new DBProblemManager(ContextSingleton.getInstance().getContext()).editProblemTitle(fileId, title);
        syncManager.syncDB2ES();
//        problem.setTitle(title);
//        new ESProblemManager.AddProblemTask().execute(problem);
    }

    /**
     * Edit problem date.
     *
     * @param problem the problem
     * @param date    the date
     */
    public void editProblemDate(Problem problem, LocalDateTime date){
        String fileId = problem.getFileId();
        new DBProblemManager(ContextSingleton.getInstance().getContext()).editProblemDate(fileId, date);
        syncManager.syncDB2ES();
//        problem.setDate(date);
//        new ESProblemManager.AddProblemTask().execute(problem);
    }

    /**
     * Edit problem desc.
     *
     * @param problem     the problem
     * @param description the description
     */
    public void editProblemDesc(Problem problem, String description){
        String fileId = problem.getFileId();
        new DBProblemManager(ContextSingleton.getInstance().getContext()).editProblemDesc(fileId, description);
        syncManager.syncDB2ES();
//        problem.setDesc(description);
//        new ESProblemManager.AddProblemTask().execute(problem);
    }


    /**
     * Search problem with keyword.
     *
     * @param parentId the parent id of the problems to search
     * @param keyword  the keyword to search for
     */
    public ArrayList<Problem> searchProblemsWithKeywords(String parentId, String keyword){
        if(syncManager.isConnectedINET()){
            try {
                return new ESProblemManager.SearchUsingKeywordTask().execute(parentId, keyword).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        return new DBProblemManager(ContextSingleton.getInstance().getContext()).searchProblemsWithKeyword(parentId, keyword);
    }

    public ArrayList<Problem> searchProblemWithGeoLocation(String parentId, LatLng location, String distance){
        ArrayList<Problem> problems = getListOfProblems(parentId);
        ArrayList<Problem> validProblems = new ArrayList<>();
        for(Problem problem: problems){
            ArrayList<Record> records = new RecordController().searchRecordsWithGeo(problem.getFileId(), distance, location);
            if(!records.isEmpty()){validProblems.add(problem);}
        }
        return validProblems;
    }

    public ArrayList<Problem> searchProblemWithBodyLocation(String parentId, String bodyLocation){
        ArrayList<Problem> problems = new ProblemController().getListOfProblems(parentId);
        ArrayList<Problem> validProblems = new ArrayList<>();
        for(Problem problem: problems){
            ArrayList<Record> records = new RecordController().searchRecordsWithBodyLocation(problem.getFileId(), bodyLocation);
            if(!records.isEmpty()){validProblems.add(problem);}
        }
        return validProblems;
    }

}

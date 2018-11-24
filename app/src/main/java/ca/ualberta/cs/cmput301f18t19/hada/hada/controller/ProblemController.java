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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESProblemManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESRecordManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;
import ca.ualberta.cs.cmput301f18t19.hada.hada.utility.Listener;

/**
 * A controller with which to manage a list of problems.
 *
 * @author Christopher Penner
 * @version 0.1
 * @see
 */
public class ProblemController {

    /**
     * Instantiates a new Problem controller with an empty list of problems.
     */
    public ProblemController() {

    }

    /**
     * Add problem.
     *
     * @param title       the title
     * @param date        the date
     * @param description the description
     * @param parentId    the parent id
     */
    public void addProblem(String title, LocalDateTime date, String description, String parentId) {
        Problem problem = new Problem(title, date, description);
        problem.setParentId(parentId);
        new ESProblemManager.AddProblemTask().execute(problem);
    }

    /**
     * Returns a problem given an index.
     *
     * @param fileId the file Id that must be retrieved
     * @return the problem
     */
    public Problem getProblem(String fileId) {
        try {
            Problem problem = new ESProblemManager.GetAProblemTask().execute(fileId).get();
            return problem;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get list of problems for given parentId.
     *
     * @param parentId the parent id
     * @return the array list
     */
    public ArrayList<Problem> getListOfProblems(String parentId){
        try {
            ArrayList<Problem> problems = new ESProblemManager.GetProblemListTask().execute(parentId).get();
            return problems;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a given problem from the list.
     *
     * @param fileId the problem
     */
    public void deleteProblem(String fileId) {
        new ESProblemManager.DeleteProblemTask().execute(fileId);
        //Getting records to delete based on the given parentId.
        List<Record> recordsToDelete = new RecordController().getRecordList(fileId);
        for(Record record: recordsToDelete){
            new RecordController().deleteRecord(record.getFileId());
        }
    }

    /**
     * Edit problem title.
     *
     * @param problem the problem
     * @param title   the title
     */
    public void editProblemTitle(Problem problem, String title){
        problem.setTitle(title);
        new ESProblemManager.AddProblemTask().execute(problem);
    }

    /**
     * Edit problem date.
     *
     * @param problem the problem
     * @param date    the date
     */
    public void editProblemDate(Problem problem, LocalDateTime date){
        problem.setDate(date);
        new ESProblemManager.AddProblemTask().execute(problem);
    }

    /**
     * Edit problem desc.
     *
     * @param problem     the problem
     * @param description the description
     */
    public void editProblemDesc(Problem problem, String description){
        problem.setDesc(description);
        new ESProblemManager.AddProblemTask().execute(problem);
    }
}

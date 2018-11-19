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
package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.utility.Listener;

/**
 * A controller with which to manage a list of problems.
 *
 * @author Christopher Penner
 * @version 0.1
 * @see
 */
public class ProblemController {
    private ArrayList<Problem> problemList;
    private ArrayList<Listener> listeners;

    /**
     * Instantiates a new Problem controller with an empty list of problems.
     */
    public ProblemController(){this.problemList = new ArrayList<Problem>();}

    /**
     * Returns a problem given an index.
     *
     * @param index the index
     * @return the problem
     */
    public Problem getProblem(int index) {
        return problemList.get(index);
    }

    /**
     * Adds a problem to the problems list.
     *
     * @param problem the problem
     */
    public void addProblem(Problem problem) {
        problemList.add(problem);
    }

    /**
     * Inserts a problem at a given index in the list.
     *
     * @param index   the index
     * @param problem the problem
     */
    public void insertProblem(int index, Problem problem) {
        problemList.add(index, problem);
    }

    /**
     * Returns a boolean value for if the problem is in the list.
     *
     * @param problem the problem
     * @return the boolean
     */
    public boolean inList(Problem problem) {
        return problemList.contains(problem);
    }

    /**
     * Deletes a given problem from the list.
     *
     * @param problem the problem
     */
    public void deleteProblem(Problem problem) {
        problemList.remove(problem);
    }

    /**
     * Returns a boolean for whether the list is empty.
     *
     * @return the boolean
     */
    public boolean isEmpty() {
        return problemList.isEmpty();
    }

    /**
     * Returns the size of the problem list.
     *
     * @return the size
     */
    public int getSize() {
        return problemList.size();
    }

    /**
     * Returns an index for a problem in the list
     *
     * @param problem the problem
     * @return the index
     */
    public int getPos(Problem problem) {
        return problemList.indexOf(problem);
    }

    /**
     * Notifies any listeners -- likely to be removed
     */
    public void notifyListeners(){
        for (Listener listener : listeners){
            if(listener != null){
                listener.update();}
        }
    }

    /**
     * Add listener. -- likely to be removed
     *
     * @param l the listener
     */
    public void addListener(Listener l){
        listeners.add(l);
    }

    /**
     * Remove listener. -- likely to be removed
     *
     * @param l the listener
     */
    public void removeListener(Listener l){
        listeners.remove(l);
    }

    /**
     * Returns the list of problems associated with the controller.
     *
     * @param patient the patient
     * @return the array list
     */
    public ArrayList<Problem> getProblemList(Patient patient){
        ArrayList<Problem> problems = patient.getProblemList();
        return problems;
    }


}

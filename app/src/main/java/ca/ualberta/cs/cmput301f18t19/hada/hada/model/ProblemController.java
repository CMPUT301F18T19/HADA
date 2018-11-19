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
 * @author Christopher Penner
 * @see
 * @version 0.1
 */
public class ProblemController {
    private ArrayList<Problem> problemList;
    private ArrayList<Listener> listeners;

    ProblemController(){this.problemList = new ArrayList<Problem>();}
    
    public Problem getProblem(int index) {
        return problemList.get(index);
    }

    public void addProblem(Problem problem) {
        problemList.add(problem);
    }

    public void insertProblem(int index, Problem problem) {
        problemList.add(index, problem);
    }

    public boolean inList(Problem problem) {
        return problemList.contains(problem);
    }

    public void deleteProblem(Problem problem) {
        problemList.remove(problem);
    }

    public boolean isEmpty() {
        return problemList.isEmpty();
    }

    public int getSize() {
        return problemList.size();
    }

    public int getPos(Problem problem) {
        return problemList.indexOf(problem);
    }

    public void notifyListeners(){
        for (Listener listener : listeners){
            if(listener != null){
                listener.update();}
        }
    }
    public void addListener(Listener l){
        listeners.add(l);
    }
    public void removeListener(Listener l){
        listeners.remove(l);
    }

    public ArrayList<Problem> getProblemList(Patient patient){
        ArrayList<Problem> problems = patient.getProblemList();
        return problems;
    }


}

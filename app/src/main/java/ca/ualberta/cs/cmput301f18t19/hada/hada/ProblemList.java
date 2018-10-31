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
package ca.ualberta.cs.cmput301f18t19.hada.hada;

import java.util.ArrayList;

/**
 * @author Christopher Penner
 * @see
 * @version 0.1
 */
public class ProblemList {

    private ArrayList<Problem> problemList;

    ProblemList() {
        this.problemList = new ArrayList<Problem>();
    }

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

    public void deleteProblem(int index) {
        Problem problem = problemList.get(index);
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
}

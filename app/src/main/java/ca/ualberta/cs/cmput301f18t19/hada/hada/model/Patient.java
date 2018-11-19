/* CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 2018-10-27
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.model;


import java.util.ArrayList;

/**
 * Object which represents a user of type patient.
 *
 * @author Joseph Potentier-Neal
 * @see User
 */
public class Patient extends User {
    private ArrayList<Problem> problemList = new ArrayList<Problem>();

    /**
     * Instantiates a new Patient with no attributes set.
     */
    public Patient(){
        super();
    }

    /**
     * Instantiates a new Patient given a userID, phone number and email address.
     *
     * @param userID       the user id
     * @param phoneNumber  the phone number
     * @param emailAddress the email address
     */
    public Patient(String userID, String phoneNumber, String emailAddress){
        super(userID, phoneNumber, emailAddress);
    }


    /**
     * Sets the list of problems for a given patient to one supplied.
     *
     * @param newProblemList the new problem list
     */
    /*Following are the problemList operation*/
    public void setProblemList(ArrayList<Problem> newProblemList){
        this.problemList = newProblemList;
    }

    /**
     * Returns the list of problems of the patient.
     *
     * @return the array list
     */
    public ArrayList<Problem> getProblemList(){
        return this.problemList;
    }

    /**
     * Clears the list of problems entirely.
     */
    public void clearProblemList(){
        problemList.clear();
    }

    /**
     * Sets the problem at a given index to be a new problem.
     *
     * @param index      the index
     * @param newProblem the new problem
     */
    /*Following are the individual problem operations*/
    public void setProblem(int index, Problem newProblem){
      problemList.set(index, newProblem);
    }

    /**
     * Returns a problem given an index.
     *
     * @param index the index
     * @return the problem
     */
    public Problem getProblem(int index){
        return problemList.get(index);
    }

    /**
     * Adds a problem to the list given a problem object.
     *
     * @param newProblem the new problem
     */
    public void addProblem(Problem newProblem){
      problemList.add(newProblem);
    }

    /**
     * Removes a problem from the list given the problem object.
     *
     * @param problem the problem
     */
    public void removeProblem(Problem problem){
        problemList.remove(problem);
    }






    //TODO setFullBodyImage() once we know of the data type/structure.
    //public void setFullBodyImage()

}

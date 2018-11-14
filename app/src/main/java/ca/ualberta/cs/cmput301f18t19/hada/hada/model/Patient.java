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
 * @author Joseph Potentier-Neal
 * @see
 * @version
 */
public class Patient extends User {

    public Patient(){
        //Empty constructor... do we need this?
    }

    public Patient(String userID, String phone, String email){
       this.setUserID(userID);
       this.setPhoneNumber(phone);
       this.setEmailAdress(email);
    }
    private ArrayList<Problem> problemList = new ArrayList<Problem>();
    /*Following are the problemList operation*/
    public void setProblemList(ArrayList<Problem> newProblemList){
        this.problemList = newProblemList;
    }

    public ArrayList<Problem> getProblemList(){
        return this.problemList;
    }

    public void clearProblemList(){
        problemList.clear();
    }

    /*Following are the individual problem operations*/
    public void setProblem(int index, Problem newProblem){
      problemList.set(index, newProblem);
    }

    public Problem getProblem(int index){
        return problemList.get(index);
    }

    public void addProblem(Problem newProblem){
      problemList.add(newProblem);
    }

    public void removeProblem(Problem problem){
        problemList.remove(problem);
    }






    //TODO setFullBodyImage() once we know of the data type/structure.
    //public void setFullBodyImage()

}

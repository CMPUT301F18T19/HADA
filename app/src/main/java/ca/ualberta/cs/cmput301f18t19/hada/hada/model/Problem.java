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

import android.util.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Object which represents a problem, ie. an issue to be tracked over time, for a given patient.
 *
 * @author Joe
 * @see Patient
 * @see Record
 */
public class Problem {

    private String title;
    private LocalDateTime date;
    private String description;
    private ArrayList<Record> records = new ArrayList<Record>();

    /**
     * Instantiates a new Problem with no attributes given.
     */
    public Problem(){}

    /**
     * Instantiates a new Problem with a title, date, and description.
     *
     * @param title       the title
     * @param date        the date
     * @param description the description
     */
    public Problem(String title, LocalDateTime date, String description){
        this.title = title;
        this.date = date;
        this.description = description;
    }

    /**
     * Sets the title of the problem to a given string.
     *
     * @param newTitle the new title
     */
    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    /**
     * Returns the title of a problem.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the start date of a problem, stored as a LocalDateTime object.
     *
     * @param newDate the new date
     */
    public void setDate(LocalDateTime newDate) {
        this.date = newDate;
    }

    /**
     * Returns the start date of the problem.
     *
     * @return the date
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Sets the description of the problem.
     *
     * @param newDesc the new desc
     */
    public void setDesc(String newDesc) {
        this.description = newDesc;
    }

    /**
     * Returns the desciption of the problem.
     *
     * @return the desc
     */
    public String getDesc() {
        return this.description;
    }

    /**
     * Sets the list of records associated with a problem to a new one supplied.
     *
     * @param newRecords the new records
     */
    public void setRecords(ArrayList<Record> newRecords){
        this.records = newRecords;
    }

    /**
     * Returns the list of records associated with the problem.
     *
     * @return the array list
     */
    public ArrayList<Record> getRecords(){
        return this.records;
    }


    /**
     * Custom toString that currently displays the title, for easier displaying.
     * @return
     */
    @Override
    public String toString(){
        return this.title;
    }
}
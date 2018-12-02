/*
 *  CMPUT 301 - Fall 2018
 *
 *  Photos.java
 *
 *  11/29/18 9:04 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import java.util.ArrayList;

/**
 * A photos object, which has a fileId, a parentId, and an array of strings which can be made
 * to bitmaps.
 *
 * @author AndersJ , Jason
 * @version 2.0
 * @see Record
 */
public class Photos {
    private String parentId;
    private String fileID;
    private ArrayList<String> bitmaps;

    /**
     * Gets file id.
     *
     * @return the file id
     */
    public String getFileID() {
        return fileID;
    }

    /**
     * Sets file id.
     *
     * @param fileID the file id
     */
    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    /**
     * Gets parent id.
     *
     * @return the parent id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Sets parent id.
     *
     * @param parentId the parent id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * Get bitmaps array list.
     *
     * @return the array list
     */
    public ArrayList<String> getBitmaps(){
        return this.bitmaps;
    }

    /**
     * Set bitmaps.
     *
     * @param bitmaps the bitmaps
     */
    public void setBitmaps(ArrayList<String> bitmaps){
        this.bitmaps = bitmaps;
    }
}

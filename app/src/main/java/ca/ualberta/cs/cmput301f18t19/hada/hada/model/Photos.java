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

public class Photos {
    private String parentId;
    private String fileID;
    private ArrayList<String> uriPhotos;
    private ArrayList<String> httpPhotos;

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ArrayList<String> getUriPhotos() {
        return uriPhotos;
    }

    public void setUriPhotos(ArrayList<String> uriPhotos) {
        this.uriPhotos = uriPhotos;
    }

    public ArrayList<String> getHttpPhotos() {
        return httpPhotos;
    }

    public void setHttpPhotos(ArrayList<String> httpPhotos) {
        this.httpPhotos = httpPhotos;
    }
}

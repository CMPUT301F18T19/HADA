/*
 *  CMPUT 301 - Fall 2018
 *
 *  BodyLocation.java
 *
 *  11/29/18 9:08 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import java.util.ArrayList;

public class BodyLocation {
    private String bodyLocation;
    private ArrayList<Integer> coords;
    private String refImageFileId;
    private String parentId;
    private String fileID;

    public BodyLocation(){}

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public ArrayList<Integer> getCoords() {
        return coords;
    }

    public void setCoords(ArrayList<Integer> coords) {
        this.coords = coords;
    }
    public String getRefImageFileId(){
        return refImageFileId;
    }
    public void setRefImageFileId(String refImage){
        this.refImageFileId = refImage;
    }

    public String getBodyLocation() {
        return bodyLocation;
    }

    public void setBodyLocation(String bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentID) {
        this.parentId = parentID;
    }
}

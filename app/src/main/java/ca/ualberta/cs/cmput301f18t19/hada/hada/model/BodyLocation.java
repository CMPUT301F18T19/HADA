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

public class BodyLocation {
    private String photoUri;
    private String photoHTTP;
    private String bodyLocation;
    private String parentID;

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getPhotoHTTP() {
        return photoHTTP;
    }

    public void setPhotoHTTP(String photoHTTP) {
        this.photoHTTP = photoHTTP;
    }

    public String getBodyLocation() {
        return bodyLocation;
    }

    public void setBodyLocation(String bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }
}

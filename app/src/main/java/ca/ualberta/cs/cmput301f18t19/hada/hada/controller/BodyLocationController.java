/*
 *  CMPUT 301 - Fall 2018
 *
 *  BodyLocationController.java
 *
 *  12/1/18 6:00 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESBodyLocationManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.BodyLocation;

public class BodyLocationController {

    public BodyLocation getABodyLocation(String parentId, String bodyLocationString){
        try {
            return new ESBodyLocationManager.GetABodyLocationTask().execute(parentId, bodyLocationString).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addBodyLocation(BodyLocation bodyLocation ,String parentId){
        bodyLocation.setParentId(parentId);
        new ESBodyLocationManager.AddBodyLocationTask().execute(bodyLocation);
    }
}

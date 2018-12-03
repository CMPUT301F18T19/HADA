/*
 *  CMPUT 301 - Fall 2018
 *
 *  ESBodyLocationManager.java
 *
 *  30/11/18 12:42 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.BodyLocation;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;
import io.searchbox.client.JestResult;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;


public class ESBodyLocationManager extends ESManager{
    /**
     * Task which adds a Record to the server, given a Record object.
     */
    public static class AddBodyLocationTask extends AsyncTask<BodyLocation, Void, Void> {
        @Override
        protected Void doInBackground(BodyLocation... params) {
            setClient();
            for(BodyLocation bodyLocation:params){
                //Assign a file Id if the object does not contain one.
                String fileId;
                if(bodyLocation.getFileID() == null) {
                    fileId = UUID.randomUUID().toString();
                    bodyLocation.setFileID(fileId);
                }
                try {
                    Index index = new Index.Builder(bodyLocation)
                            .index(teamIndex)
                            .type("bodylocation")
                            .id(bodyLocation.getFileID())
                            .refresh(true)
                            .build();
                    DocumentResult result = client.execute(index);
                    Log.d("AddBodyLocation", index.getURI());
                    Log.d("AddBodyLocation",result.getJsonString());
                    if (result.isSucceeded()) {
                        Log.d("AddBodyLocation", "bodylocation successfully added.");
                    }
                } catch (IOException e) {
                    Log.d("AddBodyLocation", "Failed to execute while adding a record.");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * Deletes a body location for a given fileId
     */
    public static class DeleteABodyLocationTask extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {
            setClient();
            for (String fileId : params) {
                String query = "{\"query\": {\"match\": {\"fileId\": \"" + fileId + "\"}}}";
                DeleteByQuery delete = new DeleteByQuery.Builder(query)
                        .addIndex(teamIndex)
                        .addType("bodylocation")
                        .build();
                try {
                    JestResult result = client.execute(delete);
                    if(result.isSucceeded()) {
                        Log.d("DeleteBodyLocationTask", "Body location deleted deleted.");
                    }
                    else{
                        Log.d("DeleteBodyLocationTask", "Problem deletion failed.");
                    }
                } catch (IOException e) {
                    Log.d("DeleteBodyLocationTask", "IOException");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }


    /**
     * Task which loads a Record from the server when given a fileId.
     */
    public static class GetABodyLocationTask extends AsyncTask<String, Void, BodyLocation> {
        @Override
        protected BodyLocation doInBackground(String... params) {
            setClient();
            String parentId = params[0];
            String query = "{\n" +
                    "\t\"query\" : {\n" +
                    "\t\t\"bool\" : {\n" +
                    "\t\t\t\"must\" : {\n" +
                    "\t\t\t\t\"match\" : {\n" +
                    "\t\t\t\t\t\"parentId\" : \""+parentId+"\"\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t}\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}";
            BodyLocation matchingLocation = null;
            Log.d("GetABodyLocationTask Query: ", query);

            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("bodylocation")
                    .build();
            try {
                JestResult result = client.execute(search);

                if (result.isSucceeded()) {
                    List<BodyLocation> results;
                    results = result.getSourceAsObjectList(BodyLocation.class);
                    for(BodyLocation returnedBodyLocation: results){
                        Log.d("GetABodyLocationTask Results: ", returnedBodyLocation.toString());
                    }
                    if(results.size() > 0) {
                        matchingLocation = results.get(0);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return matchingLocation;
        }
    }

}

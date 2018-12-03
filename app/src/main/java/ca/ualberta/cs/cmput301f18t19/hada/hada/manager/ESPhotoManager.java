/*
 *  CMPUT 301 - Fall 2018
 *
 *  ESPhotoManager.java
 *
 *  12/1/18 1:09 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.os.AsyncTask;
import android.util.Log;

import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos;
import io.searchbox.client.JestResult;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

public class ESPhotoManager extends ESManager {

    public static class AddPhotosTask extends AsyncTask<Photos, Void, Void> {
        @Override
        protected Void doInBackground(Photos... params) {
            setClient();
            for (Photos photo : params) {
                //Assign a file Id if the object does not contain one.
                String fileId;
                if (photo.getFileID() == null) {
                    fileId = UUID.randomUUID().toString();
                    photo.setFileID(fileId);
                }
                try {
                    Index index = new Index.Builder(photo)
                            .index(teamIndex)
                            .type("Photos")
                            .id(photo.getFileID())
                            .refresh(true)
                            .build();
                    DocumentResult result = client.execute(index);
                    Log.d("AddPhotos", index.getURI());
                    Log.d("AddPhotos", result.getJsonString());
                    if (result.isSucceeded()) {
                        Log.d("AddPhotos", "Record successfully added.");
                    }
                } catch (IOException e) {
                    Log.d("AddPhotos", "Failed to execute while adding a record.");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * Deletes a body location for a given fileId
     */
    public static class DeletePhotosTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            setClient();
            for (String fileId : params) {
                String query = "{\"query\": {\"match\": {\"fileId\": \"" + fileId + "\"}}}";
                DeleteByQuery delete = new DeleteByQuery.Builder(query)
                        .addIndex(teamIndex)
                        .addType("Photos")
                        .build();
                try {
                    JestResult result = client.execute(delete);
                    if (result.isSucceeded()) {
                        Log.d("DeletePhotosTask", "Photo deleted deleted.");
                    } else {
                        Log.d("DeletePhotosTask", "Problem deletion failed.");
                    }
                } catch (IOException e) {
                    Log.d("DeletePhotosTask", "IOException");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }


    /**
     * Task which loads a Record from the server when given a fileId.
     */
    public static class GetPhotoListTask extends AsyncTask<String, Void, ArrayList<Photos>> {
        @Override
        protected ArrayList<Photos> doInBackground(String... params) {
            setClient();
            String parentId = params[0];
            String query = "{\"query\": {\"match\": {\"parentId\": \"" + parentId + "\"}}}";
            ArrayList<Photos> matchingPhotos = new ArrayList<>();
            Log.d("GetPhotoTask query", query);
            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("Photos")
                    .build();
            try {
                JestResult result = client.execute(search);

                if (result.isSucceeded()) {
                    List<Photos> results;
                    results = result.getSourceAsObjectList(Photos.class);
                    for (Photos photo : results) {
                        Log.d("GetProblemListTask", "Problem loaded:" + photo.toString());
                    }
                    matchingPhotos.addAll(results);
                }
            } catch (IOException e) {
                Log.d("GetAProblemTask", "IOException");
                e.printStackTrace();
            }
            return matchingPhotos;
        }

    }
}

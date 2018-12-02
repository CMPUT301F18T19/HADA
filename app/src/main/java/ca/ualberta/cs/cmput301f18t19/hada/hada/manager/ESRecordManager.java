/*
 *  CMPUT 301 - Fall 2018
 *
 *  ESRecordManager.java
 *
 *  11/23/18 12:50 PM
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

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;
import io.searchbox.client.JestResult;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

/**
 * A manager that saves and loads Records to ES.
 *
 * @author Joe
 * @version 1
 * @see ESManager
 * @see Record
 */
public class ESRecordManager extends ESManager{

    /**
     * Task which adds a Record to the server, given a Record object.
     */
    public static class AddRecordTask extends AsyncTask<Record, Void, Void> {
        @Override
        protected Void doInBackground(Record... params) {
            setClient();
            for(Record record:params){
                //Assign a file Id if the object does not contain one.
                String fileId;
                if(record.getFileId() == null) {
                    fileId = UUID.randomUUID().toString();
                    record.setFileId(fileId);
                }
                try {
                    Index index = new Index.Builder(record)
                            .index(teamIndex)
                            .type("record")
                            .id(record.getFileId())
                            .refresh(true)
                            .build();
                    DocumentResult result = client.execute(index);
                    Log.d("AddRecordTask index", index.getURI());
                    Log.d("AddRecordTask What is result",result.getJsonString());
                    if (result.isSucceeded()) {
                        Log.d("AddRecordTask", "Record successfully added.");
                    }
                } catch (IOException e) {
                    Log.d("AddRecordTask", "Failed to execute while adding a record.");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * Deletes a record for a given fileId
     */
    public static class DeleteARecordTask extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {
            setClient();
            for (String fileId : params) {
                String query = "{\"query\": {\"match\": {\"fileId\": \"" + fileId + "\"}}}";
                DeleteByQuery delete = new DeleteByQuery.Builder(query)
                        .addIndex(teamIndex)
                        .addType("record")
                        .build();
                try {
                    JestResult result = client.execute(delete);
                    if(result.isSucceeded()) {
                        Log.d("DeleteRecordTask", "Record deleted.");
                    }
                    else{
                        Log.d("DeleteRecordTask", "Record deletion failed.");
                    }
                } catch (IOException e) {
                    Log.d("DeleteRecordTask", "IOException");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }


    /**
     * Task which loads a Record from the server when given a fileId.
     */
    public static class GetARecordTask extends AsyncTask<String, Void, Record> {
        @Override
        protected Record doInBackground(String... params) {
            setClient();
            String query = "{\"query\": {\"match\": {\"fileId\": \"" + params[0] + "\"}}}";
            Record matchingRecord = null;
            Log.d("GetARecordTask Query: ", query);

            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("record")
                    .build();
            try {
                JestResult result = client.execute(search);

                if (result.isSucceeded()) {
                    List<Record> results;
                    results = result.getSourceAsObjectList(Record.class);
                    for(Record record: results){
                        Log.d("GetARecordTask Results: ", record.toString());
                    }
                    matchingRecord = results.get(0);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return matchingRecord;
        }
    }


    /**
     * Task which loads all records from server with a matching parentId.
     */
    public static class GetRecordListTask extends AsyncTask<String, Void, ArrayList<Record>> {
        @Override
        protected ArrayList<Record> doInBackground(String... params){
            setClient();

            String query = "{\"query\": {\"match\": {\"parentId\": \"" + params[0] + "\"}}}";
            Log.d("GetRecordListTask Query: ", query);
            //String query = "{\"query\": {\"match\": {\"patient\": {\"userID\": "+ params[0] + "\"}}}}";

            //We use an array to store all matching records given the userID -- in case we get duplicates
            ArrayList<Record> matchingRecords = new ArrayList<>();
            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("record")
                    .build();

            try {
                JestResult result = client.execute(search);

                if (result.isSucceeded()) {
                    List<Record> results;
                    results = result.getSourceAsObjectList(Record.class);

                    for(Record record:results) {
                        //Log.d("GetRecordListTask Results: ", record.toString());
                    }
                    matchingRecords.addAll(results);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(Record record: matchingRecords){
                //Log.d("GetRecordListTask Records: ", record.toString());
            }
            return matchingRecords;
            }
        }

    public static class SearchUsingKeywordTask extends AsyncTask<String, Void, ArrayList<Record>> {
        @Override
        protected ArrayList<Record> doInBackground(String... params) {
            setClient();
            String parentId = params[0];
            String keywords = params[1];
            String query = "{\n" +
                    "\t\"query\": {\n" +
                    "\t\t\"bool\": {\n" +
                    "\t\t\t\"must\": {\n" +
                    "\t\t\t\t\"match\": {\"parentId\" : \""+parentId+"\"}\n" +
                    "\t\t\t\t},\n" +
                    "\t\t\t\"should\": [\n" +
                    "\t\t\t\t{\n" +
                    "\t\t\t\t\t\"match\" : {\"comment\": \""+keywords+"\"}\n" +
                    "\t\t\t\t},\n" +
                    "\t\t\t\t{\n" +
                    "\t\t\t\t\t\"match\" : {\"title\": \""+keywords+"\"}\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t],\n" +
                    "\t\t\t\"minimum_should_match\" : 1\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}";
            Log.d("SearchUsingKeywordTask Query", query);
            ArrayList<Record> matchingRecords = new ArrayList<>();
            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("record")
                    .build();
            try{
                JestResult result = client.execute(search);

                if(result.isSucceeded()){
                    List<Record> results;
                    results = result.getSourceAsObjectList(Record.class);
                    matchingRecords.addAll(results);
                }
            }catch(IOException e){
                Log.d("SearchUsingKeywordTask", "IOException");
                e.printStackTrace();
            }
            return matchingRecords;
        }
    }

    public static class SearchUsingGeoLocationTask extends AsyncTask<String, Void, ArrayList<Record>> {
        @Override
        protected ArrayList<Record> doInBackground(String... params) {
            setClient();
            String parentId = params[0];
            String distance = params[1] + "km";
            String lat = params[2];
            String lng = params[3];
            String query = "{\n" +
                    "\t\"query\": {\n" +
                    "\t\t\"filtered\": {\n" +
                    "\t\t\t\"query\": {\n" +
                    "\t\t\t\t\"match\": {\"parentId\" : \""+parentId+"\"}\n" +
                    "\t\t\t},\n" +
                    "\t\t\t\"filter\" : {\n" +
                    "\t\t\t\t\"geo_distance\" : {\n" +
                    "\t\t\t\t\t\"distance\" : \""+distance+"\",\n" +
                    "\t\t\t\t\t\"location\" : {\n" +
                    "\t\t\t\t\t\t\"lat\" : "+lat+",\n" +
                    "\t\t\t\t\t\t\"lon\" : "+lng+"\n" +
                    "\t\t\t\t\t}\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t}\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}";
            Log.d("SearchUsingGeoLocationTask Query", query);
            ArrayList<Record> matchingRecords = new ArrayList<>();
            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("record")
                    .build();
            try{
                JestResult result = client.execute(search);

                if(result.isSucceeded()){
                    List<Record> results;
                    results = result.getSourceAsObjectList(Record.class);
                    for(Record record: results){
                        Log.d("SearchUsingGeoLocationTask", record.toString());
                    }
                    matchingRecords.addAll(results);
                }
            }catch(IOException e){
                Log.d("SearchUsingGeoLocationTask", "IOException");
                e.printStackTrace();
            }
            return matchingRecords;
        }
    }

    public static class SearchUsingBodyLocationTask extends AsyncTask<String, Void, ArrayList<Record>> {
        @Override
        protected ArrayList<Record> doInBackground(String... params) {
            setClient();
            String parentId = params[0];
            String bodyLocation = params[1];
            String query = "{\n" +
                    "\t\"query\" : {\n" +
                    "\t\t\"bool\" : {\n" +
                    "\t\t\t\"must\" : {\n" +
                    "\t\t\t\t\"match\" : {\n" +
                    "\t\t\t\t\t{\"parentId\" : \""+parentId+"\"},\n" +
                    "\t\t\t\t\t{\"bodyLocation\" : \""+bodyLocation+"\"}\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t}\n" +
                    "\t\t}\n" +
                    "\t}\n" +
                    "}";
            Log.d("SearchUsingKeywordTask Query", query);
            ArrayList<Record> matchingRecords = new ArrayList<>();
            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("record")
                    .build();
            try{
                JestResult result = client.execute(search);

                if(result.isSucceeded()){
                    List<Record> results;
                    results = result.getSourceAsObjectList(Record.class);
                    matchingRecords.addAll(results);
                }
            }catch(IOException e){
                Log.d("SearchUsingKeywordTask", "IOException");
                e.printStackTrace();
            }
            return matchingRecords;
        }
    }
}

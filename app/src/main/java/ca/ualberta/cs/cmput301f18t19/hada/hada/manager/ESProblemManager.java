package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import io.searchbox.client.JestResult;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

/**
 * A manager that saves and loads problems to and from ES.
 *
 * @author AndersJ
 * @version 1
 * @see Problem
 * @see ESManager
 */
public class ESProblemManager extends ESManager{

    /**
     * Asyc task for adding a problem to ES in the background.
     */
    public static class AddProblemTask extends AsyncTask<Problem, Void, Void>{
        @Override
        protected Void doInBackground(Problem... params){
            setClient();
            for(Problem problem : params){
                //Assign a file Id if the object does not contain one.
                String fileId;
                if(problem.getFileId() == null) {
                     fileId = UUID.randomUUID().toString();
                     problem.setFileId(fileId);
                }
                try{
                    Index index = new Index.Builder(problem)
                            .index(teamIndex)
                            .type("problem")
                            .id(problem.getFileId())
                            .refresh(true)
                            .build();
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()){
                        Log.d("AddProblemTask", "Problem was successfully added.");
                    }else{
                        Log.d("AddProblemTask", "Failed to add problem to server.");
                    }
                }catch (IOException e){
                    Log.d("AddProblemTask", "IOException");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * AsyncTask for retrieving a list of all problems with a matching parentId.
     */
    public static class GetProblemListTask extends AsyncTask<String, Void, ArrayList<Problem>>{
        @Override
        protected ArrayList<Problem> doInBackground(String... params){
            setClient();
            String parentId = params[0];
            String query = "{\"query\": {\"match\": {\"parentId\": \"" + parentId + "\"}}}";
            ArrayList<Problem> matchingProblems = new ArrayList<>();
            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("problem")
                    .build();
            try{
                JestResult result = client.execute(search);

                if(result.isSucceeded()){
                    List<Problem> results;
                    results = result.getSourceAsObjectList(Problem.class);
                    for(Problem problem: results){
                        Log.d("GetProblemListTask", "Problem loaded:" + problem.toString());
                    }
                    matchingProblems.addAll(results);
                }
            }catch(IOException e){
                Log.d("GetProblemListTask", "IOException");
                e.printStackTrace();
            }
            return matchingProblems;
        }
    }

    /**
     * AsyncTask which retrieves a problem given its fileId.
     * <p>
     * RETURNS A NULL OBJECT IF IT CANNOT FIND THE OBJECT
     */
    public static class GetAProblemTask extends AsyncTask<String, Void, Problem>{
        @Override
        protected Problem doInBackground(String... params){
            setClient();
            String fileId = params[0];
            String query = "{\"query\": {\"match\": {\"fileId\": \"" + fileId + "\"}}}";
            Problem matchingProblem = null;
            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("problem")
                    .build();
            try{
                JestResult result = client.execute(search);

                if(result.isSucceeded()){
                    List<Problem> results;
                    results = result.getSourceAsObjectList(Problem.class);
                    for(Problem problem: results){
                        Log.d("GetProblemListTask", "Problem loaded:" + problem.toString());
                    }
                    matchingProblem = results.get(0);
                }
            }catch(IOException e){
                Log.d("GetAProblemTask", "IOException");
                e.printStackTrace();
            }
            return matchingProblem;
        }
    }

    /**
     * Deletes a problem given its fileId.
     */
    public static class DeleteProblemTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            setClient();
            for (String fileId : params) {
                String query = "{\"query\": {\"match\": {\"fileId\": \"" + fileId + "\"}}}";
                DeleteByQuery delete = new DeleteByQuery.Builder(query)
                        .addIndex(teamIndex)
                        .addType("problem")
                        .build();
                try {
                    JestResult result = client.execute(delete);
                    if(result.isSucceeded()) {
                        Log.d("DeleteProblemTask", "Problem deleted.");
                    }
                    else{
                        Log.d("DeleteProblemTask", "Problem deletion failed.");
                    }
                } catch (IOException e) {
                    Log.d("DeleteAProblemTask", "IOException");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static class SearchUsingKeywordTask extends AsyncTask<String, Void, ArrayList<Problem>> {
        @Override
        protected ArrayList<Problem> doInBackground(String... params) {
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
                    "\t\t\t\t\t\"match\" : {\"description\": \""+keywords+"\"}\n" +
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
            ArrayList<Problem> matchingProblems = null;
            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("problem")
                    .build();
            try{
                JestResult result = client.execute(search);

                if(result.isSucceeded()){
                    List<Problem> results;
                    results = result.getSourceAsObjectList(Problem.class);
                    matchingProblems.addAll(results);
                }
            }catch(IOException e){
                Log.d("SearchUsingKeywordTask", "IOException");
                e.printStackTrace();
            }
            return matchingProblems;
        }
    }
}

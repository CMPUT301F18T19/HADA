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
 * The type Es problem manager.
 */
public class ESProblemManager extends ESManager{

    /**
     * The type Add problem task.
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
     * The type Get problem list based on given parentId
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
     * The type Get a problem task.
     *
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
     * The type Delete problem task.
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
}

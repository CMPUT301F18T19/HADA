package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.os.AsyncTask;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

public class ESProblemManager {
    static JestDroidClient client = null;
    static String teamIndex = "cmput301f18t19test";

    public static void setClient(){
        if (client==null){
            DroidClientConfig config = new DroidClientConfig
                    .Builder("http://cmput301.softwareprocess.es:8080/cmput301f18t19test")
                    .build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    public static class AddProblemsTask extends AsyncTask<Problem, Void, Void> {
        @Override
        protected Void doInBackground(Problem... params) {
            setClient();
            Problem problem = params[0];
            Index index = new Index.Builder(problem)
                    .index(teamIndex)
                    .type("HARDCODE")
                    .build();
            try {
                DocumentResult result = client.execute(index);

                if (result.isSucceeded()){

                }
            } catch (IOException e) {
            }
            return null;
        }
    }

    public static class GetProblemsTask extends AsyncTask<String, Void, ArrayList<Problem>> {
        @Override
        protected ArrayList<Problem> doInBackground(String... params) {
            setClient();
            ArrayList<Problem> problems = new ArrayList<Problem>();
            Search search = new Search.Builder(params[0])
                    .addIndex(teamIndex)
                    .addType("HARDCODE")
                    .build();

            try {
                JestResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Problem> problemList;
                    problemList = result.getSourceAsObjectList(Problem.class);
                    problems.addAll(problemList);

                }

            } catch (IOException e) {
            }

            return problems;
        }


    }
}

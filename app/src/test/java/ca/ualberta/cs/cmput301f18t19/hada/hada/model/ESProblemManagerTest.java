package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

import static org.junit.Assert.*;


public class ESProblemManagerTest {
    @Test
    public void testSaveProblem() {
        Problem problem = new Problem();
        new ESProblemManager.AddProblemsTask().execute(problem);

        DroidClientConfig config = new DroidClientConfig
                .Builder("http://cmput301.softwareprocess.es:8080/cmput301f18t19test")
                .build();

        JestClientFactory factory = new JestClientFactory();
        factory.setDroidClientConfig(config);
        JestDroidClient client = (JestDroidClient) factory.getObject();
        Search search = new Search.Builder("")
                .addIndex("HARDCODE")
                .addType("HARDCODE")
                .build();

        try {
            JestResult result = client.execute(search);
            assertTrue(result.isSucceeded());
        }
        catch (IOException e){

        }
    }

    @Test
    public void testGetProblem() {
        Problem problem = new Problem();
        ArrayList<Problem> problems = new ArrayList<Problem>();
        new ESProblemManager.AddProblemsTask().execute(problem);

        try {
            problems = new ESProblemManager.GetProblemsTask().execute("").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(problems.isEmpty());

    }
}

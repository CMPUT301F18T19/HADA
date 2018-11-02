package ca.ualberta.cs.cmput301f18t19.hada.hada;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.junit.Test;

import java.io.IOException;
import java.lang.annotation.Target;
import java.util.ArrayList;

import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

import static org.junit.Assert.*;


public class ListManagerProblemsTest {
    @Test
    public void testSaveProblem() {
        Problem problem = new Problem();
        ProblemList problems = new ProblemList();
        assertEquals(true, problems.isEmpty());
        new ListManagerProblems.AddProblemsTask().execute(problem);
        assertEquals(false, problems.isEmpty());
        assertEquals(1, problems.getSize());


        Problem problem = new Problem();
        new ListManagerProblems().AddProblemsTask.execute(problem);
        DroidClientConfig config = new DroidClientConfig
                .Builder("")
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

    public void testGetProblem() {
        Problem problem = new Problem();
        ArrayList<Problem> problems = new ArrayList<Problem>();

        JestClientFactory factory = new JestClientFactory();
        factory.setDroidClientConfig(config);
        JestDroidClient client = (JestDroidClient) factory.getObject();

        new ListManagerProblems().AddProblemsTask.execute(problem);
        try {
            JestResult result = client.execute()
        }

        new ListManagerProblems.GetProblemsTask().execute();

    }
}

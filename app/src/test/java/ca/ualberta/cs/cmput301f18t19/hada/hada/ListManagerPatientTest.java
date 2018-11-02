package ca.ualberta.cs.cmput301f18t19.hada.hada;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ListManagerPatientTest {
    @Test
    public void testSavePatient() {
        Patient patient = new Patient();
        new ListManagerPatient.AddPatientTask().execute(patient);

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
    public void testGetPatient() {
        Patient patient = new Patient();
        ArrayList<Patient> patients = new ArrayList<Patient>();
        new ListManagerPatient.AddPatientTask().execute(patient);

        try {
            patients = new ListManagerPatient.GetPatientsTask().execute("").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(patients.isEmpty());

    }
}
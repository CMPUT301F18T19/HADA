package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

/**
 * Class resposible for saving and loading users from the ElasticSearch server.
 *
 * @version 1.0
 * @author Anders, Joe
 * @see Patient
 * @see CareProvider
 */
public class ESUserManager {
    /**
     * The JestDroidClient which is built with setClient() each time ESUserManager
     * performs an action.
     */
    static JestDroidClient client = null;
    /**
     * The index of the ElasticSearch node -- currently set to the test one while app is built.
     */
    static String teamIndex = "cmput301f18t19test";

    /**
     * Sets the client to connect to the ElasticSearch node with JestDroid methods.
     */
    public static void setClient(){
        if(client == null){
            DroidClientConfig config = new DroidClientConfig
                    .Builder("http://cmput301.softwareprocess.es:8080/")
                    .build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    /**
     * Task which adds a Patient to the server, given a Patient object.
     */
    public static class AddPatientTask extends AsyncTask<Patient, Void, Void>{
        @Override
        protected Void doInBackground(Patient... params) {
            setClient();
            Patient patient = params[0];
            Log.d("newPatient", "Id = "+ patient.getUserID() + " Phone = " + patient.getPhoneNumber() +" Email = "+ patient.getEmailAddress());
            try {
                Index index = new Index.Builder(patient)
                        .index(teamIndex)
                        .type("patient")
                        .id(patient.getUserID())
                        .refresh(true)
                        .build();
                DocumentResult result = client.execute(index);
                Log.d("index", index.getURI());
                Log.d("What is result",result.getJsonString());
                if (result.isSucceeded()) {
                    Log.d("AddPatientTask", "We did it boys");
                } else {
                    Log.d("AddPatientTask", "Could not add patient");
                }
            } catch (IOException e) {
                Log.d("AddPatientTask", "Failed to execute");
            }

            return null;
        }
    }

    /**
     * Task which loads a patient from the server when given a userID.
     */
    public static class GetPatientTask extends AsyncTask<String, Void, Patient> {
        @Override
        protected Patient doInBackground(String... params){
            setClient();

            String query = "{\"query\": {\"match\": {\"userID\": \"" + params[0] + "\"}}}";
            Log.d("Query", query);
            //String query = "{\"query\": {\"match\": {\"patient\": {\"userID\": "+ params[0] + "\"}}}}";

            //We use an array to store all matching patients given the userID -- in case we get duplicates
            ArrayList<Patient> matchingPatients = new ArrayList<Patient>();
            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("patient")
                    .build();

            try {
                JestResult result = client.execute(search);

                if (result.isSucceeded()) {
                    List<Patient> results;
                    results = result.getSourceAsObjectList(Patient.class);

                    for(Patient patient:results) {
                        Log.d("bleh", results.toString());
                    }
                    matchingPatients.addAll(results);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(Patient i: matchingPatients){
                Log.d("Results", i.getUserID());
            }
            if(matchingPatients.size() == 0){
                return null;
            }
            else {
                return matchingPatients.get(0);
            }
            }
        }

    /**
     * Task which adds a Care Provider to the server, given a CareProvider object.
     */
    public static class AddCareProviderTask extends AsyncTask<CareProvider, Void, Void>{
        @Override
        protected Void doInBackground(CareProvider... params) {
            setClient();
            CareProvider careProvider = params[0];
            Log.d("newPatient", "Id = "+ careProvider.getUserID() + " Phone = " + careProvider.getPhoneNumber() +" Email = "+ careProvider.getEmailAddress());
            try {
                Index index = new Index.Builder(careProvider)
                        .index(teamIndex)
                        .type("careprovider")
                        .id(careProvider.getUserID())
                        .refresh(true)
                        .build();
                DocumentResult result = client.execute(index);
                Log.d("index", index.getURI());
                Log.d("What is result",result.getJsonString());
                if (result.isSucceeded()) {
                    Log.d("AddPatientTask", "We did it boys");
                } else {
                    Log.d("AddPatientTask", "Could not add patient");
                }
            } catch (IOException e) {
                Log.d("AddPatientTask", "Failed to execute");
            }

            return null;
        }
    }

    /**
     * Task which retrieves a CareProvider object from the server given a userID.
     */
    public static class GetCareProviderTask extends AsyncTask<String, Void, CareProvider> {
        @Override
        protected CareProvider doInBackground(String... params) {
            setClient();

            String query = "{\"query\": {\"match\": {\"userID\": \"" + params[0] + "\"}}}";
            Log.d("Query", query);
            //String query = "{\"query\": {\"match\": {\"patient\": {\"userID\": "+ params[0] + "\"}}}}";

            //We use an array to store all matching patients given the userID -- in case we get duplicates
            ArrayList<CareProvider> matchingCareProviders = new ArrayList<CareProvider>();
            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("careprovider")
                    .build();

            try {
                JestResult result = client.execute(search);

                if (result.isSucceeded()) {
                    List<CareProvider> results;
                    results = result.getSourceAsObjectList(CareProvider.class);
                    for(CareProvider careProvider:results) {
                        Log.d("bleh", results.toString());
                    }
                    matchingCareProviders.addAll(results);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(CareProvider i: matchingCareProviders){
                Log.d("Results", i.getUserID());
            }

            if(matchingCareProviders.size() == 0){
                return null;
            }
            else{return matchingCareProviders.get(0);}
        }
    }
}

package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import io.searchbox.client.JestResult;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

/**
 * Class resposible for saving and loading users from the ElasticSearch server.
 *
 * @version 2.0
 * @author Anders, Joe
 * @see Patient
 * @see CareProvider
 */
public class ESUserManager extends ESManager{



//Patients

    /**
     * Task which adds a Patient to the server, given a Patient object.
     */
    public static class AddPatientTask extends AsyncTask<Patient, Void, Void>{
        @Override
        protected Void doInBackground(Patient... params) {
            setClient();
            for(Patient patient: params) {
                Log.d("newPatient", "Id = " + patient.getUserID() + " Phone = " + patient.getPhoneNumber() + " Email = " + patient.getEmailAddress());
                try {
                    Index index = new Index.Builder(patient)
                            .index(teamIndex)
                            .type("patient")
                            .id(patient.getUserID())
                            .refresh(true)
                            .build();
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        Log.d("AddPatientTask", "Problem was successfully added.");
                    } else {
                        Log.d("AddPatientTask", "Failed to add problem to server.");
                    }
                } catch (IOException e) {
                    Log.d("AddPatientTask", "IOException");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * Task which deletes a patient given a userId.
     */
    public static class DeletePatientTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            setClient();
            for (String fileId : params) {
                String query = "{\"query\": {\"match\": {\"fileId\": \"" + fileId + "\"}}}";
                DeleteByQuery delete = new DeleteByQuery.Builder(query)
                        .addIndex(teamIndex)
                        .addType("patient")
                        .build();
                try {
                    JestResult result = client.execute(delete);
                    if(result.isSucceeded()) {
                        Log.d("DeletePatientTask", "Patient deleted.");
                    }
                    else{
                        Log.d("DeletePatientTask", "Patient deletion failed.");
                    }
                } catch (IOException e) {
                    Log.d("DeletePatientTask", "IOException");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * Task which loads a patient from the server when given a userID.
     */
    public static class GetAPatientTask extends AsyncTask<String, Void, Patient> {
        @Override
        protected Patient doInBackground(String... params){
            setClient();
            String userID = params[0];
            String query = "{\"query\": {\"match\": {\"userID\": \"" + userID + "\"}}}";
            Patient matchingPatient = null;
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
                        Log.d("GetAPatientTask", "Patient loaded:" + patient.toString());
                    }
                    if(results.size() != 0){
                        matchingPatient = results.get(0);
                    }
                }
            } catch (IOException e) {
                Log.d("GetAPatientTask", "IOException");
                e.printStackTrace();
            }
            return matchingPatient;
        }

    }

    public static class GetPatientWithShortCodeTask extends AsyncTask<String, Void, Patient>{
        @Override
        protected Patient doInBackground(String... params){
            setClient();
            String shortCode = params[0];
            String query = "{\"query\": {\"match\": {\"shortCode\": \"" + shortCode + "\"}}}";
            Patient matchingPatient = null;
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
                        Log.d("GetAPatientWithShortCodeTask", "Patient loaded:" + patient.toString());
                    }
                    if(results.size() != 0){
                        matchingPatient = results.get(0);
                    }
                }
            } catch (IOException e) {
                Log.d("GetAPatientWithShortCodeTask", "IOException");
                e.printStackTrace();
            }
            //Log.d("GetAPatientWithShortCodeTask", "Returning patient " + matchingPatient.toString());
            return matchingPatient;
        }
    }


    /**
     * Task which returns a list of all patients with a common parentId.
     */
    public static class GetPatientListTask extends AsyncTask<String, Void, ArrayList<Patient>>{
        @Override
        protected ArrayList<Patient> doInBackground(String... params){
            setClient();
            String parentId = params[0];
            String query = "{\"query\": {\"match\": {\"parentId\": \"" + parentId + "\"}}}";
            ArrayList<Patient> matchingPatients = new ArrayList<>();
            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("patient")
                    .build();
            try{
                JestResult result = client.execute(search);

                if(result.isSucceeded()){
                    List<Patient> results;
                    results = result.getSourceAsObjectList(Patient.class);
                    for(Patient patient: results){
                        Log.d("GetPatientListTask", "Patient loaded:" + patient.toString());
                    }
                    matchingPatients.addAll(results);
                }
            }catch(IOException e){
                Log.d("GetProblemListTask", "IOException");
                e.printStackTrace();
            }
            return matchingPatients;
        }
    }


//care providers

    /**
     * Task which adds a Care Provider to the server, given a CareProvider object.
     */
    public static class AddCareProviderTask extends AsyncTask<CareProvider, Void, Void>{
        @Override
        protected Void doInBackground(CareProvider... params) {
            setClient();
            for(CareProvider careProvider : params) {
                try {
                    Index index = new Index.Builder(careProvider)
                            .index(teamIndex)
                            .type("careprovider")
                            .id(careProvider.getUserID())
                            .refresh(true)
                            .build();
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        Log.d("AddCareProviderTask", "Care Provider was successfully added.");
                    } else {
                        Log.d("AddCareProviderTask", "Failed to add Care Provider to server.");
                    }
                } catch (IOException e) {
                    Log.d("AddPatientTask", "Failed to execute");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * Deletes a care provider given its userId.
     */
    public static class DeleteCareProviderTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            setClient();
            String fileId = params[0]; //Prevents mass deletion of Care Providers
            String query = "{\"query\": {\"match\": {\"fileId\": \"" + fileId + "\"}}}";
            DeleteByQuery delete = new DeleteByQuery.Builder(query)
                    .addIndex(teamIndex)
                    .addType("careprovider")
                    .build();
            try {
                JestResult result = client.execute(delete);
                if(result.isSucceeded()) {
                    Log.d("DeleteCareProviderTask", "Care Provider deleted.");
                }
                else{
                    Log.d("DeleteCareProviderTask", "Care Provider deletion failed.");
                }

            } catch (IOException e) {
                Log.d("DeleteCareProviderTask", "IOException");
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Task which retrieves a CareProvider object from the server given a userID.
     */
    public static class GetACareProviderTask extends AsyncTask<String, Void, CareProvider> {
        @Override
        protected CareProvider doInBackground(String... params){
            setClient();
            String userID = params[0];
            String query = "{\"query\": {\"match\": {\"userID\": \"" + userID + "\"}}}";
            CareProvider matchingCareProvider = null;
            Search search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("careprovider")
                    .build();
            try {
                JestResult result = client.execute(search);

                if (result.isSucceeded()) {
                    List<CareProvider> results;
                    results = result.getSourceAsObjectList(CareProvider.class);
                    for(CareProvider careProvider :results) {
                        Log.d("GetACareProviderTask", "Patient loaded:" + careProvider.toString());
                    }
                    if(results.size() != 0){
                        matchingCareProvider = results.get(0);
                    }
                }
            } catch (IOException e) {
                Log.d("GetAPatientTask", "IOException");
                e.printStackTrace();
            }
            return matchingCareProvider;
        }

    }
}

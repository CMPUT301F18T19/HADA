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

public class ListManagerPatient {
    static JestDroidClient client = null;

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

    public static class AddPatientTask extends AsyncTask<Patient, Void, Void>{
        @Override
        protected Void doInBackground(Patient... params) {
            setClient();
            Patient patient = params[0];
            Index index = new Index.Builder(patient)
                    .index("HARDCODE")
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

    public static class GetPatientsTask extends AsyncTask<String, Void, ArrayList<Patient>> {
        @Override
        protected ArrayList<Patient> doInBackground(String... params) {
            setClient();
            ArrayList<Patient> patients = new ArrayList<Patient>();
            Search search = new Search.Builder(params[0])
                    .addIndex("HARDCODE")
                    .addType("HARDCODE")
                    .build();

            try {
                JestResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Patient> patientList;
                    patientList = result.getSourceAsObjectList(Patient.class);
                    patients.addAll(patientList);

                }

            } catch (IOException e) {
            }

            return patients;
        }


    }
}

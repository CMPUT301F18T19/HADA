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

public class ESRecordManager {
    static JestDroidClient client=null;
    public static void setClient(){
        if(client==null){
            DroidClientConfig config= new DroidClientConfig
                    //testing for our group
                    .Builder("http://cmput301.softwareprocess.es:8080/cmput301f18t19test")
                    .build();

            JestClientFactory factory=new JestClientFactory();
            factory.setDroidClientConfig(config);
            client=(JestDroidClient) factory.getObject();
        }

    }

    public static class AddRecordTask extends AsyncTask<Record, Void, Void>{
        @Override
        protected Void doInBackground(Record... params){
            setClient();
            Record record = params[0];
            //TODO need to make these not hardcoded values and dependant on record/patient etc
            Index index = new Index.Builder(record).index("hardcode!").type("AlsoHC").build();
            try {
                DocumentResult result = client.execute(index);
                if(result.isSucceeded()){
                    //TODO do something with record maybe?
                }
            }catch (IOException e){
                //TODO do something here
            }

        return null;
        }
    }

    public static class GetRecordTask extends AsyncTask<String, Void, ArrayList<Record>>{
        @Override
        protected ArrayList<Record> doInBackground(String... params){
           setClient();
           ArrayList<Record> records = new ArrayList<Record>();
           Search search = new Search.Builder(params[0]).addIndex("hardcode!").addType("AlsoHC").build();
           try {
               JestResult result = client.execute(search);
               if(result.isSucceeded()){
                   List<Record> recordList;
                   recordList = result.getSourceAsObjectList(Record.class);
                   records.addAll(recordList);
               }
           }catch (IOException e){} //TODO FIX THIS!
           return records;
        }
    }
}

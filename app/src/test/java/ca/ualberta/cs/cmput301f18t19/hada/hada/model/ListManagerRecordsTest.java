package ca.ualberta.cs.cmput301f18t19.hada.hada.model;



import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ListManagerRecord;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

import static org.junit.Assert.*;

//http://cmput301.softwareprocess.es:8080/cmput301f18t19
//http://cmput301.softwareprocess.es:8080/cmput301f18t19test

public class ListManagerRecordsTest{
    @Test
    public void testSaveRecord() {
        Record record = new Record();
        new ListManagerRecord.AddRecordTask().execute(record);
        //so we gotta check if it is in the db so we should search for it
        DroidClientConfig config= new DroidClientConfig
                //testing for our group
                .Builder("http://cmput301.softwareprocess.es:8080/cmput301f18t19test")
                .build();
        JestClientFactory factory=new JestClientFactory();
        factory.setDroidClientConfig(config);
        JestDroidClient client=(JestDroidClient) factory.getObject();
        Search search = new Search.Builder("")
                .addIndex("hardcode!") //TODO discuss how to uniquely identify records.
                .addType("AlsoHC")
                .build();
        try{
            JestResult result = client.execute(search);

            assertTrue(result.isSucceeded());
        }catch (IOException e){} //TODO do something here I guess?
    }
    @Test
    public void testGetRecordTask(){
        Record record = new Record();
        ArrayList<Record> records = new ArrayList<Record>();
        new ListManagerRecord.AddRecordTask().execute(record);
        try {
            records = new ListManagerRecord.GetRecordTask().execute("").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(records.isEmpty());

    }
}

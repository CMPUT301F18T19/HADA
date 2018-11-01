package ca.ualberta.cs.cmput301f18t19.hada.hada;



import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.junit.Test;

import java.io.IOException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Search;

import static org.junit.Assert.*;

//http://cmput301.softwareprocess.es:8080/cmput301f18t19
//http://cmput301.softwareprocess.es:8080/cmput301f18t19test

public class ListManagerRecordsTest {
    @Test
    public void testSaveRecord() { ;
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
                .addIndex("shaiful-thursday") //TODO discuss record.ID info.
                .addType("tweet")
                .build();
        try{
            JestResult result = client.execute(search);

            assertTrue(result.isSucceeded());
        }catch (IOException e){} //TODO do something here I guess?
    }
}

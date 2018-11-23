package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

/**
 * The type Es problem manager.
 */
public class ESProblemManager {

    /**
     * The Client.
     */
    static JestDroidClient client = null;


    /**
     * Set client.
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


}

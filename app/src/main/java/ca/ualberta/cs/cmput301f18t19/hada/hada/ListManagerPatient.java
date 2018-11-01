package ca.ualberta.cs.cmput301f18t19.hada.hada;

import android.content.AsyncTaskLoader;
import android.icu.text.AlphabeticIndex;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.lang.reflect.Array;
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

    public static class GetPatientTask extends AsyncTask<Record, Void, Void {
        @Override
        protected Void doInBackground(Patient... params) {
            setClient();

            Record record = params[0];
                    .addIndex("argoebel")
                    .addType("tweet")
                    .build();
            try {
                JestResult result = client.execute(search);

                if (result.isSucceeded()){
                    List<NormalTweet> tweetList;
                    tweetList = result.getSourceAsObjectList(NormalTweet.class);
                    tweets.addAll(tweetList);
                }
            } catch (IOException e) {
            }
            return tweets;
        }
    }





}

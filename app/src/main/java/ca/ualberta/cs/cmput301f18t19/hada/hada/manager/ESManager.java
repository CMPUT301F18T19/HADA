/*
 *  CMPUT 301 - Fall 2018
 *
 *  ESManager.java
 *
 *  11/23/18 2:28 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

/**
 * Abstract class ESManager, which defines the ES server to connect to and a common setClient
 * method for other ESManagers to use to connect.
 *
 * @author AndersJ
 * @see ESProblemManager
 * @see ESRecordManager
 * @see ESUserManager
 * @version 1
 */
public abstract class ESManager {

    /**
     * The JestDroidClient for connecting to ES.
     */
    static JestDroidClient client = null;
    /**
     * The index of the team's ES node.
     */
    static String teamIndex = "cmput301f18t19";

    /**
     * Sets the client to be the defined server..
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

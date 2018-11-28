/*
 *  CMPUT 301 - Fall 2018
 *
 *  ViewRecordLocationsForUserActivity.java
 *
 *  11/27/18 8:32 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * The activity for displaying the locations for all records for a given user.
 *
 * @author AndersJ
 * @version 1.0
 * @see Record
 * @see ca.ualberta.cs.cmput301f18t19.hada.hada.model.User
 */
public class ViewRecordLocationsForUserActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    /**
     * The User id supplied via the intent.
     */
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record_locations);
        Intent intent = getIntent();

        userId = intent.getStringExtra("userId");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Loads the map and all related records and places pins on the map.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Nasty nested for loop -- consider adding userId() to Record
        ArrayList<Problem> problems = new ProblemController().getListOfProblems(userId);
        for(Problem problem: problems) {
            String problemFileId = problem.getFileId();
            ArrayList<Record> records = new RecordController().getRecordList(problemFileId);
            for (Record record : records) {
                if (record.getGeoLocation() != null) {
                    double lat = record.getGeoLocation().getLatitude();
                    double lng = record.getGeoLocation().getLongitude();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(record.toString()));
                }
            }
        }
    }
}

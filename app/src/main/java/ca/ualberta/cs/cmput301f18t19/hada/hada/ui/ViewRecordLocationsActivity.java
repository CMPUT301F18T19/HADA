/*
 *  CMPUT 301 - Fall 2018
 *
 *  ViewRecordLocationsActivity.java
 *
 *  11/25/18 5:41 PM
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

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ContextSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * Activity which loads locations for all records in a problem.
 *
 * @author AndersJ
 * @version 1.0
 * @see Record
 * @see ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem
 *
 */
public class ViewRecordLocationsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    /**
     * The problem file id
     */
    String problemFileId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record_locations);
        Intent intent = getIntent();
        problemFileId = intent.getStringExtra("problemFileId");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        ContextSingleton.getInstance().setContext(this);
    }
    /**
     * Loads the map and places all pins from the records on the map.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList<Record> records = new RecordController().getRecordList(problemFileId);
        for(Record record: records){
            if(record.getLocationArrayList()!=null){
                mMap.addMarker(new MarkerOptions().position(record.getLocation()).title(record.toString()));
            }
        }
    }
}

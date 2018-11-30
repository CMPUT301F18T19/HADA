/*
 *  CMPUT 301 - Fall 2018
 *
 *  ViewSingleRecordLocationActivity.java
 *
 *  11/27/18 9:47 PM
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
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * Activity for displaying the location for a single record given a record's fileId in the intent.
 *
 * @author AndersJ
 * @version 1.0
 * @see Record
 */
public class ViewSingleRecordLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    /**
     * The Record file id we get from the intent.
     */
    String recordFileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record_locations);
        Intent intent = getIntent();
        recordFileId = intent.getStringExtra("recordFileId");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * onCreate generates the map, we load the record, and put the pin on the map.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Record record = new RecordController().getRecord(recordFileId);
        if (record.getLocation() != null) {
            mMap.addMarker(new MarkerOptions().position(record.getLocation()).title(record.toString()));
        }
    }
}


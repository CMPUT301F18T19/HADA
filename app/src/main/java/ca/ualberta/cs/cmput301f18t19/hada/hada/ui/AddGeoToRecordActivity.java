/*
 *  CMPUT 301 - Fall 2018
 *
 *  AddGeoToRecordActivity.java
 *
 *  11/25/18 1:17 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Activity which displays a map for a patient to specify the geo-location of their record.
 * Uses Google Maps SDK.
 *
 * @author AndersJ
 * @version 1
 * @see ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record
 * @see Location
 * @see LatLng
 */
public class AddGeoToRecordActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    //Default pinLocation
    private LatLng pinLocation= new LatLng(-34, 151);

    /**
     * OnCreate starts the Activity and loads the map fragment.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_geo_to_record);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    /**
     * Called to initialize the map, think of it as where OnCreate stuff normally goes.
     *
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mMap = googleMap;

        //Tries to load current location and drop a pin there
        try{
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = lastKnownLocation.getLongitude();
            double latitude = lastKnownLocation.getLatitude();
            pinLocation = new LatLng(latitude, longitude);
        }catch (SecurityException e){
            Log.d("AddGeoToRecordActivity", "issue loading last known location");
            e.printStackTrace();
        }
        mMap.addMarker(new MarkerOptions().position(pinLocation).title("Current Location").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pinLocation));
        Button getCurrent = findViewById(R.id.addGeoToRecordActivitySetCurrentLocButton);
        Button save = findViewById(R.id.addGeoToRecordActivitySaveButton);



        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            /**
             * Map LongClick listener -- drops pin at any point you long click
             * @param latLng: The lat and long of where pin was dropped.
             */
            @Override
            public void onMapLongClick(LatLng latLng) {
                pinLocation = latLng;
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(pinLocation).title(latLng.toString()));
            }
        });


        getCurrent.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick for the "Get Current Location" button. Calls for current location and
             * drops a pin there.
             *
             */
            @Override
            public void onClick(View view) {
                try{
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    double longitude = lastKnownLocation.getLongitude();
                    double latitude = lastKnownLocation.getLatitude();
                    pinLocation = new LatLng(latitude, longitude);
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(pinLocation).title("Current Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(pinLocation));
                }catch (SecurityException e){
                    Log.d("AddGeoToRecordActivity", "issue loading last known location");
                    e.printStackTrace();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick for save button. When clicked, returns the LatLng object to AddRecordActivity
             * where it is converted into a Location object.
             *
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Location", pinLocation);
                setResult(RESULT_OK, intent);
                finish();
            }
        });







    }
}

/*
 * CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 11/11/18 12:41 PM
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 * Last modified 11/11/18 12:41 PM
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Adds a new record with selected photos and, depending on the position of the geo location
 * switch, geographical data can also be included. Multiple photos can be selected and an
 * optional comment can be included.
 *
 * Location permissions based off example by Yashas on StackOverFlow
 *
 * @author Christopher Penner
 * @version 0.1
 * @see <a href="https://stackoverflow.com/a/51350622">StackOverflow example by Yashas</a>
 */
public class AddRecordActivity extends AppCompatActivity {

    private boolean saveLocationBoolean;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        final EditText addTitle = findViewById(R.id.addRecordActivityTitle);
        final EditText addComment = findViewById(R.id.addRecordActivityComment);
        Button addPhotos = findViewById(R.id.addRecordActivitySelectRecordPhotos);
        Switch geoLocation = findViewById(R.id.addRecordActivityGeoLocationSwitch);
        Button saveRecord = findViewById(R.id.addRecordActivitySaveButton);
        Intent intent = getIntent();
        final int problemIndex = intent.getExtras().getInt("problem");




        addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO access photos and select them
                Toast.makeText(AddRecordActivity.this, "Select photos", Toast.LENGTH_SHORT).show();
            }
        });

        geoLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    requestLocationPermission();
                    saveLocationBoolean = true;
                } else {
                    saveLocationBoolean = false;
                }
            }
        });

        saveRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = addTitle.getText().toString();
                String comment = addComment.getText().toString();
                //TODO: Saving photos
                if(saveLocationBoolean){
                    try {
                        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Record record = new Record();
                        record.setComment(comment);
                        record.setTitle(title);
                        record.setGeoLocation(lastKnownLocation);
                        //TODO: Photos
                        Log.d("AddRecord", "New Record: title=" + record.getTitle()+ " comment=" +record.getComment() + " location="+record.getGeoLocation().toString());
                        UserController userController = new UserController();
                        userController.addRecord(record, problemIndex);
                        finish();
                    }catch(SecurityException e){
                        Toast.makeText(AddRecordActivity.this, "Unable to save location. Please enable the location permissions.", Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Record record = new Record();
                    record.setComment(comment);
                    record.setTitle(title);
                    //TODO: Photos
                    Log.d("AddRecord", "New Record: title=" + record.getTitle()+ " comment=" +record.getComment());
                    UserController userController = new UserController();
                    userController.addRecord(record, problemIndex);
                    finish();
                }
            }
        });
    }

    //based off of Yasha's answer on StackOverflow https://stackoverflow.com/a/51350622
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantresults){
        super.onRequestPermissionsResult(requestCode, permissions, grantresults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantresults, this);
    }

    //based off of Yasha's answer on StackOverflow https://stackoverflow.com/a/51350622
    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission(){
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)){
            Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
        }else{
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }
}

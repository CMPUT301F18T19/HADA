/*
 *  CMPUT 301 - Fall 2018
 *
 *  SearchInputActivity.java
 *
 *  11/26/18 8:53 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SearchInputActivity extends AppCompatActivity {

    private final int REQUEST_LOCATION_PERMISSION = 1;
    private String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
    private int requestCode = 200;
    private LatLng chosenLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_input);

        Intent intent = getIntent();
        final String searchObjectType = intent.getStringExtra("searchObjectType");
        final String parentId = intent.getStringExtra("parentId");

        //Get views
        TextView searchingTitle = findViewById(R.id.searchInputTitle);
        final RadioButton keywordRadio = findViewById(R.id.searchInputKeywordRadio);
        final RadioButton geoLocationRadio = findViewById(R.id.searchInputGeoLocRadio);
        final RadioButton bodyLocationRadio = findViewById(R.id.searchInputBodyLocRadio);
        final EditText keywordInput = findViewById(R.id.searchInputKeywordInput);
        //All geo related items
        final Button geoInputButton = findViewById(R.id.searchInputGeoLocInputButton);
        final TextView geoSearchWithin = findViewById(R.id.searchInputSearchWithin);
        final EditText geoDistanceInput = findViewById(R.id.searchInputDistanceInput);
        final TextView geoKmOfText = findViewById(R.id.searchInputKmOf);
        final TextView geoLocOutput = findViewById(R.id.searchInputGeoLocationChosen);

        final Spinner bodyLocationInput = findViewById(R.id.searchInputBodyLocInput);
        Button searchButton  = findViewById(R.id.searchInputSearchButton);


        //Swap to EditText if the user selects to search by Keyword (DEFAULT)
        keywordRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywordInput.setVisibility(View.VISIBLE);
                bodyLocationInput.setVisibility(View.GONE);

                geoInputButton.setVisibility(View.GONE);
                geoLocOutput.setVisibility(View.GONE);
                geoSearchWithin.setVisibility(View.GONE);
                geoDistanceInput.setVisibility(View.GONE);
                geoKmOfText.setVisibility(View.GONE);
            }
        });

        //Add input button, three textViews and EditText if the user wants to search by geo-loc
        geoLocationRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geoInputButton.setVisibility(View.VISIBLE);
                geoLocOutput.setVisibility(View.VISIBLE);
                geoSearchWithin.setVisibility(View.VISIBLE);
                geoDistanceInput.setVisibility(View.VISIBLE);
                geoKmOfText.setVisibility(View.VISIBLE);

                bodyLocationInput.setVisibility(View.GONE);
                keywordInput.setVisibility(View.GONE);
            }
        });

        //Swap to Dropdown if the user selects to search by Body-Location
        bodyLocationRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bodyLocationInput.setVisibility(View.VISIBLE);
                keywordInput.setVisibility(View.GONE);

                geoInputButton.setVisibility(View.GONE);
                geoLocOutput.setVisibility(View.GONE);
                geoSearchWithin.setVisibility(View.GONE);
                geoDistanceInput.setVisibility(View.GONE);
                geoKmOfText.setVisibility(View.GONE);
            }
        });
        //Pick a geo location button
        geoInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //We check for location permissions here before we load AddGeoToRecordActivity
                if (!EasyPermissions.hasPermissions(SearchInputActivity.this, perms)) {
                    requestLocationPermission();
                } else {
                    Intent intent = new Intent(SearchInputActivity.this, AddGeoToRecordActivity.class);
                    startActivityForResult(intent, requestCode);
                }
            }
        });

        //Setup spinner
        ArrayList<String> bodyParts = new ArrayList<>();
        bodyParts.add("Head");
        bodyParts.add("Chest");
        bodyParts.add("Left arm upper");
        bodyParts.add("Right arm upper");
        bodyParts.add("Left arm lower");
        bodyParts.add("Right arm lower");
        bodyParts.add("Stomach");
        bodyParts.add("Left leg upper");
        bodyParts.add("Right leg upper");
        bodyParts.add("Left leg lower");
        bodyParts.add("Right leg lower");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bodyParts);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bodyLocationInput.setAdapter(dataAdapter);


        //Opens the searchResultsActivity
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toPassToIntent = "";
                Boolean valid = false;
                Intent intent = new Intent(SearchInputActivity.this, SearchResultsActivity.class);
                if(keywordRadio.isChecked()){
                    toPassToIntent = keywordInput.getText().toString();
                    if(toPassToIntent.isEmpty()){
                        Toast.makeText(SearchInputActivity.this,"Empty text cannot be searched.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        valid=true;
                        intent.putExtra("keyword", toPassToIntent);

                    }
                }
                else if(geoLocationRadio.isChecked()){
                    String distance = geoDistanceInput.getText().toString();
                    if(chosenLocation == null || distance.isEmpty()){
                        Toast.makeText(SearchInputActivity.this, "Geo-search requires a pin plus a distance.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        valid=true;
                        intent.putExtra("location",chosenLocation);
                        intent.putExtra("distance", geoDistanceInput.getText().toString());
                    }

                }
                else if(bodyLocationRadio.isChecked()){
                    //toPassToIntent = bodyLocationInput.getSelectedItem().getID; //TODO Get item from the spinner
                    Toast.makeText(SearchInputActivity.this,"Body-Location searching is currently not supported.", Toast.LENGTH_SHORT).show();
                }
                else{ //Run if something went wrong in the activity and closes it.
                    Toast.makeText(SearchInputActivity.this,"Something went wrong. Try again.", Toast.LENGTH_SHORT).show();
                    finish();
                }

                //If valid search, start searchResultsActivity
                if(valid){
                    intent.putExtra("parentId", parentId);
                    intent.putExtra("searchObjectType", searchObjectType);
                    startActivity(intent);
                    Toast.makeText(SearchInputActivity.this,"Searching...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * Deals with retrieving the location set by the user.
     *
     * @param requestCode: The code we specified when starting the activity
     * @param resultCode:  The result we got from AddGeoToRecordActivity
     * @param intent:      The intent retrieved from AddGeoToRecordActivity
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                chosenLocation = intent.getExtras().getParcelable("Location");
                TextView selectedLoc = findViewById(R.id.searchInputGeoLocationChosen);
                selectedLoc.setText("Location: " + chosenLocation.toString());
            }
        }
        else {
            Toast.makeText(this, "An error occurred. Please try again. Request code: " + requestCode, Toast.LENGTH_SHORT).show();
        }
    }

    //based off of Yasha's answer on StackOverflow https://stackoverflow.com/a/51350622
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantresults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantresults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantresults, this);
    }

    //based off of Yasha's answer on StackOverflow https://stackoverflow.com/a/51350622
    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        final int REQUEST_LOCATION_PERMISSION = 1;
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }

}

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SearchInputActivity extends AppCompatActivity {

    private final int REQUEST_LOCATION_PERMISSION = 1;
    private String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
    private int requestCode = 200;
    private Location chosenLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_input);

        Intent intent = getIntent();
        String searchObjectType = intent.getStringExtra("searchObjectType");
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

        //Used if object being searched are problems
        if(searchObjectType == "problems"){
            //Disables search types that are not allowed when searching for problems
            geoLocationRadio.setVisibility(View.GONE);
            bodyLocationRadio.setVisibility(View.GONE);
        }
        //Used if object being searched are records
        else if(searchObjectType == "records"){

        }

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

        //Swap to Dropdown if the user selects to search by Body-Location
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
                        intent.putExtra("Search pre-query", toPassToIntent);
                        intent.putExtra("parentId", parentId);
                    }
                }
                else if(geoLocationRadio.isChecked()){
                    String distance = geoDistanceInput.getText().toString();
                    if(chosenLocation != null &&  !distance.isEmpty()){
                        intent.putExtra("parentId", parentId);
                        intent.putExtra("location",chosenLocation);
                        intent.putExtra("distance", geoDistanceInput.getText());
                    }
                    Toast.makeText(SearchInputActivity.this,"Geo-Location searching is currently not supported.", Toast.LENGTH_SHORT).show();
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
                LatLng chosenLatLng = intent.getExtras().getParcelable("Location");
                double lat = chosenLatLng.latitude;
                double lon = chosenLatLng.longitude;
                chosenLocation = new Location(LocationManager.GPS_PROVIDER);
                chosenLocation.setLatitude(lat);
                chosenLocation.setLongitude(lon);
                TextView selectedLoc = findViewById(R.id.searchInputGeoLocationChosen);
                selectedLoc.setText("Location: " + chosenLatLng.toString());
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

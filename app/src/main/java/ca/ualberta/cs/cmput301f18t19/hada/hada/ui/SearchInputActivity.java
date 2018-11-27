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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;

public class SearchInputActivity extends AppCompatActivity {

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
        final Button geoInputButton = findViewById(R.id.searchInputGeoLocInputButton);
        final Spinner bodyLocationInput = findViewById(R.id.searchInputBodyLocInput);
        Button searchButton  = findViewById(R.id.searchInputSearchButton);

        //Swap to EditText if the user selects to search by Keyword (DEFAULT)
        keywordRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywordInput.setVisibility(View.VISIBLE);
                bodyLocationInput.setVisibility(View.GONE);
                geoInputButton.setVisibility(View.GONE);
            }
        });

        //Swap to Dropdown if the user selects to search by Body-Location
        geoLocationRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geoInputButton.setVisibility(View.VISIBLE);
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
            }
        });

        //Used if object being searched are problems
        if(searchObjectType == "problems"){

        }
        //Used if object being searched are records
        else if(searchObjectType == "records"){

        }

        //Opens the searchResultsActivity
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toPassToIntent = "";
                Boolean valid = false;
                if(keywordRadio.isChecked()){
                    toPassToIntent = keywordInput.getText().toString();
                    if(toPassToIntent.isEmpty()){
                        Toast.makeText(SearchInputActivity.this,"Empty text cannot be searched.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        valid=true;
                    }
                }
                else if(geoLocationRadio.isChecked()){
                    //toPassToIntent = ""; //TODO Get geo location from activity
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
                    Intent intent = new Intent(SearchInputActivity.this, SearchResultsActivity.class);
                    intent.putExtra("Search pre-query", toPassToIntent);
                    intent.putExtra("parentId", parentId);
                    startActivity(intent);
                    Toast.makeText(SearchInputActivity.this,"Searching...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

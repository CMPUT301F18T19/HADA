package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

public class SearchResultsActivity extends AppCompatActivity {


    String parentId;
    String searchObjectType;
    String keyword;
    LatLng geoLocation;
    String geoDistance;
    String bodyLocation;
    String searchType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //Get string from intent
        Intent intent = getIntent();
        parentId = intent.getStringExtra("parentId");
        searchObjectType = intent.getStringExtra("searchObjectType");
        //For keyword search
        keyword = intent.getStringExtra("keyword");
        //For geo search
        geoLocation = intent.getExtras().getParcelable("location");
        geoDistance = intent.getStringExtra("distance");
        //For body location search
        bodyLocation = intent.getStringExtra("bodyLocation");//TODO pass this in

        if(keyword != null){
            searchType = "keyword";
        }
        else if(geoLocation != null){
            searchType = "geo-location";
        }
        else if(bodyLocation != null){
            searchType = "body-location";
        }

        TextView title = findViewById(R.id.searchResultsTitle);
        String titleText = "Results for: " + searchType + ".";
        title.setText(titleText);


    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView resultsList = findViewById(R.id.searchResultsListView);

        //Setup List and ArrayAdapter
        if(searchObjectType.equals("problems")){
            final List<Problem> problemsReturned;
            ArrayAdapter<Problem> problemArrayAdapter;

            if(searchType == "keyword"){
                problemsReturned = new ProblemController().searchProblemsWithKeywords(parentId, keyword);

            }
            else if(searchType == "geo-location"){
                problemsReturned = new ProblemController().searchProblemWithGeoLocation(parentId, geoLocation, geoDistance);
            }
            else if(searchType == "body-location"){
                //TODO get records from body location search
                problemsReturned = new ArrayList<>();
            }
            else {
                problemsReturned = null;
            }
            problemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, problemsReturned);
            resultsList.setAdapter(problemArrayAdapter);
            problemArrayAdapter.notifyDataSetChanged();

            //Goes to ViewProblemActivity
            resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(SearchResultsActivity.this, ViewProblemActivity.class);
                    intent.putExtra("problemFileId", problemsReturned.get(position).getFileId());
                    startActivity(intent);
                }
            });

            //Goes to EditProblemActivity if user is a patient
            if(!LoggedInSingleton.getInstance().getIsCareProvider()){
                resultsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Intent intent = new Intent(SearchResultsActivity.this, EditProblemActivity.class);
                        intent.putExtra("problemFileId", problemsReturned.get(position).getFileId());
                        startActivity(intent);
                        return true;
                    }
                });
            }
        }
        //If record type
        else if(searchObjectType.equals("records")){
            final List<Record> recordsReturned;
            ArrayAdapter<Record> recordArrayAdapter;
            if(searchType == "keyword"){
                recordsReturned = new RecordController().searchRecordsWithKeywords(parentId, keyword);

            }
            else if(searchType == "geo-location"){
                recordsReturned = new RecordController().searchRecordsWithGeo(parentId, geoDistance, geoLocation);
            }
            else if(searchType == "body-location"){
                //TODO get records from body location search
                recordsReturned = new ArrayList<>();
            }
            else {
                recordsReturned = null;
            }
            recordArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recordsReturned);
            resultsList.setAdapter(recordArrayAdapter);
            recordArrayAdapter.notifyDataSetChanged();

            //Goes to EditRecordActivity
            if(!LoggedInSingleton.getInstance().getIsCareProvider()){
                resultsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(SearchResultsActivity.this, EditRecordActivity.class);
                        intent.putExtra("recordFileId", recordsReturned.get(position).getFileId());
                        startActivity(intent);
                        return true;
                    }
                });
            }

            //Goes to ViewRecordActivity
            resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(SearchResultsActivity.this, ViewRecordActivity.class);
                    intent.putExtra("recordFileId", recordsReturned.get(position).getFileId());
                    startActivity(intent);
                }
            });
        }
        else{
            Toast.makeText(this, "SearchObjectType was not detected.", Toast.LENGTH_SHORT).show();
        }


    }
}

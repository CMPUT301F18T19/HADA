package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //Get string from intent
        Intent intent = getIntent();
        String preQuery = intent.getStringExtra("Search pre-query");
        String parentId = intent.getStringExtra("parentId");
        TextView title = findViewById(R.id.searchResultsTitle);
        String titleText = "RESULTS FOR: " + preQuery;
        title.setText(titleText);

        //Assuming keyword search for now //TODO Allow for more types of searching
        ListView resultsList = findViewById(R.id.searchResultsListView);
        List<Problem> problemsReturned = new ProblemController().searchProblemsWithKeywords(parentId, preQuery);
        Log.d("SearchResultsActivity", problemsReturned.toString());
        ArrayAdapter<Problem> problemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, problemsReturned);
        resultsList.setAdapter(problemArrayAdapter);
        problemArrayAdapter.notifyDataSetChanged();

    }
}

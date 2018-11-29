package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;

public class SearchResultsActivity extends AppCompatActivity {

    String preQuery;
    String parentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //Get string from intent
        Intent intent = getIntent();
        preQuery = intent.getStringExtra("Search pre-query");
        parentId = intent.getStringExtra("parentId");
        TextView title = findViewById(R.id.searchResultsTitle);
        String titleText = "RESULTS FOR: " + preQuery;
        title.setText(titleText);


    }

    @Override
    protected void onResume() {
        super.onResume();

        //Assuming keyword search for now //TODO Allow for more types of searching
        ListView resultsList = findViewById(R.id.searchResultsListView);
        final List<Problem> problemsReturned = new ProblemController().searchProblemsWithKeywords(parentId, preQuery);
        Log.d("SearchResultsActivity", problemsReturned.toString());
        ArrayAdapter<Problem> problemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, problemsReturned);
        resultsList.setAdapter(problemArrayAdapter);
        problemArrayAdapter.notifyDataSetChanged();


        //Goes to PatientProblemCommentActivity
        if(LoggedInSingleton.getInstance().getIsCareProvider()){
            resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(SearchResultsActivity.this, PatientProblemCommentActivity.class);
                    Problem problem = problemsReturned.get(position);
                    intent.putExtra("problemFileId", problem.getFileId());
                    intent.putExtra("patientUserId",problem.getParentId());
                    startActivity(intent);
                }
            });
        }
        else{
            //Goes to ViewProblemActivity
            resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(SearchResultsActivity.this, PatientProblemCommentActivity.class);
                    intent.putExtra("problemFileId", problemsReturned.get(position).getFileId());
                    startActivity(intent);
                }
            });

            //Goes to EditProblemActivity
            resultsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(SearchResultsActivity.this, EditProblemActivity.class);
                    intent.putExtra("problemFileId", problemsReturned.get(position).getFileId());
                    startActivity(intent);
                    return true;
                }
            });
        }
    }
}

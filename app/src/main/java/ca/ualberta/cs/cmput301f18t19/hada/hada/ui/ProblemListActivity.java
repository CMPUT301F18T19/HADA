package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;

/**
 * Activity for browsing problems of a given patient.
 *
 * @author Joe
 * @see Patient
 * @see Problem
 */
public class ProblemListActivity extends AppCompatActivity implements Serializable {
    /**
     * The userID of the logged in user.
     */
    String loggedInUser = LoggedInSingleton.getInstance().getLoggedInID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_list);

        //Sets custom title @author Joe
        TextView titleTextView = findViewById(R.id.problemListUsername);
        String titleText = loggedInUser + "'s Problems";
        titleTextView.setText(titleText);

        //Goes to AddProblemActivity
        FloatingActionButton addProblem = findViewById(R.id.problemListFloatingButton);
        addProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Add activity name here when AddProblemActivity is done. - Joe
                Intent intent = new Intent(ProblemListActivity.this, AddProblemActivity.class);
                startActivity(intent);
            }
        });

        //Goes to EditUserSettingsActivity
        ImageButton settingsButton = findViewById(R.id.problemListSettingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProblemListActivity.this, EditUserSettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Get listView
        ListView listView = findViewById(R.id.problemListListView);

        //Displays the list of problems
        final ArrayList<Problem> problems = new ProblemController().getListOfProblems(loggedInUser);
        ArrayAdapter<Problem> problemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, problems);
        listView.setAdapter(problemArrayAdapter);
        problemArrayAdapter.notifyDataSetChanged();

        //Goes to EditProblemActivity
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProblemListActivity.this, EditProblemActivity.class);
                intent.putExtra("problemFileId", problems.get(position).getFileId());
                startActivity(intent);
                return true;
            }
        });

        //Goes to ViewProblemActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProblemListActivity.this, ViewProblemActivity.class);
                intent.putExtra("problemFileId", problems.get(position).getFileId());
                startActivity(intent);
            }
        });

    }
}

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
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ESUserManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;

public class ProblemListActivity extends AppCompatActivity implements Serializable {
    String loggedInUser = LoggedInSingleton.getInstance().getLoggedInID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_list);

        //Sets custom title @author Joe

        TextView titleTextView = findViewById(R.id.problemListUsername);
        String titleText = loggedInUser + "'s Problems";
        titleTextView.setText(titleText);
        ListView listView = findViewById(R.id.problemListListView);


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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProblemListActivity.this, EditProblemActivity.class);
                intent.putExtra("problemObject", position);
                startActivity(intent);
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProblemListActivity.this, ViewProblemActivity.class);
                intent.putExtra("Position", position);
                startActivity(intent);
            }
        });





    }
    @Override
    protected void onStart(){
        super.onStart();
        //Displays the list of problems
        ListView listView = findViewById(R.id.problemListListView);
        Patient patient = new UserController().getPatient(loggedInUser);
        ArrayList<Problem> problems = new ProblemController().getProblemList(patient);
        ArrayAdapter<Problem> problemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, problems);
        listView.setAdapter(problemArrayAdapter);
    }
}

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;

public class ProblemListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_list);

        //Sets custom title @author Joe
        String loggedInUser = LoggedInSingleton.getInstance().getLoggedInID();
        TextView titleTextView = findViewById(R.id.problemListUsername);
        String titleText = loggedInUser + "'s Problems";
        titleTextView.setText(titleText);


        //Goes to AddProblemActivity
        FloatingActionButton addProblem = findViewById(R.id.problemListFloatingButton);
        addProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Add activity name here when AddProblemActivity is done. - Joe
                //Intent intent = new Intent(ProblemListActivity.this, )
            }
        });

        //Goes to EditUserSettingsActivityroge
        ImageButton settingsButton = findViewById(R.id.problemListSettingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProblemListActivity.this, EditUserSettingsActivity.class);
                startActivity(intent);
            }
        });


    }
}

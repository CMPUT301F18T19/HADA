package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import ca.ualberta.cs.cmput301f18t19.hada.hada.R;

public class ProblemListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_list);
        Intent intent = getIntent();
        String loggedInUser = (String) intent.getExtras().get("User that is logged in");

        TextView username = findViewById(R.id.problemListUsername);
        username.setText(loggedInUser);
        FloatingActionButton settingFab = (FloatingActionButton) findViewById(R.id.problemListFloatingButton);
        settingFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO DO THINGS!
            }
        });

    }
}

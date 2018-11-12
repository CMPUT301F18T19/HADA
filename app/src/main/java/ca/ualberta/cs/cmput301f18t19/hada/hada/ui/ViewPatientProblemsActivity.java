package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;

public class ViewPatientProblemsActivity extends AppCompatActivity {

    private ListView problemsList;
    private EditText searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_problems);

        Button userSettings = findViewById(R.id.viewPatientProblemsUserSettings);
        Button newProblem = findViewById(R.id.viewPatientProblemsNewProblem);
        searchQuery = (EditText) findViewById(R.id.viewPatientProblemsSearch);
        problemsList = (ListView) findViewById(R.id.viewPatientProblemsList);

    }
}

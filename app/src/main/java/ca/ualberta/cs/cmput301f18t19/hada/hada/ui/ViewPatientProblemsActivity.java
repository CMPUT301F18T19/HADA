package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;

/**
 * Activity for viewing the problems for a given patient in a CareProvider's list of patients.
 *
 * @author Austin
 * @see ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider
 * @see ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient
 * @see PatientListActivity
 */
public class ViewPatientProblemsActivity extends AppCompatActivity {

    private ListView problemsList;
    private EditText searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_problems);

        //TODO map these buttons correctly
        //Button userSettings = findViewById(R.id.viewPatientProblemsUserSettings);
        //Button newProblem = findViewById(R.id.viewPatientProblemsNewProblem);
        searchQuery = (EditText) findViewById(R.id.viewPatientProblemsSearch);
        problemsList = (ListView) findViewById(R.id.viewPatientProblemsList);

    }
}

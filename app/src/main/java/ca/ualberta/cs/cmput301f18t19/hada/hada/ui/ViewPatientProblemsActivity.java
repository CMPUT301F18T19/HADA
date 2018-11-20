package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;

/**
 * Activity for viewing the problems for a given patient in a CareProvider's list of patients.
 *
 * @author Austin
 * @see ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider
 * @see ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient
 * @see PatientListActivity
 */
public class ViewPatientProblemsActivity extends AppCompatActivity {

    String loggedInUser = LoggedInSingleton.getInstance().getLoggedInID();
    private ListView problemsList;
    private EditText searchQuery;
    private Patient patient;
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_problems);
        final Intent intent = getIntent();
        final int patientPosition = (int) intent.getSerializableExtra("Position");
        listView = findViewById(R.id.viewPatientProblemsList);

        //Gets patient
        patient = new UserController().getPatientList(loggedInUser).get(patientPosition);

        //Sets custom title @author Joe
        TextView titleTextView = findViewById(R.id.viewPatientProblemsDisplayUsername);
        String titleText = loggedInUser + "-->" + patient + "'s Problems";
        titleTextView.setText(titleText);

        //Goes to EditUserSettingsActivity
        ImageButton settingsButton = findViewById(R.id.viewPAtientProblemsSettingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewPatientProblemsActivity.this, EditUserSettingsActivity.class);
                startActivity(intent);
            }
        });

        //Goes to ViewProblemActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int problemPosition, long id) {
                Intent intent = new Intent(ViewPatientProblemsActivity.this, PatientProblemComment.class);
                intent.putExtra("patientPosition", patientPosition);
                intent.putExtra("problemPosition", problemPosition);
                startActivity(intent);
            }
        });


    }

    @Override
    protected  void onResume(){
        super.onResume();
        ArrayList<Problem> problems = new ProblemController().getProblemList(patient.getUserID());
        ArrayAdapter<Problem> problemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, problems);
        listView.setAdapter(problemArrayAdapter);
        problemArrayAdapter.notifyDataSetChanged();
    }
}

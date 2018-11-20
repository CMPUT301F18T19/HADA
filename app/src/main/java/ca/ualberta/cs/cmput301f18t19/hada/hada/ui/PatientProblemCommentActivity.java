package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;

public class PatientProblemCommentActivity extends AppCompatActivity {
    String loggedInUser = LoggedInSingleton.getInstance().getLoggedInID();
    Patient patient;
    Problem problem;
    int problemPosition;
    int patientPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problem_comment);
        Intent intent = getIntent();
        patientPosition = (int) intent.getSerializableExtra("patientPosition");
        problemPosition = (int) intent.getSerializableExtra("problemPosition");

        //Get patient and problem that are related.
        patient = new UserController().getPatientList(loggedInUser).get(patientPosition);
        problem = new ProblemController().getProblemList(patient.getUserID()).get(problemPosition);

        //Sets custom title
        TextView titleTextView = findViewById(R.id.patientProblemCommentTitle);
        String titleText = loggedInUser + "-->" + patient.getUserID() +  "'s Problems" + "-->" + problem.getTitle();
        titleTextView.setText(titleText);


        //Add comment
        final EditText editText = findViewById(R.id.patientProblemCommentCommentInput);
        Button button = findViewById(R.id.patientProblemCommentbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO make record unclickable
                String inputComment = editText.getText().toString();
                new RecordController().addCommentRecord(patientPosition, problemPosition, inputComment);
                finish();
            }
        });
    }


    @Override
    protected void onResume(){
        super.onResume();
        ListView listView = findViewById(R.id.patientProblemCommentList);
        ArrayList<Record> records = problem.getRecords();
        ArrayAdapter<Record> recordArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        listView.setAdapter(recordArrayAdapter);
        recordArrayAdapter.notifyDataSetChanged();
    }

}

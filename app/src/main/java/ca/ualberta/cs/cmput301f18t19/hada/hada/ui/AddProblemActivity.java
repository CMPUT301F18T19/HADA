package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;

public class AddProblemActivity extends AppCompatActivity {
    private static final String TAG = "AddProblemActivity";
    private EditText addProblemTitle;
    private EditText addProblemDate;
    private EditText addProblemDescription;
    private Button addProblemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        // get references for editTexts
        addProblemTitle = findViewById(R.id.addProblemTitle);
        addProblemDate = findViewById(R.id.addProblemDate) ;
        addProblemDescription = findViewById(R.id.addProblemDescription);
        addProblemButton = findViewById(R.id.addProblemButton);

        // save problem using a controller when button is pressed
        addProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = addProblemTitle.getText().toString();
                String date = addProblemDate.getText().toString();
                String description = addProblemDescription.getText().toString();
                Toast.makeText(AddProblemActivity.this, "save problem", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "title = " + title);
                Log.d(TAG, "date = " + date);
                Log.d(TAG, "description = " + description);

                //TODO: store problem by calling a controller

            }
        });
    }

}
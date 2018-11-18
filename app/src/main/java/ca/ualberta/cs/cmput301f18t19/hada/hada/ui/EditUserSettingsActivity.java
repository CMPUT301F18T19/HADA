package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;

/**
 * @author Joe Potentier
 */

//TODO Generalize for all users, only works for patients right now
public class EditUserSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_settings);

        //Get current logged in user
        String loggedInID =  LoggedInSingleton.getInstance().getLoggedInID();
        final Patient patient = new UserController().getPatient(loggedInID);


        //Elements in activity layout
        final TextView emailInput = findViewById(R.id.editUserSettingsEditTextInput);
        TextView usernameInput = findViewById(R.id.editUserSettingsUsername);
        final TextView contactNumberInput = findViewById(R.id.editUserSettingsContactNumber);

        //AutoCompletes Data for the users existing settings
        emailInput.setText(patient.getEmailAddress());
        usernameInput.setText(patient.getUserID());
        contactNumberInput.setText(patient.getPhoneNumber());

        //Save button actions
        Button saveButton = findViewById(R.id.editUserSettingsSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean editedEmail = false;
                Boolean editedNumber = false;

                //Update email if changed
                String newEmail = emailInput.getText().toString();
                if(newEmail != patient.getEmailAddress()){
                    new UserController().editPatientEmail(patient, newEmail);
                    editedEmail = true;
                }

                //Update contact number if changed
                String newContactNumber = contactNumberInput.getText().toString();
                if(newContactNumber != patient.getPhoneNumber()){
                    new UserController().editPatientContactNumber(patient, newContactNumber);
                    editedNumber = true;
                }

                //Checks for changed and displays a Toast for each permutation
                //TODO Swap out strings into @strings to avoid hardcoding
                if(editedEmail && editedNumber){Toast.makeText(EditUserSettingsActivity.this, "Updated email and contact number.", Toast.LENGTH_SHORT).show();}
                else if(editedEmail){Toast.makeText(EditUserSettingsActivity.this, "Updated email.", Toast.LENGTH_SHORT).show();}
                else if(editedNumber){Toast.makeText(EditUserSettingsActivity.this, "Updated contact number.", Toast.LENGTH_SHORT).show();}

                finish();
            }
        });

        //Cancel button
        Button cancelButton = findViewById(R.id.editUserSettingsCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





    }
}

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;

/**
 * Activity for editing user settings.
 *
 * @author Joe Potentier
 * @see ca.ualberta.cs.cmput301f18t19.hada.hada.model.User
 * @see Patient
 * @see CareProvider
 * @see UserController
 */
//TODO Generalize for all users, only works for patients right now
public class EditUserSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_settings);

        //Elements in activity layout
        final TextView emailInput = findViewById(R.id.editUserSettingsEditTextInput);
        TextView usernameInput = findViewById(R.id.editUserSettingsUsername);
        final TextView contactNumberInput = findViewById(R.id.editUserSettingsContactNumber);

        //Get current logged in user
        LoggedInSingleton instance = LoggedInSingleton.getInstance();
        String loggedInID =  instance.getLoggedInID();
        Boolean isCareProvider = instance.getIsCareProvider();

        //Splits file based on isCareProvider
        //If they are a CareProvider
        if(isCareProvider){
            final CareProvider careProvider = new UserController().getCareProvider(loggedInID);

            //AutoCompletes Data for the users existing settings
            emailInput.setText(careProvider.getEmailAddress());
            usernameInput.setText(careProvider.getUserID());
            contactNumberInput.setText(careProvider.getPhoneNumber());

            //Save button actions
            Button saveButton = findViewById(R.id.editUserSettingsSaveButton);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Boolean editedEmail = false;
                    Boolean editedNumber = false;

                    //Update email if changed
                    String newEmail = emailInput.getText().toString();
                    if(newEmail != careProvider.getEmailAddress()){
                        new UserController().editCareProviderEmail(careProvider, newEmail);
                        editedEmail = true;
                    }

                    //Update contact number if changed
                    String newContactNumber = contactNumberInput.getText().toString();
                    if(newContactNumber != careProvider.getPhoneNumber()){
                        new UserController().editCareProviderContactNumber(careProvider, newContactNumber);
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

        }
        //If they are a patient
        else {
            final Patient patient = new UserController().getPatient(loggedInID);

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

        }

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

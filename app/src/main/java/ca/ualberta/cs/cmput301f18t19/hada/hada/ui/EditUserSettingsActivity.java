package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;

public class EditUserSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_settings);

        //AutoCompletes Data for the users existing settings
        TextView emailInput = findViewById(R.id.editUserSettingsEmail);
        TextView usernameInput = findViewById(R.id.editUserSettingsUsername);
        TextView contactNumberInput = findViewById(R.id.editUserSettingsContactNumber);




    }
}

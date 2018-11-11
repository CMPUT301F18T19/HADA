package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;

/**
 * Adds a new record with selected photos and, depending on the position of the geo location
 * switch, geographical data can also be included. Multiple photos can be selected and an
 * optional comment can be included.
 *
 * @author Christopher Penner
 * @version 0.1
 */
public class AddRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        Button addPhotos = findViewById(R.id.selectRecordPhotos);
        Switch geoLocation = findViewById(R.id.geoLocationSwitch);

        addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO access photos and select them
                Toast.makeText(AddRecordActivity.this, "Select photos", Toast.LENGTH_SHORT).show();
            }
        });

        geoLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO toggle geo location service on/off
                if (isChecked) {
                    Toast.makeText(AddRecordActivity.this, "on", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddRecordActivity.this, "off", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

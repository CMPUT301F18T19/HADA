package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ContextSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * Activity to view a single record from a problem.
 *
 * @author Austin
 * @see Record
 *
 */
public class ViewRecordActivity extends AppCompatActivity {

    private Record record;
    private String recordFileId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        Intent intent = getIntent();
        String LoggedInUser = LoggedInSingleton.getInstance().getLoggedInID();
        recordFileId = intent.getStringExtra("recordFileId");
        record = new RecordController().getRecord(recordFileId);

        // Goes to view GeoLocation
        Button viewGeoLocation = findViewById(R.id.viewRecordActivityGeolocation);
        viewGeoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(record.getLocationArrayList() != null) {
                    Intent intent = new Intent(ViewRecordActivity.this, ViewSingleRecordLocationActivity.class);
                    intent.putExtra("recordFileId", recordFileId);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ViewRecordActivity.this, "This record does not have a geo location.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Goes to view BodyLocation
        Button viewBodyLocation = findViewById(R.id.viewRecordActivityViewBodyLocation);
        viewBodyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //A check that bodyLocation has been set for the record
                if(record.getBodyLocation() != null){

                }
                else{
                    Toast.makeText(ViewRecordActivity.this, "This record does not have a body location.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    protected void onResume() {
        super.onResume();
        ContextSingleton.getInstance().setContext(this);
        record = new RecordController().getRecord(recordFileId);
        TextView titleText = findViewById(R.id.viewRecordActivityTitle);
        TextView commentText = findViewById(R.id.viewRecordActivityComment);
        TextView timeText = findViewById(R.id.viewRecordActivityTimestamp);

        titleText.setText(record.getTitle());
        commentText.setText(record.getComment());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime timestamp = record.getTimestamp();
        timeText.setText(timestamp.format(formatter));
    }
}

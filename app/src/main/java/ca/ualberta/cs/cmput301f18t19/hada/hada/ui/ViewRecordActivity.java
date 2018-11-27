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

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

public class ViewRecordActivity extends AppCompatActivity {

    private int recordPosition;
    private int problemPosition;
    private ArrayList<Record> records;
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


        ImageButton recordSettings = (ImageButton) findViewById(R.id.viewRecordActivitySettings);
        recordSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRecordActivity.this, EditRecordActivity.class);
                intent.putExtra("recordFileId", recordFileId);
                startActivity(intent);
            }
        });

    }

    protected void onResume() {
        super.onResume();

        record = new RecordController().getRecord(recordFileId);
        TextView titleText = (TextView) findViewById(R.id.viewRecordActivityTitle);
        TextView commentText = (TextView) findViewById(R.id.viewRecordActivityComment);
        TextView timeText = (TextView) findViewById(R.id.viewRecordActivityTimestamp);

        titleText.setText(record.getTitle());
        commentText.setText(record.getComment());

        //TODO FIX TIMESTAMP ISSUES - TIMESTAMP FROM RECORDCONTROLLER DOES NOT RETURN VALID VALUE
/*
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime timestamp = record.getTimestamp();
        timeText.setText(timestamp.format(formatter)); // Gives null error
        timeText.setText(LocalDateTime.now().format(formatter)); // Works fine
*/


    }
}

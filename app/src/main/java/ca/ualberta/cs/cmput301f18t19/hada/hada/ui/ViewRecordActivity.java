package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;
import android.widget.EditText;

import java.sql.Time;
import java.sql.Timestamp;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        Intent intent = getIntent();
        problemPosition = (int) intent.getSerializableExtra("ProblemPosition");
        recordPosition = (int) intent.getSerializableExtra("RecordPosition");
        final String recordFileId = intent.getStringExtra("recordFileId");
        String LoggedInUser = LoggedInSingleton.getInstance().getLoggedInID();



        Button editDate = findViewById(R.id.viewRecordEditDate);
        Button editTime = findViewById(R.id.viewRecordEditTime);
        Button editLocation = findViewById(R.id.viewRecordEditGeolocation);
        Button viewLocation = findViewById(R.id.viewRecordViewLocation);
        Button editPhotos = findViewById(R.id.viewRecordEditPhotos);
        final EditText editedComment = findViewById(R.id.viewRecordComment);
        editedComment.setHint(record.getComment());

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Go to EditDateActivity
                 */
            }
        });

        TextView titleText = (TextView) findViewById(R.id.viewRecordActivityTitle);
        TextView commentText = (TextView) findViewById(R.id.viewRecordActivityComment);
        TextView timeText = (TextView) findViewById(R.id.viewRecordActivityTimestamp);
        ImageButton recordSettings = (ImageButton) findViewById(R.id.viewRecordActivitySettings);

        titleText.setText(new RecordController().getRecord(recordFileId).getTitle());

        commentText.setText(new RecordController().getRecord(recordFileId).getComment());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        LocalDateTime timestamp = new RecordController().getRecord(recordFileId).getTimestamp();

        String time = timestamp.format(formatter);

        timeText.setText(time);

        recordSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRecordActivity.this, EditRecordActivity.class);
                intent.putExtra("recordFileId", recordFileId);
                startActivity(intent);
            }
        });



    }
}

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;

public class ViewRecordActivity extends AppCompatActivity {

    private int recordPosition;
    private int problemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        Intent intent = getIntent();
        problemPosition = (int) intent.getSerializableExtra("ProblemPosition");
        recordPosition = (int) intent.getSerializableExtra("RecordPosition");

        TextView titleText = (TextView) findViewById(R.id.viewRecordActivityTitle);
        TextView commentText = (TextView) findViewById(R.id.viewRecordActivityComment);
        TextView timeText = (TextView) findViewById(R.id.viewRecordActivityTimestamp);
        ImageButton recordSettings = (ImageButton) findViewById(R.id.viewRecordActivitySettings);

        titleText.setText( new UserController().getPatient(LoggedInSingleton.getInstance().getLoggedInID())
                .getProblem(problemPosition).getRecords().get(recordPosition).getTitle());

        commentText.setText(new UserController().getPatient(LoggedInSingleton.getInstance().getLoggedInID())
                .getProblem(problemPosition).getRecords().get(recordPosition).getComment());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        LocalDateTime timestamp = new UserController().getPatient(LoggedInSingleton.getInstance().getLoggedInID())
                .getProblem(problemPosition).getRecords().get(recordPosition).getTimestamp();

        String time = timestamp.format(formatter);

        timeText.setText(time);

        recordSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRecordActivity.this, EditRecordActivity.class);
                intent.putExtra("ProblemPosition",problemPosition);
                intent.putExtra("RecordPosition",recordPosition);
                startActivity(intent);
            }
        });



    }
}

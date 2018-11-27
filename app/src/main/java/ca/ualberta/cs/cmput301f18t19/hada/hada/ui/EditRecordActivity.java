package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;


import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * Activity to view a specific record from a problem.
 *
 * @author Austin, Jason
 * @see Record
 * @see Problem
 */
public class EditRecordActivity extends AppCompatActivity {


    //for selecting custom date
    //Based on adj-feelsbook by Anders Johnson
    //https://github.com/ColonelSanders21/adj-FeelsBook
    private static final String TAG = "EditRecordActivity";
    /**
     * The formatter object for converting localdatetime objects to and from strings.
     */
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    /**
     * The listener for the datepickerdialog, which enables the user to select a new date.
     */
    DatePickerDialog.OnDateSetListener dateSetListener;
    /**
     * The listener for the timepickerdialog, which enables the user to select a new time.
     */
    TimePickerDialog.OnTimeSetListener timeSetListener;
    /**
     * This variable is used to ensure that the pickers read from a common calendar date,
     * so that the date the user has selected is not reset when they go to change the time
     * (and vice versa).
     */
    Calendar oldCalendar = Calendar.getInstance();

    private ListView recordsList;
    private ArrayList<Record> records;
    private Problem oldProblem;

    private int position;
    private int problemPosition;
    private int recordPosition;
    private String recordFileId;
    private Record record;
    private Problem problem;

    //TODO Can't save or access timestamps!

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        Intent intent = getIntent();
        recordFileId = intent.getStringExtra("recordFileId");
        String LoggedInUser = LoggedInSingleton.getInstance().getLoggedInID();

        record = new RecordController().getRecord(recordFileId);

        //TODO - FIGURE OUT WHY .toString() APPLIED TO TIMESTAMP RETURNS AN ERROR

        final TextView displayDate = (TextView) findViewById(R.id.editRecordActivityCurrentDateDisplay);
        displayDate.setText("PLACEHOLDER");
/*        LocalDateTime timestamp = record.getTimestamp();
        String date = timestamp.toString();
        String modifiedDate = "";
        for (int i = 0; i < 10; i++) {
            char c = date.charAt(i);
            modifiedDate = modifiedDate + c;
        }
        displayDate.setText(modifiedDate);
*/

        final TextView displayTime = (TextView) findViewById(R.id.editRecordActivityCurrentTimeDisplay);
        displayTime.setText("PLACEHOLDER");
/*        String time = record.getTimestamp().toString();
        String modifiedTime = "";
        for (int i = 11; i < time.length(); i++) {
            char c = date.charAt(i);
            modifiedTime = modifiedTime + c;
        }
        displayTime.setText(modifiedTime);
*/
        final TextView title = (TextView) findViewById(R.id.editRecordActivityTitle);
        title.setText(record.getTitle());

        Button saveAll = (Button) findViewById(R.id.editRecordActivitySaveAllChanges);
        Button saveComment = (Button) findViewById(R.id.editRecordActivitySaveComment);
        final Button editDate = (Button) findViewById(R.id.editRecordActivityEditDate);
        Button editTime = (Button) findViewById(R.id.editRecordActivityEditTime);
        Button editLocation = (Button) findViewById(R.id.editRecordActivityEditGeolocation);
        Button editPhotos = (Button) findViewById(R.id.editRecordActivityEditPhotos);
        final EditText editedComment = (EditText) findViewById(R.id.editRecordActivityComment);
        editedComment.setHint(record.getComment());


        //for selecting custom date
        //Based on adj-feelsbook by Anders Johnson
        //https://github.com/ColonelSanders21/adj-FeelsBook
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = oldCalendar;
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                //The following three are final -- we want time to carry over from what was set to before
                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int minute = calendar.get(Calendar.MINUTE);
                final int second = calendar.get(Calendar.SECOND);

                dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, dayOfMonth, hour, minute, second);
                        LocalDateTime newDate = LocalDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());
                        oldCalendar = cal;

                        String date = newDate.format(formatter);
                        String modifiedDate = "";
                        for (int i = 0; i < 10; i++) {
                            char c = date.charAt(i);
                            modifiedDate = modifiedDate + c;
                        }
                        displayDate.setText(modifiedDate);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(EditRecordActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_Alert, dateSetListener,
                        year, month, day);
                dialog.show();
            }
        });

        //for selecting custom time
        //Based on adj-feelsbook by Anders Johnson
        //https://github.com/ColonelSanders21/adj-FeelsBook
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = oldCalendar;
                //The following three are final -- we want date to carry over from what was set before
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, day, hourOfDay, minute, 0);
                        LocalDateTime newDate = LocalDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());
                        oldCalendar = cal;
                        String time = newDate.format(formatter);

                        String modifiedTime = "";
                        for (int i = 10; i < time.length(); i++) {
                            char c = time.charAt(i);
                            modifiedTime = modifiedTime + c;
                        }
                        displayTime.setText(modifiedTime);
                    }
                };
                TimePickerDialog dialog = new TimePickerDialog(EditRecordActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_Alert,
                        timeSetListener, hour, minute,
                        true);
                dialog.show();
            }
        });


        editLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Go to EditLocationActivity
                 */
            }
        });


        editPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Go to EditPhotosActivity
                 */
            }
        });
        // KeyListener inspired by code on https://code.i-harness.com/en/q/16bbbc
        editedComment.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    new RecordController().editRecordComment(record, editedComment.getText().toString());
                    finish();
                }
                return false;
            }
        });

        saveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RecordController().editRecordComment(record, editedComment.getText().toString());
                finish();
            }
        });

        saveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((editedComment.getText().toString().length() != 0) || (editedComment.getText().toString() != record.getComment())) {
                    new RecordController().editRecordComment(record, editedComment.getText().toString());
                }

                String dateTime = "";
                for (int i = 0; i < 10; i++) {
                    char c = displayDate.getText().toString().charAt(i);
                    dateTime = dateTime + c;
                }

                for (int i = 10; i < displayTime.length(); i++) {
                    char c = displayTime.getText().toString().charAt(i);
                    dateTime = dateTime + c;
                }

                LocalDateTime date = LocalDateTime.parse(dateTime, formatter);
                record.setTimestamp(date);
                finish();
            }
        });


    }
}

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * Activity to edit a specific record from a problem.
 *
 * @author Austin, Jason
 * @see Record
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

    //TODO Can't save or access timestamps!

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        Intent intent = getIntent();
        String recordFileId = intent.getStringExtra("recordFileId");
        Record record = new RecordController().getRecord(recordFileId);

        //Get all view from activity
        TextView title = findViewById(R.id.editRecordTitle);
        ImageView imageView = findViewById(R.id.editRecordImage);
        EditText commentInput = findViewById(R.id.editRecordCommentValue);
        final TextView dateInput = findViewById(R.id.editRecordDateValue);
        Button editDateButton = findViewById(R.id.editRecordEditDateButton);
        Button editTimeButton = findViewById(R.id.editRecordEditTimeButton);
        Button geoLocationButton = findViewById(R.id.editRecordEditGeolocation);
        Button addRemovePhotosButton = findViewById(R.id.editRecordEditPhotos);
        Button saveAll = findViewById(R.id.editRecordSaveAllChanges);


        //Set all pre-defined values for the record
        title.setText(record.getTitle());
        commentInput.setText(record.getComment());
        final LocalDateTime currentDate = record.getTimestamp();
        String currentDateString = currentDate.format(formatter);
        dateInput.setText(currentDateString);


        //for selecting custom date
        //Based on adj-feelsbook by Anders Johnson
        //https://github.com/ColonelSanders21/adj-FeelsBook
        editDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                        dateInput.setText(newDate.format(formatter));
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
        editTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                        dateInput.setText(newDate.format(formatter));
                    }
                };
                TimePickerDialog dialog = new TimePickerDialog(EditRecordActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_Alert,
                        timeSetListener, hour, minute,
                        true);
                dialog.show();
            }
        });


        geoLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Go to EditLocationActivity
                 */
            }
        });


        addRemovePhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Go to EditPhotosActivity
                 */
            }
        });

        saveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }
}

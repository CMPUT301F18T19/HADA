package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;

/**
 * Activity for editing problems to a given user's list of problems. edited from NewProblemActivity
 * @author Anders, Alex, Jason
 * @see Problem, Patient, ProblemListActivity
 * @version 1.1
 */
public class EditProblemActivity extends AppCompatActivity implements Serializable {
    private static final String TAG = "EditProblemActivity";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem);

        //Gets the problem to edit
        Intent intent = getIntent();
        final String problemFileId = intent.getStringExtra("problemFileId");
        final Problem problem = new ProblemController().getProblem(problemFileId);

        //set date to current date and time
        final TextView editProblemDate = findViewById(R.id.editProblemDate);
        final LocalDateTime currentDate = problem.getDate();
        String currentDateString = currentDate.format(formatter);
        editProblemDate.setText(currentDateString);

        //set title and description to current ones
        final EditText editProblemTitle = findViewById(R.id.editProblemTitle);
        final EditText editProblemDescription = findViewById(R.id.editProblemDescription);
        editProblemDescription.setText(problem.getDesc());
        editProblemTitle.setText(problem.getTitle());



        //for selecting custom date
        //Based on adj-feelsbook by Anders Johnson
        //https://github.com/ColonelSanders21/adj-FeelsBook
        Button changeDateButton = findViewById(R.id.editProblemChangeDateButton);
        changeDateButton.setOnClickListener(new View.OnClickListener() {
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
                        editProblemDate.setText(newDate.format(formatter));
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(EditProblemActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_Alert, dateSetListener,
                        year, month, day);
                dialog.show();
            }
        });

        //for selecting custom time
        //Based on adj-feelsbook by Anders Johnson
        //https://github.com/ColonelSanders21/adj-FeelsBook
        Button changeTimeButton = findViewById(R.id.editProblemChangeTimeButton);
        changeTimeButton.setOnClickListener(new View.OnClickListener() {
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
                        editProblemDate.setText(newDate.format(formatter));
                    }
                };
                TimePickerDialog dialog = new TimePickerDialog(EditProblemActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_Alert,
                        timeSetListener, hour, minute,
                        true);
                dialog.show();
            }
        });


        // save problem using a controller when button is pressed
        Button editProblemSaveButton = findViewById(R.id.editProblemSaveButton);
        editProblemSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editProblemTitle.getText().toString();
                String description = editProblemDescription.getText().toString();

                //Gets date as string and converts
                String dateString = editProblemDate.getText().toString();
                LocalDateTime date = LocalDateTime.parse(dateString, formatter);

                if(description.equals("")| title.equals("")){
                    Toast.makeText(EditProblemActivity.this, "Please enter a description and title.", Toast.LENGTH_SHORT).show();
                }
                else {
                    //TODO add offline exception

                    ProblemController problemController = new ProblemController();
                    problemController.editProblemTitle(problem, title);
                    problemController.editProblemDate(problem, date);
                    problemController.editProblemDesc(problem, description);

                    Toast.makeText(EditProblemActivity.this, "Problem saved!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "title = " + title);
                    Log.d(TAG, "date = " + date);
                    Log.d(TAG, "description = " + description);
                    finish();
                }
            }
        });
        Button editProblemDelete = findViewById(R.id.editProblemDelete);
        editProblemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProblemController().deleteProblem(problemFileId);
                finish();
            }
        });
    }

}
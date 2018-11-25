package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;

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

    private ListView recordsList;
    private ArrayList<Record> records;
    private Problem oldProblem;

    private int position;
    private int problemPosition;
    private int recordPosition;
    private String recordFileId;
    private Record record;
    private Problem problem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        Intent intent = getIntent();
        recordFileId = intent.getStringExtra("recordFileId");
        String LoggedInUser = LoggedInSingleton.getInstance().getLoggedInID();

        record = new RecordController().getRecord(recordFileId);

        Button editDate = (Button) findViewById(R.id.viewRecordEditDate);
        Button editTime = (Button) findViewById(R.id.viewRecordEditTime);
        Button editLocation = (Button) findViewById(R.id.viewRecordEditGeolocation);
        Button viewLocation = (Button) findViewById(R.id.viewRecordViewLocation);
        Button editPhotos = (Button) findViewById(R.id.viewRecordEditPhotos);
        final EditText editedComment = (EditText) findViewById(R.id.viewRecordComment);
        editedComment.setHint(record.getComment());

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Go to EditDateActivity
                 */
            }
        });


        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Go to EditTimeActivity
                 */
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

        viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Go to ViewLocationActivity
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
                    record.setComment(editedComment.getText().toString());
                    finish();
                }
                return false;
            }
        });


    }
}

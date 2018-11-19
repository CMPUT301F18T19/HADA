package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;

public class ViewRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);


        Button editDate = (Button) findViewById(R.id.viewRecordEditDate);
        Button editTime = (Button) findViewById(R.id.viewRecordEditTime);
        Button editLocation = (Button) findViewById(R.id.viewRecordEditGeolocation);
        Button viewLocation = (Button) findViewById(R.id.viewRecordViewLocation);
        EditText editedComment = (EditText) findViewById(R.id.viewRecordComment);
        
    }
}

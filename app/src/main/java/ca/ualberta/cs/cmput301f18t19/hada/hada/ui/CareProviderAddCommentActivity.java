/*
 *  CMPUT 301 - Fall 2018
 *
 *  CareProviderAddCommentActivity.java
 *
 *  11/30/18 8:42 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

public class CareProviderAddCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_add_comment);

        final String parentId = getIntent().getStringExtra("parentId");
        Problem problem = new ProblemController().getProblem(parentId);

        //Set subtitle
        TextView subtitle = findViewById(R.id.careProviderAddCommentSubtitle);
        String subtitleText = "Problem: " + problem.getTitle();
        subtitle.setText(subtitleText);

        //Add comment button
        Button addComment = findViewById(R.id.careProviderAddCommentButton);
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textInput = findViewById(R.id.careProviderAddCommentTextInput);
                String commentText = textInput.getText().toString();
                //If string is not empty
                if(!commentText.isEmpty()){
                    new RecordController().addCommentRecord(parentId, commentText);
                    finish();
                }
                else{
                    Toast.makeText(CareProviderAddCommentActivity.this, "Please enter a comment first.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

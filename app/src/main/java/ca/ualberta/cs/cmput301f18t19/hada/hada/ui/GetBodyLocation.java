/*
 *  CMPUT 301 - Fall 2018
 *
 *  GetBodyLocation.java
 *
 *  29/11/18 2:08 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;

public class GetBodyLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_body_location);
        Button leftLegUpper = findViewById(R.id.getBodyLocationActivityRightLegUpper);
        leftLegUpper.setVisibility(View.VISIBLE);
        leftLegUpper.setBackgroundColor(Color.TRANSPARENT);
        leftLegUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "left leg upper",Toast.LENGTH_SHORT).show();
            }
        });
        Button rightLegUpper = findViewById(R.id.getBodyLocationActivityLeftLegUpper);
        rightLegUpper.setVisibility(View.VISIBLE);
        rightLegUpper.setBackgroundColor(Color.TRANSPARENT);
        rightLegUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "right leg upper",Toast.LENGTH_SHORT).show();
            }
        });
        Button rightLegLower = findViewById(R.id.getBodyLocationActivityRightLegLower);
        rightLegLower.setVisibility(View.VISIBLE);
        rightLegLower.setBackgroundColor(Color.TRANSPARENT);
        rightLegLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "right leg lower",Toast.LENGTH_SHORT).show();
            }
        });

    }
}

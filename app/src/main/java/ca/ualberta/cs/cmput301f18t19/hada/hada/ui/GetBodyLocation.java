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
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESBodyLocationManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.BodyLocation;

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
                Toast.makeText(GetBodyLocation.this, "right leg upper",Toast.LENGTH_SHORT).show();
                DoWork("RightLegUpper");
            }
        });
        Button rightLegUpper = findViewById(R.id.getBodyLocationActivityLeftLegUpper);
        rightLegUpper.setVisibility(View.VISIBLE);
        rightLegUpper.setBackgroundColor(Color.TRANSPARENT);
        rightLegUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "left leg upper",Toast.LENGTH_SHORT).show();
                DoWork("LeftLegUpper");
            }
        });
        Button rightLegLower = findViewById(R.id.getBodyLocationActivityRightLegLower);
        rightLegLower.setVisibility(View.VISIBLE);
        rightLegLower.setBackgroundColor(Color.TRANSPARENT);
        rightLegLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "right leg lower",Toast.LENGTH_SHORT).show();
                DoWork("RightLegLower");
            }
        });
        Button leftLegLower = findViewById(R.id.getBodyLocationActivityLeftLegLower);
        leftLegLower.setVisibility(View.VISIBLE);
        leftLegLower.setBackgroundColor(Color.TRANSPARENT);
        leftLegLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "left leg lower",Toast.LENGTH_SHORT).show();
                DoWork("LeftLegLower");
            }
        });
        Button stomach = findViewById(R.id.getBodyLocationActivityStomach);
        stomach.setVisibility(View.VISIBLE);
        stomach.setBackgroundColor(Color.TRANSPARENT);
        stomach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "Stomach",Toast.LENGTH_SHORT).show();
                DoWork("Stomach");
            }
        });
        Button chest = findViewById(R.id.getBodyLocationActivityChest);
        chest.setVisibility(View.VISIBLE);
        chest.setBackgroundColor(Color.TRANSPARENT);
        chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "Chest",Toast.LENGTH_SHORT).show();
                DoWork("Chest");
            }
        });
        Button head = findViewById(R.id.getBodyLocationActivityHead);
        head.setVisibility(View.VISIBLE);
        head.setBackgroundColor(Color.TRANSPARENT);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "Head",Toast.LENGTH_SHORT).show();
                DoWork("Head");
            }
        });
        Button rightArmLower = findViewById(R.id.getBodyLocationActivityRightArmLower);
        rightArmLower.setVisibility(View.VISIBLE);
        rightArmLower.setBackgroundColor(Color.TRANSPARENT);
        rightArmLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "right arm lower",Toast.LENGTH_SHORT).show();
                DoWork("RightArmLower");
            }
        });
        Button rightArmUpper = findViewById(R.id.getBodyLocationActivityRightArmUpper);
        rightArmUpper.setVisibility(View.VISIBLE);
        rightArmUpper.setBackgroundColor(Color.TRANSPARENT);
        rightArmUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "right arm upper",Toast.LENGTH_SHORT).show();
                DoWork("RightArmUpper");
            }
        });
        Button leftArmLower = findViewById(R.id.getBodyLocationActivityLeftArmLower);
        leftArmLower.setVisibility(View.VISIBLE);
        leftArmLower.setBackgroundColor(Color.TRANSPARENT);
        leftArmLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "left arm lower",Toast.LENGTH_SHORT).show();
                DoWork("LeftArmLower");
            }
        });
        Button leftArmUpper = findViewById(R.id.getBodyLocationActivityLeftArmUpper);
        leftArmUpper.setVisibility(View.VISIBLE);
        leftArmUpper.setBackgroundColor(Color.TRANSPARENT);
        leftArmUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "left arm upper",Toast.LENGTH_SHORT).show();
                DoWork("LeftArmUpper");
            }
        });



    }
    private void DoWork(String type){
        BodyLocation bodyLocation = new BodyLocation();
        bodyLocation.setBodyLocation(type);
        String uuid = bodyLocation.getFileID();
        //TODO Camera stuff
        //TODO return uuid to link to record
        new ESBodyLocationManager.AddBodyLocationTask().execute(bodyLocation);
    }
}

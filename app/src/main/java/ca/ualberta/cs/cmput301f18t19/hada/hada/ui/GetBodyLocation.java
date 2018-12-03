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

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.BodyLocationController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.PhotoController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.BitmapPhotoEncodeDecodeManager;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.BodyLocation;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos;

public class GetBodyLocation extends AppCompatActivity {
    Uri picture1;
    Uri picture2;
    String parentId;
    String location;
    String loggedInUser = LoggedInSingleton.getInstance().getLoggedInID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_body_location);

        Intent intent = getIntent();
        parentId = intent.getStringExtra("parentId");

        Button leftLegUpper = findViewById(R.id.getBodyLocationActivityRightLegUpper);
        leftLegUpper.setVisibility(View.VISIBLE);
        leftLegUpper.setBackgroundColor(Color.TRANSPARENT);
        leftLegUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "Right leg upper",Toast.LENGTH_SHORT).show();
                DoWork("RightLegUpper");
            }
        });
        Button rightLegUpper = findViewById(R.id.getBodyLocationActivityLeftLegUpper);
        rightLegUpper.setVisibility(View.VISIBLE);
        rightLegUpper.setBackgroundColor(Color.TRANSPARENT);
        rightLegUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "Left leg upper",Toast.LENGTH_SHORT).show();
                DoWork("LeftLegUpper");
            }
        });
        Button rightLegLower = findViewById(R.id.getBodyLocationActivityRightLegLower);
        rightLegLower.setVisibility(View.VISIBLE);
        rightLegLower.setBackgroundColor(Color.TRANSPARENT);
        rightLegLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "Right leg lower",Toast.LENGTH_SHORT).show();
                DoWork("RightLegLower");
            }
        });
        Button leftLegLower = findViewById(R.id.getBodyLocationActivityLeftLegLower);
        leftLegLower.setVisibility(View.VISIBLE);
        leftLegLower.setBackgroundColor(Color.TRANSPARENT);
        leftLegLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "Left leg lower",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(GetBodyLocation.this, "Right arm lower",Toast.LENGTH_SHORT).show();
                DoWork("RightArmLower");
            }
        });
        Button rightArmUpper = findViewById(R.id.getBodyLocationActivityRightArmUpper);
        rightArmUpper.setVisibility(View.VISIBLE);
        rightArmUpper.setBackgroundColor(Color.TRANSPARENT);
        rightArmUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "Right arm upper",Toast.LENGTH_SHORT).show();
                DoWork("RightArmUpper");
            }
        });
        Button leftArmLower = findViewById(R.id.getBodyLocationActivityLeftArmLower);
        leftArmLower.setVisibility(View.VISIBLE);
        leftArmLower.setBackgroundColor(Color.TRANSPARENT);
        leftArmLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "Left arm lower",Toast.LENGTH_SHORT).show();
                DoWork("LeftArmLower");
            }
        });
        Button leftArmUpper = findViewById(R.id.getBodyLocationActivityLeftArmUpper);
        leftArmUpper.setVisibility(View.VISIBLE);
        leftArmUpper.setBackgroundColor(Color.TRANSPARENT);
        leftArmUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "Left arm upper",Toast.LENGTH_SHORT).show();
                DoWork("LeftArmUpper");
            }
        });


    }
    private void DoWork(String type){
        location = type;
        Photos photo = new PhotoController().getRefPhoto(loggedInUser + type);
        if(photo == null) { //if no ref photo is present - DO this
            Intent intent = new Intent(GetBodyLocation.this, CameraActivity.class);
            intent.putExtra("TYPE", "400");
            startActivityForResult(intent, 400);
            Toast.makeText(GetBodyLocation.this, "Take photos", Toast.LENGTH_SHORT).show();
        }
        else{ //If the ref image is already saved for the user, just make a new record linking to that ref image.
            BodyLocation bodyLocation = new BodyLocation();
            bodyLocation.setRefImageFileId(photo.getFileID());
            bodyLocation.setBodyLocation(type);
            new BodyLocationController().addBodyLocation(bodyLocation, parentId);
            Intent intent2 = new Intent();
            setResult(RESULT_OK, intent2);
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 400) {
            if (resultCode == RESULT_OK) {
                picture1 = Uri.parse(data.getStringExtra("URI"));
                Intent intent = new Intent(GetBodyLocation.this,CameraActivity.class);
                intent.putExtra("TYPE","500");
                startActivityForResult(intent, 500);
            }
        }
        if (requestCode == 500) {
            if (resultCode == RESULT_OK) {
                picture2 = Uri.parse(data.getStringExtra("URI"));
                buildBody();
            }
        }
    }

    public void buildBody(){
        BodyLocation bodyLocation = new BodyLocation();
        bodyLocation.setBodyLocation(location);
        Bitmap bitmap1;
        Bitmap bitmap2;
        String refImageBitmapString = null;
        try {
            bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picture1);
            bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picture2);
            Bitmap combined = overlay(bitmap1,bitmap2);
             refImageBitmapString = new BitmapPhotoEncodeDecodeManager.EncodeBitmapTask().execute(combined).get();
             Log.d("buildBody", refImageBitmapString);
            //Uri comb = saveImage(combined);
            //bodyLocation.setPhotoUri(comb.toString()); set coords instead
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        String refImageFileID = loggedInUser + location;
        bodyLocation.setRefImageFileId(refImageFileID);
        new PhotoController().addRefPhoto(refImageBitmapString,location);
        new BodyLocationController().addBodyLocation(bodyLocation, parentId);
        Intent intent2 = new Intent();
        setResult(RESULT_OK, intent2);
        finish();

    }

    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        //Courtesy of Stackoverflow user xil3 (Original author - https://stackoverflow.com/users/380579/xil3)
        // and adneal (Editor - https://stackoverflow.com/users/420015/adneal)  - Post: https://stackoverflow.com/a/4863551/10454730
        Bitmap cs = null;

        int width, height = 0;

        if(bmp1.getWidth() > bmp2.getWidth()) {
            width = bmp1.getWidth() + bmp2.getWidth();
            height = bmp1.getHeight();
        } else {
            width = bmp2.getWidth() + bmp2.getWidth();
            height = bmp1.getHeight();
        }

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(bmp1, 0f, 0f, null);
        comboImage.drawBitmap(bmp2, bmp1.getWidth(), 0f, null);
        return cs;
    }
}

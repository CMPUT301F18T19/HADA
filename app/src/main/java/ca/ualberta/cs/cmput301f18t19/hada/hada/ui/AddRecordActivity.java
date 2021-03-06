/*
 * CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 11/11/18 12:41 PM
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 * Last modified 11/11/18 12:41 PM
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.PhotoController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.BitmapPhotoEncodeDecodeManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Adds a new record with selected photos and, depending on the position of the geo location
 * switch, geographical data can also be included. Multiple photos can be selected and an
 * optional comment can be included.
 * <p>
 * Location permissions based off example by Yashas on StackOverFlow
 *
 * @author Christopher Penner
 * @version 0.1
 * @see <a href="https://stackoverflow.com/a/51350622">StackOverflow example by Yashas</a>
 */
public class AddRecordActivity extends AppCompatActivity {
    private Uri imageURI;
    private ArrayList<String> imageStrings;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    private String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
    private int requestCode = 1;
    private LatLng chosenLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        imageStrings = new ArrayList<>();

        //Gets parentId
        Intent intent = getIntent();
        final String parentId = intent.getStringExtra("problemFileId");


        //Generates UUID so that we can save bodyLocation/photos etc and tie them back to the record.
        final String fileId = UUID.randomUUID().toString();



        Button takePhoto = findViewById(R.id.addRecordActivityTakePhoto);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecordActivity.this, CameraActivity.class);
                intent.putExtra("TYPE","100");
                startActivityForResult(intent, 100);
                Toast.makeText(AddRecordActivity.this, "Take photos", Toast.LENGTH_SHORT).show();
            }
        });
        Button refPhoto = findViewById(R.id.addRecordActivityAddReferenceImage);
        refPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecordActivity.this, GetBodyLocation.class);
                intent.putExtra("parentId", fileId);
                startActivityForResult(intent,200);
            }
        });


        //Saves the record
        Button saveRecord = findViewById(R.id.addRecordActivitySaveButton);
        Button addGeoLocation = findViewById(R.id.addRecordActivityAddGeoButton);
        final EditText addTitle = findViewById(R.id.addRecordActivityTitle);
        final EditText addComment = findViewById(R.id.addRecordActivityComment);
        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        saveRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = addTitle.getText().toString();
                String comment = addComment.getText().toString();
                //TODO: Saving photos
                Record record = new Record();
                record.setFileId(fileId);
                try {

                    new PhotoController().addPhoto(record.getFileId(), imageStrings);


                }catch (Exception e){
                    Log.d("AddRecord", "Failed to add photo");
                }
                Log.d("AddRecord", "New Record: title=" + record.getTitle()+ " timestamp=" +record.getTimestamp().toString());
                record.setComment(comment);
                record.setTitle(title);
                Log.d("AddRecord", "New Record: title=" + record.getTitle()+ " comment=" +record.getComment()+ " timestamp=" +record.getTimestamp().toString());
                if (chosenLocation != null) {
                    try {
                        record.setLocation(chosenLocation);

                        record.setTimestamp(LocalDateTime.now());
                      
                        Log.d("AddRecord", "New Record: title=" + record.getTitle()+ " comment=" +record.getComment() + " location="+record.getLocation().toString()+ " timestamp=" +record.getTimestamp().toString());

                        new RecordController().addRecord(record, parentId);
                        finish();
                      
                    } catch (SecurityException e) {
                        Toast.makeText(AddRecordActivity.this, "Unable to save location. Please enable the location permissions.", Toast.LENGTH_SHORT).show();
                        }
                }
                new RecordController().addRecord(record, parentId);
                finish();

            }
        });

        addGeoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //We check for location permissions here before we load AddGeoToRecordActivity
                if (!EasyPermissions.hasPermissions(AddRecordActivity.this, perms)) {
                    requestLocationPermission();
                } else {
                    Intent intent = new Intent(AddRecordActivity.this, AddGeoToRecordActivity.class);
                    startActivityForResult(intent, requestCode);
                }

            }
        });
    }

    /**
     * Deals with retrieving the location set by the user.
     *
     * @param requestCode: The code we specified when starting the activity
     * @param resultCode:  The result we got from AddGeoToRecordActivity
     * @param intent:      The intent retrieved from AddGeoToRecordActivity
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                chosenLocation = intent.getExtras().getParcelable("Location");
                TextView selectedLoc = findViewById(R.id.AddRecordActivityLocationSelectedTitle);
                selectedLoc.setText("Location: " + chosenLocation.toString());
            }
        }
        else if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                imageURI = Uri.parse(intent.getStringExtra("URI"));
                ImageView imagePreview = findViewById(R.id.addRecordActivityImagePreview);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageURI);
                    String fullSizeImage = new BitmapPhotoEncodeDecodeManager.EncodeBitmapTask().execute(bitmap).get();
                    imageStrings.add(fullSizeImage);
                    Bitmap smallBitmap = new BitmapPhotoEncodeDecodeManager.DecodeBitmapTask().execute(fullSizeImage).get();
                    imagePreview.setImageBitmap(smallBitmap);
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
//                    byte[] byteArray = byteArrayOutputStream .toByteArray();
//                    imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);


//                    byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
//                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                }catch ( Exception e){
                    Log.d("AddRecordActivity", "Got URI but couldn't convert");
                }
                //imageURI = Uri.parse(intent.getStringExtra("URI"));
            }
        }
        else if (requestCode == 200){
            Toast.makeText(this, "Body location added!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "An error occurred. Please try again. Request code: " + requestCode, Toast.LENGTH_SHORT).show();
        }
    }

    //based off of Yasha's answer on StackOverflow https://stackoverflow.com/a/51350622
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantresults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantresults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantresults, this);
    }

    //based off of Yasha's answer on StackOverflow https://stackoverflow.com/a/51350622
    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }


}

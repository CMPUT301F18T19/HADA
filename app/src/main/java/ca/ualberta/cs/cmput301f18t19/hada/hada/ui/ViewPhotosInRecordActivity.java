/*
 *  CMPUT 301 - Fall 2018
 *
 *  ViewPhotosInRecordActivity.java
 *
 *  29/11/18 12:56 AM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.PhotoController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

public class ViewPhotosInRecordActivity extends AppCompatActivity {

    private ArrayList<Bitmap> bitmaps;
    private CustomPhotoAdapter customPhotoAdapter;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos_in_record);

        Intent intent = getIntent();
        String recordFileId = intent.getStringExtra("recordFileId");
        Record record = new RecordController().getRecord(recordFileId);
        ArrayList<Uri> imgs = new PhotoController().getPhotos(record);


/*
        for (int i  = 0; i < imgs.size(); i++) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imgs.get(i));
                bitmaps.add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */
        viewPager = findViewById(R.id.viewPhotosInRecordActivityViewPager);
        customPhotoAdapter = new CustomPhotoAdapter(this);
        viewPager.setAdapter(customPhotoAdapter);








    }
}

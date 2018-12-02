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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.PhotoController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

import static android.graphics.drawable.Drawable.createFromStream;

public class ViewPhotosInRecordActivity extends AppCompatActivity {

    public ArrayList<Uri> imgs;
    private ArrayList<Bitmap> bitmaps;
    private CustomPhotoAdapter customPhotoAdapter;
    ViewPager viewPager;
    GridView gridView;
    private ImageGridAdapter imageGridAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos_in_record);

        Intent intent = getIntent();
        String recordFileId = intent.getStringExtra("recordFileId");
        Record record = new RecordController().getRecord(recordFileId);
        //imgs = new PhotoController().getPhotos(record);


        
        for (int i  = 0; i < imgs.size(); i++) {
            try {
                bitmaps.add(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgs.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        gridView = findViewById(R.id.viewPhotosInRecordActivityGridView);
        imageGridAdapter = new ImageGridAdapter(this, bitmaps);
        gridView.setAdapter(imageGridAdapter);

        /*
        viewPager = findViewById(R.id.viewPhotosInRecordActivityViewPager);
        customPhotoAdapter = new CustomPhotoAdapter(this);
        viewPager.setAdapter(customPhotoAdapter);
*/







    }
}

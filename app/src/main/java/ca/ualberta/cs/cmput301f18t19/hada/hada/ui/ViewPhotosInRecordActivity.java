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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.PhotoController;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.BitmapPhotoEncodeDecodeManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos;

/**
 * The type View photos in record activity.
 *
 * @Author Austin Goebel, Joseph Potentier
 */
public class ViewPhotosInRecordActivity extends AppCompatActivity {

    private Photos photos_array;
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    /**
     * The Grid view.
     */
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos_in_record);

        Intent intent = getIntent();
        String recordFileId = intent.getStringExtra("recordFileId");
        photos_array = new PhotoController().getPhotos(recordFileId);
        
        for (int i  = 0; i < photos_array.getBitmaps().size(); i++) {
            String imgString = photos_array.getBitmaps().get(i);
            Bitmap bp = null;
            try {
                bp = new BitmapPhotoEncodeDecodeManager.DecodeBitmapTask().execute(imgString).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bitmaps.add(bp);
        }
        gridView = findViewById(R.id.viewPhotosInRecordActivityGridView);
        gridView.setAdapter(new ImageGridAdapter(this, bitmaps));
    }
}

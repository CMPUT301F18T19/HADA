package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.PhotoController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.BitmapPhotoEncodeDecodeManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

public class ViewSlideshow extends AppCompatActivity {

    private ArrayList<Bitmap> bitmaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slideshow);

        Intent intent = getIntent();
        String problemFileId = intent.getStringExtra("problemFileId");

        ArrayList<Record> recordList = new RecordController().getRecordList(problemFileId);



        for (int i = 0; i<recordList.size(); i++) {
            String recordFileId = recordList.get(i).getFileId();
            Photos photos_array = new PhotoController().getPhotos(recordFileId);
            String bpString = photos_array.getBitmaps().get(0);
            try {
                Bitmap bp = new BitmapPhotoEncodeDecodeManager.DecodeBitmapTask().execute(bpString).get();
                bitmaps.add(bp);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        ViewPager viewPager = findViewById(R.id.viewSlideshowActivityViewPager);
        CustomPhotoAdapter adapter = new CustomPhotoAdapter(this, bitmaps);
        viewPager.setAdapter(adapter);



    }
}

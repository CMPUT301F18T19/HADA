package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.PhotoController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.BitmapPhotoEncodeDecodeManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos;

public class PickBodyDotActivity extends AppCompatActivity {

    ImageView marker;
    ArrayList<Integer> touchCoords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_body_dot);

        Intent intent = getIntent();
        String refImageFileId = intent.getStringExtra("refImageFileId");
        Photos photo = new PhotoController().getRefPhoto(refImageFileId);
        String bitmapAsString = photo.getBitmaps().get(0);
        Bitmap bodyImage = null;
        try {
            bodyImage = new BitmapPhotoEncodeDecodeManager.DecodeBitmapTask().execute(bitmapAsString).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Set image
        final ImageView bodyImageView = findViewById(R.id.pickBodyDotImageView);
        bodyImageView.setImageBitmap(bodyImage);
        final Bitmap redraw = bodyImage;

        marker = findViewById(R.id.pickBodyDotMarker);
        RelativeLayout layout = findViewById(R.id.popupBodyLocRLayout);
        layout.setOnTouchListener(new View.OnTouchListener() {
            //courtesy of https://stackoverflow.com/a/14165779/10454730
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int eventAction = motionEvent.getAction();
                switch(eventAction) {
                    case MotionEvent.ACTION_DOWN:
                        float TouchX = motionEvent.getX();
                        float TouchY = motionEvent.getY();
                        placeImage(TouchX, TouchY);
                        bodyImageView.setImageBitmap(redraw);
                        marker.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });

        Button saveButton = findViewById(R.id.pickBodyDotSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(touchCoords.size() < 2){
                    Toast.makeText(PickBodyDotActivity.this, "Please select a spot on image for reference.", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("touchX", touchCoords.get(0).toString());
                    intent.putExtra("touchY", touchCoords.get(1).toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    private void placeImage(float X, float Y) {
        int touchX = (int) X;
        int touchY = (int) Y;

        //placing at center of touch
        int viewWidth = marker.getWidth();
        int viewHeight = marker.getHeight();
        viewWidth = viewWidth / 2;
        viewHeight = viewHeight / 2;

        marker.layout(touchX - viewWidth, touchY - viewHeight, touchX + viewWidth, touchY + viewHeight);
        touchCoords = new ArrayList<>();
        touchCoords.add(touchX);
        touchCoords.add(touchY);

    }
}


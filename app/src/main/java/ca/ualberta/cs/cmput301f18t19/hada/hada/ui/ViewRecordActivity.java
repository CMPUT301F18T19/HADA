package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.widget.Toast;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.BodyLocationController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.PhotoController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.BitmapPhotoEncodeDecodeManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.BodyLocation;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * Activity to view a single record from a problem.
 *
 * @author Austin
 * @see Record
 */
public class ViewRecordActivity extends AppCompatActivity {

    private Record record;
    private String recordFileId;
    private ArrayList<Integer> touchCoords;
    ImageView marker;
    /**
     * The Body location popup.
     */
    Dialog bodyLocationPopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        Intent intent = getIntent();
        bodyLocationPopup = new Dialog(this);
        recordFileId = intent.getStringExtra("recordFileId");
        record = new RecordController().getRecord(recordFileId);

        // Goes to view GeoLocation
        Button viewGeoLocation = findViewById(R.id.viewRecordActivityGeolocation);
        viewGeoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(record.getLocationArrayList() != null) {
                    Intent intent = new Intent(ViewRecordActivity.this, ViewSingleRecordLocationActivity.class);
                    intent.putExtra("recordFileId", recordFileId);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ViewRecordActivity.this, "This record does not have a geo location.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Goes to view BodyLocation
        Button viewBodyLocation = findViewById(R.id.viewRecordActivityViewBodyLocation);
        viewBodyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BodyLocation bodyLocation = new BodyLocationController().getABodyLocation(recordFileId);
                if(bodyLocation != null) {
                    showPopup(v);
                }
                else{
                    Toast.makeText(ViewRecordActivity.this, "This record does not have a body location.", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    protected void onResume() {
        super.onResume();

        record = new RecordController().getRecord(recordFileId);
        TextView titleText = findViewById(R.id.viewRecordActivityTitle);
        TextView commentText = findViewById(R.id.viewRecordActivityComment);
        TextView timeText = findViewById(R.id.viewRecordActivityTimestamp);

        ImageView imagePreview = findViewById(R.id.viewRecordActivityImage);
        Photos recordPhotos = new PhotoController().getPhotos(recordFileId);
        if(recordPhotos!= null){
            try{
                if(recordPhotos.getBitmaps().size() > 0 && recordPhotos.getBitmaps().get(0) != null) {
                    //https://stackoverflow.com/questions/3801760/android-code-to-convert-base64-string-to-bitmap
                    String photoString = recordPhotos.getBitmaps().get(0);
                    Log.d("ViewRecordActivity", "Photostring is " +photoString);
                    Bitmap bitmap = new BitmapPhotoEncodeDecodeManager.DecodeBitmapTask().execute(photoString).get();
                    imagePreview.setImageBitmap(bitmap);
                }
            }catch (Exception e){
                Log.d("ViewRecordActivity", "Failed to decode image");
            }
        }
        titleText.setText(record.getTitle());
        commentText.setText(record.getComment());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime timestamp = record.getTimestamp();
        timeText.setText(timestamp.format(formatter));

        imagePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRecordActivity.this, ViewPhotosInRecordActivity.class);
                intent.putExtra("recordFileId", recordFileId);
                startActivity(intent);

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


    }

    /**
     * Show a popup that displays the body location if the user
     *
     * @param v the v
     */
    public void showPopup(View v){
        TextView title;
        ImageButton exit;
        ImageView imageView;
        bodyLocationPopup.setContentView(R.layout.popup_body_location);
        title = bodyLocationPopup.findViewById(R.id.popupBodyLocTitle);
        exit = bodyLocationPopup.findViewById(R.id.popupBodyLocExitButton);
        imageView = bodyLocationPopup.findViewById(R.id.popupBodyLocImageView);
        RelativeLayout relativeLayout = bodyLocationPopup.findViewById(R.id.popupBodyLocRLayout);
        marker = relativeLayout.findViewById(R.id.popupBodyLocMarker);

        //Setup exit button
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bodyLocationPopup.dismiss();
            }
        });

        BodyLocation bodyLocation = new BodyLocationController().getABodyLocation(recordFileId);
        String bodyLocationType = bodyLocation.getBodyLocation();

        //Setup title
        title.setText(bodyLocationType);

        //Setup image
        Photos photos = new PhotoController().getRefPhoto(bodyLocation.getRefImageFileId());
        String photoString = photos.getBitmaps().get(0);
        Bitmap bitmap = null;
        try {
            bitmap = new BitmapPhotoEncodeDecodeManager.DecodeBitmapTask().execute(photoString).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);

        ArrayList coords = bodyLocation.getCoords();
        Integer coordX = (int) coords.get(0);
        Integer coordY = (int) coords.get(1);
        bodyLocationPopup.show();
        //marker.setVisibility(View.VISIBLE);
        placeImage(coordX, coordY);
    }
}

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import ca.ualberta.cs.cmput301f18t19.hada.hada.R;

import java.io.File;
import java.lang.reflect.Method;

public class CameraActivity extends AppCompatActivity {

    Uri imageFileUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton button = (ImageButton) findViewById(R.id.cameraImageButton);
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v){
                takeAPhoto();
            }
        };
        button.setOnClickListener(listener);
    }

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    public void takeAPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/images";
        File folderF = new File(folder);
        if (!folderF.exists()) {
            folderF.mkdir();
        }

        //if(Build.VERSION.SDK_INT>=16) {
        try {
            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
            m.invoke(null);

        } catch (Exception e) {
            e.printStackTrace();
        }


        //verifyPermission(this);

        String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";

        File imageFile = new File(folder,"imagetest.jpg");
        imageFileUri = Uri.fromFile(imageFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            TextView tv = (TextView) findViewById(R.id.cameraStatus);
            if (resultCode == RESULT_OK) {
                tv.setText("Photo OK!");
                ImageButton button = (ImageButton) findViewById(R.id.cameraImageButton);
                button.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
            } else if (resultCode == RESULT_CANCELED) {
                tv.setText("Photo canceled");
            } else {
                tv.setText("Not sure what happened!" + resultCode);
            }
        }
    }
}
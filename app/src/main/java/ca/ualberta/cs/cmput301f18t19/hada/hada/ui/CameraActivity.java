package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public class CameraActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    Uri imageFileUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ImageButton button = (ImageButton) findViewById(R.id.cameraImageButton);
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v){
                takeAPhoto();
            }
        };
        button.setOnClickListener(listener);
        final Button savePhoto = findViewById(R.id.cameraActivtySave);
        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton();
            }
        });
    }

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    public void takeAPhoto() {


        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this,perms)){
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
            File imageFile = new File(folder,String.valueOf(System.currentTimeMillis()) + ".jpg");
            imageFileUri = Uri.fromFile(imageFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
        else {
            EasyPermissions.requestPermissions(CameraActivity.this, "We need perms to take pictures", CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE, perms);
        }

        //String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            TextView tv = (TextView) findViewById(R.id.cameraStatus);
            Toast.makeText(CameraActivity.this, Integer.toString(resultCode), Toast.LENGTH_SHORT).show();
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
    private void saveButton (){
        Intent intent = new Intent();
        intent.putExtra("URI",imageFileUri.toString());
        setResult(RESULT_OK, intent);
        finish();
    }

}
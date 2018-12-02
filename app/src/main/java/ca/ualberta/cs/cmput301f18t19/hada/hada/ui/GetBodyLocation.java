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

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESBodyLocationManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.BodyLocation;
import pub.devrel.easypermissions.EasyPermissions;

public class GetBodyLocation extends AppCompatActivity {
    Uri picture1;
    Uri picture2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_body_location);

        Button leftLegUpper = findViewById(R.id.getBodyLocationActivityRightLegUpper);
        leftLegUpper.setVisibility(View.VISIBLE);
        leftLegUpper.setBackgroundColor(Color.TRANSPARENT);
        leftLegUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "right leg upper",Toast.LENGTH_SHORT).show();
                DoWork("RightLegUpper");
            }
        });
        Button rightLegUpper = findViewById(R.id.getBodyLocationActivityLeftLegUpper);
        rightLegUpper.setVisibility(View.VISIBLE);
        rightLegUpper.setBackgroundColor(Color.TRANSPARENT);
        rightLegUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "left leg upper",Toast.LENGTH_SHORT).show();
                DoWork("LeftLegUpper");
            }
        });
        Button rightLegLower = findViewById(R.id.getBodyLocationActivityRightLegLower);
        rightLegLower.setVisibility(View.VISIBLE);
        rightLegLower.setBackgroundColor(Color.TRANSPARENT);
        rightLegLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "right leg lower",Toast.LENGTH_SHORT).show();
                DoWork("RightLegLower");
            }
        });
        Button leftLegLower = findViewById(R.id.getBodyLocationActivityLeftLegLower);
        leftLegLower.setVisibility(View.VISIBLE);
        leftLegLower.setBackgroundColor(Color.TRANSPARENT);
        leftLegLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "left leg lower",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(GetBodyLocation.this, "right arm lower",Toast.LENGTH_SHORT).show();
                DoWork("RightArmLower");
            }
        });
        Button rightArmUpper = findViewById(R.id.getBodyLocationActivityRightArmUpper);
        rightArmUpper.setVisibility(View.VISIBLE);
        rightArmUpper.setBackgroundColor(Color.TRANSPARENT);
        rightArmUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "right arm upper",Toast.LENGTH_SHORT).show();
                DoWork("RightArmUpper");
            }
        });
        Button leftArmLower = findViewById(R.id.getBodyLocationActivityLeftArmLower);
        leftArmLower.setVisibility(View.VISIBLE);
        leftArmLower.setBackgroundColor(Color.TRANSPARENT);
        leftArmLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "left arm lower",Toast.LENGTH_SHORT).show();
                DoWork("LeftArmLower");
            }
        });
        Button leftArmUpper = findViewById(R.id.getBodyLocationActivityLeftArmUpper);
        leftArmUpper.setVisibility(View.VISIBLE);
        leftArmUpper.setBackgroundColor(Color.TRANSPARENT);
        leftArmUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GetBodyLocation.this, "left arm upper",Toast.LENGTH_SHORT).show();
                DoWork("LeftArmUpper");
            }
        });



    }
    private void DoWork(String type){
        BodyLocation bodyLocation = new BodyLocation();
        bodyLocation.setBodyLocation(type);
        String uuid = bodyLocation.getFileID();
        Intent intent = new Intent(GetBodyLocation.this, CameraActivity.class);
        intent.putExtra("TYPE","400");
        startActivityForResult(intent, 400);
        Toast.makeText(GetBodyLocation.this, "Take photos", Toast.LENGTH_SHORT).show();
        Intent intent3 = new Intent(GetBodyLocation.this,CameraActivity.class);
        intent3.putExtra("TYPE","500");
        startActivityForResult(intent3, 500);
        Bitmap bitmap1 = BitmapFactory.decodeFile(picture1.toString());
        Bitmap bitmap2 = BitmapFactory.decodeFile(picture2.toString());
        Bitmap combined = overlay(bitmap1,bitmap2);
        Uri comb = saveImage(combined);
        bodyLocation.setPhotoUri(comb.toString());
        //TODO Camera stuff
        new ESBodyLocationManager.AddBodyLocationTask().execute(bodyLocation);
        Intent intent2 = new Intent();
        intent2.putExtra("UUID",uuid);
        setResult(RESULT_OK, intent2);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 400) {
            if (resultCode == RESULT_OK) {
                picture1 = Uri.parse(data.getStringExtra("URI"));
            }
        }
        if (requestCode == 500) {
            if (resultCode == RESULT_OK) {
                picture2 = Uri.parse(data.getStringExtra("URI"));
            }
        }
    }

    public Uri saveImage(Bitmap bmp){
        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/images";
        File imageFile = new File(folder,String.valueOf(System.currentTimeMillis()) + ".jpg");
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this,perms)) {
            try (FileOutputStream out = new FileOutputStream(imageFile.getAbsoluteFile().toString())) {
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            EasyPermissions.requestPermissions(GetBodyLocation.this, "We need perms to take pictures", 600, perms);
            saveImage(bmp);
        }
        return Uri.fromFile(imageFile);
    }
    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }
}

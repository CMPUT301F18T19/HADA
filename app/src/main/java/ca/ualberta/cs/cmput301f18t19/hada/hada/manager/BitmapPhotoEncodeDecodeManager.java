/*
 *  CMPUT 301 - Fall 2018
 *
 *  BitmapPhotoEncodeDecodeManager.java
 *
 *  12/2/18 12:45 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class BitmapPhotoEncodeDecodeManager {

    public static class EncodeBitmapTask extends AsyncTask<Bitmap, Void, String>{
        @Override
        protected String doInBackground(Bitmap... params){
            Bitmap bitmap = params[0];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
            return imageString;
        }
    }

    public static class DecodeBitmapTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params){
            String imageString = params[0];
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return bitmap;
        }
    }
}

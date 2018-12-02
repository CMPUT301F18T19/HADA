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
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Manager for encoding bitmaps to string and vice versa.
 * Credit to: //https://stackoverflow.com/questions/3801760/android-code-to-convert-base64-string-to-bitmap
 * @author AndersJ
 * @version 1.0
 * @see ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos
 */
public class BitmapPhotoEncodeDecodeManager {

    /**
     * Encodes a bitmap into a base64 string as an AsyncTask.
     */
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

    /**
     * Decodes a bitmap into a base64 string as an AsyncTask.
     */
    public static class DecodeBitmapTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params){
            String imageString = params[0];
            Log.d("DecodeBitmapTask", "Decoding string " +imageString);
            byte[] decodedString = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return bitmap;
        }
    }
}

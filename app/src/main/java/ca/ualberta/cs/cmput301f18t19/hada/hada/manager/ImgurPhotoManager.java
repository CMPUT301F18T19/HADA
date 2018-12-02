/*
 *  CMPUT 301 - Fall 2018
 *
 *  ImgurPhotoManager.java
 *
 *  12/1/18 4:54 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import ca.ualberta.cs.cmput301f18t19.hada.hada.BuildConfig;

public class ImgurPhotoManager {

    public static class UploadPhotoTask extends AsyncTask<Uri, Void, String>{
        @Override
        protected String doInBackground(Uri... params){
            Uri uri = params[0];
            String imageUrl = null;
            Bitmap bitmap = BitmapFactory.decodeFile(uri.toString());

            // Creates Byte Array from picture
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); // Not sure whether this should be jpeg or png, try both and see which works best

            // opens connection and sends data
            try{
                URL url = new URL("http://api.imgur.com/3/image");
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(Base64.encode(baos.toByteArray(), Base64.DEFAULT).toString(), "UTF-8");
                data += "&" + URLEncoder.encode("Authorization: Client-ID ", "UTF-8") + "=" + URLEncoder.encode(BuildConfig.ImgurClientId, "UTF-8");
                Log.d("PhotoController",data);
                wr.write(data);
                wr.flush();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));

                String inputLine;

                while ((inputLine = in.readLine()) != null)
                    Log.d("PhotoController",inputLine);
                if (inputLine.contains("link")){
                    imageUrl = inputLine;
                    return imageUrl;
                }
                in.close();
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return imageUrl;
        }
    }
}

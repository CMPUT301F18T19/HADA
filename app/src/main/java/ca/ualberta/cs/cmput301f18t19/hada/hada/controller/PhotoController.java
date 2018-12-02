package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.net.URI;
import java.util.ArrayList;

import java.util.concurrent.ExecutionException;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESPhotoManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ImgurPhotoManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos;

public class PhotoController {
    public PhotoController(){}
/*
    public ArrayList<String> getPhotos(String photosID){
        ArrayList<String> uriList = new ArrayList<>();
        try {
            Photos photo = new ESPhotoManager.GetPhotoTask().execute(photosID).get();
            uriList = photo.getUriPhotos();
        }catch (Exception e){
            Log.d("getPhoto","couldnt get photos");
            e.printStackTrace();
        }
        if (uriList.size() != 0){
            for(String photo : uriList){
                File file = new File(URI.create(photo).getPath());
                if (!(file.exists())) {
                    int index = uriList.indexOf(photo);
                    //TODO download not found photos
                }
            }
        }
        return uriList;
    }
*/
    public void addPhoto(String parentId, Uri uri, Bitmap bitmap){
        //TODO upload image to imgur
        ArrayList<Photos> photos;
        Photos photo = new Photos();
        ArrayList<String> uriList = new ArrayList<>();
        ArrayList<String> httpList = new ArrayList<>();
        ArrayList<String> bitmaps = new ArrayList<>();
        String http = "None";
        try {
            photos = new ESPhotoManager.GetPhotoListTask().execute(parentId).get();
            if(photos.size()>0){
                photo = photos.get(0);
            }
            else {
                photo = new Photos();
            }
        } catch (Exception e) {
            Log.d("addPhoto", "Couldn't retrieve record list from ES");
            e.printStackTrace();
        }

        uriList.add(uri.toString());
        http = uploadImage(uri); //TODO uncomment this out when uploadImage works
        httpList.add(http);

        //Compressor for saving smaller bitmap strings
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            bitmaps.add(encoded);
        }catch (Exception e){
            Log.d("addPhoto", "Failed to compress bitmap");
        }
        photo.setHttpPhotos(httpList);
        photo.setUriPhotos(uriList);
        photo.setBitmaps(bitmaps);
        photo.setParentId(parentId);
        new ESPhotoManager.AddPhotosTask().execute(photo);
        //DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        //LocalDateTime timestamp = record.getTimestamp();
        //Log.d("AddRecord", "New Record: title=" + record.getTitle()+ " timestamp=" +timestamp.format(formatter));
    }


    // adapted from https://stackoverflow.com/questions/7111751/uploading-a-photo-via-imgur-on-android-programatically hrickards
    public String uploadImage(Uri uri){
        try{
            String http = new ImgurPhotoManager.UploadPhotoTask().execute(uri).get();
            return http;
        }catch (InterruptedException e){
            Log.d("uploadImage", "InterruptedException");
        }catch (ExecutionException e){
            Log.d("uploadImage", "ExecutionException");
        }
        return "DEBUG-STRING-UPLOADIMAGE-FAILED";
    }

}

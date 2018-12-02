package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
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
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;
import id.zelory.compressor.Compressor;

public class PhotoController {
    public PhotoController(){}

    public Photos getPhotos(String parentID){
        ArrayList<String> uriList = new ArrayList<>();
        Photos photos = null;
        try {
            ArrayList<Photos> photos_array = new ESPhotoManager.GetPhotoListTask().execute(parentID).get();
            if(photos_array.size() > 0){
                photos = photos_array.get(0);
            }
            //uriList = photo.getUriPhotos();
        }catch (Exception e){
            Log.d("getPhoto","couldnt get photos");
            e.printStackTrace();
        }
        return photos;
    }

    public void addPhoto(String parentId, Uri uri, String bitmapString){
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
        //http = uploadImage(uri); //TODO uncomment this out when uploadImage works
        httpList.add(http);
        bitmaps.add(bitmapString);
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

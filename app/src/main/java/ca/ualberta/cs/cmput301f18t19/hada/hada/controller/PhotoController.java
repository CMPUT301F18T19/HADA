package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESPhotoManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos;

public class PhotoController {
    public PhotoController(){}

    public Photos getPhotos(String parentID){
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


}

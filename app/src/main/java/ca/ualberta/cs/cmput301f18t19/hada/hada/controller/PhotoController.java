package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESPhotoManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos;

/**
 * Controller for Photos objects.
 *
 * @author AndersJ, Jason, Joe
 * @version 2.0
 * @see Photos
 * @see ESPhotoManager
 */
public class PhotoController {
    /**
     * Instantiates a new Photo controller.
     */
    public PhotoController(){}

    /**
     * Get the photos object for a given parentId.
     *
     * @param parentID the parent id
     * @return the photos
     */
    public Photos getPhotos(String parentID){
        Photos photos = null;
        try {
            ArrayList<Photos> photos_array = new ESPhotoManager.GetPhotoListTask().execute(parentID).get();
            if(photos_array.size() > 0){
                photos = photos_array.get(0);
            }
        }catch (Exception e){
            Log.d("getPhoto","couldnt get photos");
            e.printStackTrace();
        }
        return photos;
    }

    /**
     * Add photo to a given parentId.
     *
     * @param parentId     the parent id
     * @param bitmapString the bitmap string
     */
    public void addPhoto(String parentId, String bitmapString){
        //TODO upload image to imgur
        ArrayList<Photos> photos;
        Photos photo = new Photos();
        ArrayList<String> bitmaps = new ArrayList<>();
        try {
            photos = new ESPhotoManager.GetPhotoListTask().execute(parentId).get();
            if(photos.size()>0){
                photo = photos.get(0);
                bitmaps = photo.getBitmaps();
            }
        } catch (Exception e) {
            Log.d("addPhoto", "Couldn't retrieve record list from ES");
            e.printStackTrace();
        }
        bitmaps.add(bitmapString);
        photo.setBitmaps(bitmaps);
        photo.setParentId(parentId);
        new ESPhotoManager.AddPhotosTask().execute(photo);
        //DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        //LocalDateTime timestamp = record.getTimestamp();
        //Log.d("AddRecord", "New Record: title=" + record.getTitle()+ " timestamp=" +timestamp.format(formatter));
    }


}

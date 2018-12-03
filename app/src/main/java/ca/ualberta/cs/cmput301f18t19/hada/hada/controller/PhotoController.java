package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.util.Log;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.LSPhotoManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESPhotoManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.SyncManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ContextSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.LoggedInSingleton;
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
    SyncManager syncManager;
    /**
     * Instantiates a new Photo controller.
     */
    public PhotoController(){
        syncManager = new SyncManager();
        syncManager.syncDB2ES();
    }

    /**
     * Get the photos object for a given parentId.
     *
     * @param parentID the parent id
     * @return the photos
     */
    public Photos getPhotos(String parentID){
        if(syncManager.isConnectedINET()){
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
        }else{
            return new LSPhotoManager(ContextSingleton.getInstance().getContext()).getPhoto(parentID);
        }

    }

    /**
     * Add photo to a given parentId.
     *
     * @param parentId     the parent id
     * @param bitmapString the bitmap string
     */
    public void addPhoto(String parentId, String bitmapString){

        Photos photos = new LSPhotoManager(ContextSingleton.getInstance().getContext()).getPhoto(parentId);
        if(!new LSPhotoManager(ContextSingleton.getInstance().getContext()).existsPhoto(photos.getFileID())){
            new LSPhotoManager(ContextSingleton.getInstance().getContext()).addPhoto(photos);
        }
        if(photos.getBitmaps() != null){
            ArrayList<String> bitmaps = photos.getBitmaps();
            bitmaps.add(bitmapString);
            photos.setBitmaps(bitmaps);

        }else{
            ArrayList<String> bitmaps = new ArrayList<>();
            bitmaps.add(bitmapString);
            photos.setBitmaps(bitmaps);
        }

        String newBitmapString = new LSPhotoManager(ContextSingleton.getInstance().getContext())
                .bitmaps2json(photos.getBitmaps());
        new LSPhotoManager(ContextSingleton.getInstance().getContext())
                .editPhotoBitmap(photos.getFileID(), newBitmapString);
        syncManager.syncDB2ES();
//        ArrayList<Photos> photos;
//        Photos photo = new Photos();
//        ArrayList<String> bitmaps = new ArrayList<>();
//        try {
//            photos = new ESPhotoManager.GetPhotoListTask().execute(parentId).get();
//            if(photos.size()>0){
//                photo = photos.get(0);
//                bitmaps = photo.getBitmaps();
//            }
//        } catch (Exception e) {
//            Log.d("addPhoto", "Couldn't retrieve record list from ES");
//            e.printStackTrace();
//        }
//        bitmaps.add(bitmapString);
//        photo.setBitmaps(bitmaps);
//        photo.setParentId(parentId);
//        new ESPhotoManager.AddPhotosTask().execute(photo);

    }

    public void addRefPhoto(String bitmapString, String bodyLocation){
        String username = LoggedInSingleton.getInstance().getLoggedInID();
        ArrayList<String> bitmap = new ArrayList<>();
        bitmap.add(bitmapString);
        Photos refPhoto = new Photos();
        refPhoto.setBitmaps(bitmap); //the stitched together photo
        refPhoto.setParentId(username); //the patient that is logged in
        String fileId = username + bodyLocation; //we append the bodyLocation to the username, creating a unique fileId.
        refPhoto.setFileID(fileId);
        new ESPhotoManager.AddPhotosTask().execute(refPhoto);
    }

    public Photos getRefPhoto(String refImageFileId){
        Photos photo = null;
        try {
            photo = new ESPhotoManager.GetAPhotoTask().execute(refImageFileId).get();
        }catch (Exception e){
            Log.d("getRefPhoto","couldn't get photos");
            e.printStackTrace();
        }
        return photo;
    }


}

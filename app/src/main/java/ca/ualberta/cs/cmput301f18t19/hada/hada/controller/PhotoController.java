package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.content.Context;
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
    private SyncManager syncManager;
    private Context context = ContextSingleton.getInstance().getContext();

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
            return new LSPhotoManager(context).getPhotoByParentID(parentID);
        }
    }

    /**
     * Add photo to a given parentId.
     *
     * @param parentId     the parent id
     * @param bitmapString the bitmap string
     */
    public void addPhoto(String parentId, String bitmapString){
        Photos newPhoto = new Photos();
        newPhoto.setParentId(parentId);
        ArrayList<String> bitmaps = new ArrayList<>();
        bitmaps.add(bitmapString);
        newPhoto.setBitmaps(bitmaps);

        if (syncManager.isConnectedINET()){
            new ESPhotoManager.AddPhotosTask().execute(newPhoto);
        }
        else {
            new LSPhotoManager(context).addPhoto(newPhoto);
        }
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

        if (syncManager.isConnectedINET()){
            new ESPhotoManager.AddPhotosTask().execute(refPhoto);
        }
        else {
            new LSPhotoManager(context).addPhoto(refPhoto);
        }

    }

    public Photos getRefPhoto(String refImageFileId){
        Photos photo = null;
        if (syncManager.isConnectedINET()){
            try {
                photo = new ESPhotoManager.GetAPhotoTask().execute(refImageFileId).get();
            }catch (Exception e){
                Log.d("getRefPhoto","couldn't get photos");
                e.printStackTrace();
            }
        }
        else {
            photo = new LSPhotoManager(context).getPhotoByFileID(refImageFileId);
        }
        return photo;
    }
}

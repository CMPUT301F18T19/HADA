package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.content.Context;
import android.util.Log;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos;

/**
 * Manager class that provides an interface for interacting with SQLite database.
 * Used for saving, loading, deleting records from/to the database.
 *
 * @author Alex
 * @see Photos
 */
public class LSPhotoManager {
    private File INTERNAL_DIR;

    /**
     * Constructor that uses DBOpenHelper to open the database
     */
    public LSPhotoManager(Context context){
        INTERNAL_DIR = context.getFilesDir();

    }

    /**
     * Adds a photo to the database
     * @param photo a Photos class obj to be added to the database
     */
    public void addPhoto(Photos photo) {
        try{
            File file = new File(INTERNAL_DIR, photo.getFileID());
            FileOutputStream fos= new FileOutputStream(file);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(photo);
            oos.close();
            fos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Loads a photo from the database
     * @param  fileID a photo's fileID
     * @return a photo, a Photos class obj.
     * Null if the photo is not found
     */
    public Photos getPhoto(String fileID) {
        try {
            File file = new File(INTERNAL_DIR, fileID);
            FileInputStream fos = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fos);
            Photos photo = (Photos) ois.readObject();
            return photo;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Delete a photo with given fileID from the database
     * @param  fileID a photo's fileID
     * @return whether the photo was successfully deleted
     */
    public Boolean deletePhoto(String fileID) {
        File file = new File(INTERNAL_DIR, fileID);
        boolean deleted = file.delete();
        return deleted;
    }

    /**
     * check if a photo with fileID exists in the database
     * @param  fileID a photo's fileID
     * @return true if photo exists, false otherwise
     */
    public Boolean existsPhoto(String fileID) {
        File file = new File(INTERNAL_DIR, fileID);
        return file.exists();
    }

    /**
     * get a list of photos in the database that are not synced to
     * the ES server
     * @return list of photos that are not synced to the ES server
     */
    public ArrayList<Photos> getUnSyncedPhotos() {
        ArrayList<Photos> resultList = new ArrayList<>();
        File files[] = INTERNAL_DIR.listFiles();
        for (File file:files){
            try {
                FileInputStream fos = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fos);
                Photos photo = (Photos) ois.readObject();
                resultList.add(photo);
            } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
        }
        return resultList;
    }

}

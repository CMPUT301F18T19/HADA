package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBOpenHelper;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.photoTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos;

/**
 * Manager class that provides an interface for interacting with SQLite database.
 * Used for saving, loading, deleting records from/to the database.
 *
 * @author Alex
 * @see Photos
 * @see DBcontract
 * @see DBOpenHelper
 */
public class DBPhotoManager {
    private SQLiteDatabase db;

    /**
     * Constructor that uses DBOpenHelper to open the database
     */
    public DBPhotoManager(Context context){
        db = new DBOpenHelper(context).getWritableDatabase();
    }

    /**
     * Adds a photo to the database
     * @param photo a Photos class obj to be added to the database
     */
    public void addPhoto(Photos photo) {
        if (existsPhoto(photo.getFileID()))
            return;
        ContentValues values = new ContentValues();
        values.put(photoTable.COL_PARENTID, photo.getParentId());
        values.put(photoTable.COL_FILEID, photo.getFileID());
        values.put(photoTable.COL_BITMAP, bitmaps2json(photo.getBitmaps()));
        db.insert(photoTable.TABLE_NAME, null, values);
        setPhotosSyncFlag(photo.getFileID(), false);
    }

    /**
     * Loads a photo from the database
     * @param  fileID a photo's fileID
     * @return a photo, a Photos class obj.
     * Null if the photo is not found
     */
    public Photos getPhoto(String fileID) {
        Photos photo = null;
        Cursor cursor = db.query(
                photoTable.TABLE_NAME,
                null,
                photoTable.COL_FILEID + " = ?",
                new String[] { fileID },
                null,
                null,
                null
        );
        if (cursor.getCount() > 0) {     // If there is results, else return null
            cursor.moveToFirst();        // get the first row, should be the only row
            photo = buildPhoto(cursor);
        }
        cursor.close();
        return photo;
    }

    /**
     * Delete a photo with given fileID from the database
     * @param  fileID a photo's fileID
     * @return number of rows deleted
     */
    public int deletePhoto(String fileID) {
        int numRowsDeleted = 0;
        if (!existsPhoto(fileID))
            return numRowsDeleted;
        numRowsDeleted = db.delete(
                photoTable.TABLE_NAME,
                photoTable.COL_FILEID + " = ?",
                new String[] { fileID }
        );
        return numRowsDeleted;
    }

    /**
     * check if a photo with fileID exists in the database
     * @param  fileID a photo's fileID
     * @return true if photo exists, false otherwise
     */
    public Boolean existsPhoto(String fileID) {
        Cursor c = db.query(
                photoTable.TABLE_NAME,
                null,
                photoTable.COL_FILEID + " = ?",
                new String[] { fileID },
                null,
                null,
                null
        );
        int count = c.getCount();
        c.close();
        return  (count > 0);
    }

    /**
     * check if a photo with fileID exists in the database
     * @param  bitmaps a photo's bitmap
     * @return true if photo exists, false otherwise
     */
    public Boolean existsPhoto(ArrayList<String> bitmaps) {
        Cursor c = db.query(
                photoTable.TABLE_NAME,
                null,
                photoTable.COL_BITMAP + " = ?",
                new String[] { bitmaps2json(bitmaps) },
                null,
                null,
                null
        );
        int count = c.getCount();
        c.close();
        return  (count > 0);
    }

    /**
     * set the sync column for a given photo to syncVal
     * @param fileID a photo's fileID
     * @param syncVal a boolean to set the sync column to
     */
    public void setPhotosSyncFlag(String fileID, Boolean syncVal) {
        ContentValues value = new ContentValues();
        if (syncVal)                                // syncVal == true
            value.put(photoTable.COL_SYNCED, 1);
        else                                        // syncVal == false
            value.put(photoTable.COL_SYNCED, 0);
        db.update(
                photoTable.TABLE_NAME,
                value,
                photoTable.COL_FILEID + " =?",
                new String[] { fileID }
        );
    }

    /**
     * get a list of photos in the database that are not synced to
     * the ES server
     * @return list of photos that are not synced to the ES server
     */
    public ArrayList<Photos> getUnSyncedPhotos() {
        ArrayList<Photos> resultList = new ArrayList<>();
        Cursor c = db.query(
                photoTable.TABLE_NAME,
                null,
                photoTable.COL_SYNCED + " = ?",
                new String[] { Integer.toString(0) },   // query rows with syncFlag of 0
                null,
                null,
                null
        );
        if (c.getCount() > 0) {         // if there is results, else return empty list
            while (c.moveToNext()) {
                resultList.add(buildPhoto(c));
            }
        }
        c.close();
        return resultList;
    }

    /**
     * given a cursor, build a photo using the row
     * the cursor is currently pointing to
     * @param  cursor a cursor object
     * @return a Photos object
     */
    private Photos buildPhoto(Cursor cursor) {
        Photos photo = new Photos();
        photo.setParentId(cursor.getString(
                cursor.getColumnIndexOrThrow(photoTable.COL_PARENTID))
        );
        photo.setFileID(cursor.getString(
                cursor.getColumnIndexOrThrow(photoTable.COL_FILEID))
        );
        photo.setBitmaps(json2Bitmaps(
                cursor.getString(cursor.getColumnIndexOrThrow(photoTable.COL_BITMAP))
        ));
        return photo;
    }

    /**
     * convert given bitmaps to json string
     * @param bitmaps
     * @return json a parsed json string
     */
    private String bitmaps2json(ArrayList<String> bitmaps) {
        return new Gson().toJson(bitmaps, new TypeToken<ArrayList<String>>() {}.getType());
    }

    /**
     * convert a json string representing bitmaps to bitmaps
     * @param json
     * @return bitmaps
     */
    private ArrayList<String> json2Bitmaps(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<String>>() {}.getType());
    }
}

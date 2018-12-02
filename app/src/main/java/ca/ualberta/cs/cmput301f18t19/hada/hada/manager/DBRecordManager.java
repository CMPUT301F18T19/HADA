package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBOpenHelper;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.recordTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.ContextSingleton;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

/**
 * Manager class that provides an interface for interacting with SQLite database.
 * Used for saving, loading, deleting records from/to the database.
 *
 * @author Alex
 * @see Record
 * @see DBcontract
 * @see DBOpenHelper
 */
public class DBRecordManager {
    private SQLiteDatabase db;
    // formatter used to parse LocalDateTime to/from string
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    /**
     * Constructor that uses DBOpenHelper to open the database
     */
    public DBRecordManager(Context context){
        db = new DBOpenHelper(context).getWritableDatabase();
    }

    /**
     * Adds a record to the record table in DB
     * @param newRecord record to be added to DB
     */
    public void addRecord(Record newRecord) {
        ContentValues values = new ContentValues();
        values.put(recordTable.COL_PARENTID, newRecord.getParentId());
        values.put(recordTable.COL_FILEID, newRecord.getFileId());
        values.put(recordTable.COL_TIMESTAMP, newRecord.getTitle());
        values.put(recordTable.COL_TITLE, newRecord.getTimestamp().toString());
        values.put(recordTable.COL_COMMENT, newRecord.getComment());
        values.put(recordTable.COL_PHOTOS, newRecord.getPhotos());
        values.put(recordTable.COL_LAT, newRecord.getLocation().latitude);
        values.put(recordTable.COL_LON, newRecord.getLocation().longitude);
        values.put(recordTable.COL_BODYLOCATION, newRecord.getBodyLocation());
        db.insert(recordTable.TABLE_NAME, null, values);
    }

    /**
     * Loads a record from the record table
     * @param  recordID record's fileID
     * @return record, null if the record is not found in the table
     */
    public Record getRecord(String recordID) {
        Record record = null;
        Cursor cursor = db.query(
                recordTable.TABLE_NAME,
                null,
                recordTable.COL_FILEID + " = ?",
                new String[] { recordID },
                null,
                null,
                null
        );
        if (cursor.getCount() > 0) {     // If there is results, else return null
            cursor.moveToFirst();        // get the first row, should be the only row
            record = buildRecord(cursor);
        }
        cursor.close();
        return record;
    }

    /**
     * Delete a record with given recordID from the record table
     * @param  recordID record's fileID
     * @return number of rows deleted
     */
    public int deleteRecord(String recordID) {
        int numRowsDeleted = 0;
        if (!existsRecord(recordID))
            return numRowsDeleted;
        numRowsDeleted = db.delete(
                recordTable.TABLE_NAME,
                recordTable.COL_FILEID + " = ?",
                new String[] { recordID }
        );
        return numRowsDeleted;
    }

    /**
     * Loads all records of a given problem from the record table
     * @param  problemID a problem's fileID
     * @return a list of records, empty list if no record is found
     */
    public ArrayList<Record> getRecordList(String problemID) {
        ArrayList<Record> recordList = new ArrayList<>();
        Cursor cursor = db.query(
                recordTable.TABLE_NAME,
                null,
                recordTable.COL_PARENTID + " = ?",
                new String[] { problemID },
                null,
                null,
                null
        );
        if (cursor.getCount() > 0) {         // if there is results, else return empty list
            while (cursor.moveToNext()) {
                recordList.add(buildRecord(cursor));
            }
        }
        cursor.close();
        return recordList;
    }

    /**
     * edit a record's title given the recordID of the record.
     * @param recordID a record's fileID, newTitle is the new title to be set
     * @return number of rows modified.
     */
    public int editRecordTitle(String recordID, String newTitle) {
        int rowsUpdated = 0;
        ContentValues values = new ContentValues();
        values.put(recordTable.COL_TITLE, newTitle);

        rowsUpdated = db.update(
                recordTable.TABLE_NAME,
                values,
                recordTable.COL_FILEID + " = ?",
                new String[] { recordID }
        );
        return rowsUpdated;
    }

    /**
     * edit a record's comment given the recordID of the record.
     * @param recordID a record's fileID, newComment is the new comment to be set
     * @return number of rows modified.
     */
    public int editRecordComment(String recordID, String newComment) {
        int rowsUpdated = 0;
        ContentValues values = new ContentValues();
        values.put(recordTable.COL_COMMENT, newComment);

        rowsUpdated = db.update(
                recordTable.TABLE_NAME,
                values,
                recordTable.COL_FILEID + " = ?",
                new String[] { recordID }
        );
        return rowsUpdated;
    }

    /**
     * edit a record's stored location given the recordID of the record.
     * @param recordID a record's fileID, newLocation the new location to be set
     * @return number of rows modified.
     */
    public int editRecordGeoLocation(String recordID, LatLng newLocation) {
        int rowsUpdated = 0;
        ContentValues values = new ContentValues();
        values.put(recordTable.COL_LAT, newLocation.latitude);
        values.put(recordTable.COL_LON, newLocation.longitude);

        rowsUpdated = db.update(
                recordTable.TABLE_NAME,
                values,
                recordTable.COL_FILEID + " = ?",
                new String[] { recordID }
        );
        return rowsUpdated;
    }

    /**
     * Given the problem's fileID, matches keyword in all records belonging to this
     * problem, keyword is matched in the title and comment section of the record.
     * A list of matching records is returned.
     * @param problemID a problem's fileID
     * @param keyword the keyword to be matched in the record title and comment.
     * @return .
     */
    public ArrayList<Record> searchRecordWtihKeyword(String problemID, String keyword) {
        ArrayList<Record> resultList = new ArrayList<>();
        String selection =
                recordTable.COL_PARENTID + " =? " +
                        "AND ((" + recordTable.COL_TITLE + " LIKE ?) " +
                        "OR (" + recordTable.COL_COMMENT + " LIKE ?)" +
                        ")";
        String[] selectionArgs = {
                problemID,
                "'%" + keyword + "%'",
                "'%" + keyword + "%'"
        };

        Cursor cursor = db.query(
                recordTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.getCount() > 0) {         // if there is results, else return empty list
            while (cursor.moveToNext()) {
                resultList.add(buildRecord(cursor));
            }
        }
        cursor.close();
        return resultList;

    }

    /**
     * Given the problem's fileID,
     * @param problemID a problem's fileID
     * @param distanceStr the radius of the proximity to be searched.
     *                    distance as a string, to follow the conventions used in controllers
     * @param targetLoc the center of the proximity to be searched
     * @return .
     */
    public ArrayList<Record> searchRecordWtihGeo(String problemID, String distanceStr, LatLng targetLoc) {
        ArrayList<Record> resultList = new ArrayList<>();
        Double distance = Double.parseDouble(distanceStr);

        // query all records with matching parentID
        Cursor cursor = db.query(
                recordTable.TABLE_NAME,
                null,
                recordTable.COL_PARENTID + " = ?",
                new String[] { problemID },
                null,
                null,
                null
        );

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                // get LatLng from this record (the record being walked)
                LatLng thisLoc = new LatLng(
                        cursor.getDouble(cursor.getColumnIndexOrThrow(recordTable.COL_LAT)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(recordTable.COL_LON))
                );

                // if this record's location is within the proximity
                // of the target location, build record and add it to result
                if (thisLoc.latitude < (targetLoc.latitude + distance)              // Eastbound
                        && thisLoc.latitude > (targetLoc.latitude - distance)       // Westbound
                        && thisLoc.longitude < (targetLoc.longitude + distance)     // Northbound
                        && thisLoc.longitude > (targetLoc.longitude - distance))    // Southbound
                    resultList.add(buildRecord(cursor));
            }
        }
        cursor.close();
        return resultList;
    }

    //TODO
    public int editRecordPhoto(){
        //TODO
        return -1;

    }

    //TODO
    public int editRecordBodyLocation() {
        //TODO
        return -1;

    }

    /**
     * check if a record with fileID exists in the database
     * @param  fileID a record's fileID
     * @return true if record exists, false otherwise
     */
    private Boolean existsRecord(String fileID) {
        Cursor c = db.query(
                recordTable.TABLE_NAME,
                null,
                recordTable.COL_FILEID + " = ?",
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
     * given a cursor, build a record using the row
     * the cursor is currently pointing to
     * @param  cursor a cursor object
     * @return a record object
     */
    private Record buildRecord(Cursor cursor) {
        Record record = new Record();
        record.setParentId(cursor.getString(cursor.getColumnIndexOrThrow(recordTable.COL_PARENTID)));
        record.setFileId(cursor.getString(cursor.getColumnIndexOrThrow(recordTable.COL_FILEID)));
        record.setTimestamp(
                LocalDateTime.parse(
                        cursor.getString(cursor.getColumnIndexOrThrow(recordTable.COL_TIMESTAMP)),
                        formatter)
        );
        record.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(recordTable.COL_TITLE)));
        record.setComment(cursor.getString(cursor.getColumnIndexOrThrow(recordTable.COL_COMMENT)));
        record.setPhotos(cursor.getString(cursor.getColumnIndexOrThrow(recordTable.COL_PHOTOS)));
        record.setLocation(
                new LatLng(
                        cursor.getDouble(cursor.getColumnIndex(recordTable.COL_LAT)),
                        cursor.getDouble(cursor.getColumnIndex(recordTable.COL_LON))
                )
        );
        record.setBodyLocation(cursor.getString(cursor.getColumnIndexOrThrow(recordTable.COL_BODYLOCATION)));
        return record;
    }

}

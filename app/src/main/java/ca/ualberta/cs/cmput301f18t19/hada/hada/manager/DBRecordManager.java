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
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

public class DBRecordManager {
    private SQLiteDatabase db;
    // formatter used to parse LocalDateTime to/from string
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    /**
     * Constructor that uses DBOpenHelper to open the database
     * @param context
     */
    public DBRecordManager(Context context){
        db = new DBOpenHelper(context).getWritableDatabase();
    }

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

    public Record getRecord(String recordID) {
        Record record = null;
        Cursor c = db.query(
                recordTable.TABLE_NAME,
                null,
                recordTable.COL_FILEID + " = ?",
                new String[] { recordID },
                null,
                null,
                null
        );
        if (c.getCount() > 0) {     // If there is results, else return null
            c.moveToFirst();        // get the first row, should be the only row

            record = new Record();
            record.setParentId(c.getString(c.getColumnIndexOrThrow(recordTable.COL_PARENTID)));
            record.setFileId(c.getString(c.getColumnIndexOrThrow(recordTable.COL_FILEID)));
            record.setTimestamp(
                    LocalDateTime.parse(
                            c.getString(c.getColumnIndexOrThrow(recordTable.COL_TIMESTAMP)),
                            formatter)
            );
            record.setTitle(c.getString(c.getColumnIndexOrThrow(recordTable.COL_TITLE)));
            record.setComment(c.getString(c.getColumnIndexOrThrow(recordTable.COL_COMMENT)));
            record.setPhotos(c.getString(c.getColumnIndexOrThrow(recordTable.COL_PHOTOS)));
            record.setLocation(
                    new LatLng(
                        c.getDouble(c.getColumnIndex(recordTable.COL_LAT)),
                        c.getDouble(c.getColumnIndex(recordTable.COL_LON))
                    )
            );
            record.setBodyLocation(c.getString(c.getColumnIndexOrThrow(recordTable.COL_BODYLOCATION)));
        }
        c.close();
        return record;
    }

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

    public ArrayList<Record> getRecordList(String problemID) {
        ArrayList<Record> recordList = new ArrayList<>();
        Cursor c = db.query(
                recordTable.TABLE_NAME,
                null,
                recordTable.COL_PARENTID + " = ?",
                new String[] { problemID },
                null,
                null,
                null
        );
        if (c.getCount() > 0) {         // if there is results, else return empty list
            while (c.moveToNext()) {
                Record record = new Record();
                record.setParentId(c.getString(c.getColumnIndexOrThrow(recordTable.COL_PARENTID)));
                record.setFileId(c.getString(c.getColumnIndexOrThrow(recordTable.COL_FILEID)));
                record.setTimestamp(
                        LocalDateTime.parse(
                                c.getString(c.getColumnIndexOrThrow(recordTable.COL_TIMESTAMP)),
                                formatter)
                );
                record.setTitle(c.getString(c.getColumnIndexOrThrow(recordTable.COL_TITLE)));
                record.setComment(c.getString(c.getColumnIndexOrThrow(recordTable.COL_COMMENT)));
                record.setPhotos(c.getString(c.getColumnIndexOrThrow(recordTable.COL_PHOTOS)));
                record.setLocation(
                        new LatLng(
                                c.getDouble(c.getColumnIndex(recordTable.COL_LAT)),
                                c.getDouble(c.getColumnIndex(recordTable.COL_LON))
                        )
                );
                record.setBodyLocation(c.getString(c.getColumnIndexOrThrow(recordTable.COL_BODYLOCATION)));
                recordList.add(record);
            }
        }
        c.close();
        return recordList;
    }

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

    public int editRecordGeoLocation(String recordID, LatLng location) {
        int rowsUpdated = 0;
        ContentValues values = new ContentValues();
        values.put(recordTable.COL_LAT, location.latitude);
        values.put(recordTable.COL_LON, location.longitude);

        rowsUpdated = db.update(
                recordTable.TABLE_NAME,
                values,
                recordTable.COL_FILEID + " = ?",
                new String[] { recordID }
        );
        return rowsUpdated;
    }

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

        Cursor c = db.query(
                recordTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (c.getCount() > 0) {         // if there is results, else return empty list
            while (c.moveToNext()) {
                Record record = new Record();
                record.setParentId(c.getString(c.getColumnIndexOrThrow(recordTable.COL_PARENTID)));
                record.setFileId(c.getString(c.getColumnIndexOrThrow(recordTable.COL_FILEID)));
                record.setTimestamp(
                        LocalDateTime.parse(
                                c.getString(c.getColumnIndexOrThrow(recordTable.COL_TIMESTAMP)),
                                formatter)
                );
                record.setTitle(c.getString(c.getColumnIndexOrThrow(recordTable.COL_TITLE)));
                record.setComment(c.getString(c.getColumnIndexOrThrow(recordTable.COL_COMMENT)));
                record.setPhotos(c.getString(c.getColumnIndexOrThrow(recordTable.COL_PHOTOS)));
                record.setLocation(
                        new LatLng(
                                c.getDouble(c.getColumnIndex(recordTable.COL_LAT)),
                                c.getDouble(c.getColumnIndex(recordTable.COL_LON))
                        )
                );
                record.setBodyLocation(c.getString(c.getColumnIndexOrThrow(recordTable.COL_BODYLOCATION)));
                resultList.add(record);
            }
        }
        c.close();
        return resultList;

    }

    public ArrayList<Record> searchRecordWtihGeo(String problemID, String distance, LatLng location) {
        ArrayList<Record> resultList = new ArrayList<>();
        Double dist = Double.parseDouble(distance);

        // TODO: query all records with problemID
        Cursor c = db.query(
                recordTable.TABLE_NAME,
                null,
                recordTable.COL_PARENTID + " = ?",
                new String[] { problemID },
                null,
                null,
                null
        );
        // TODO: get their latitude and longitude, if either one is +/- distance, add to resultList


        return resultList;
    }

    public int editRecordPhoto(){
        //TODO
        return -1;

    }

    public int editRecordBodyLocation() {
        //TODO
        return -1;

    }

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

}

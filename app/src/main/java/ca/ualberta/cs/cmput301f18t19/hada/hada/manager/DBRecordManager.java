package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

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
        //        private String parentId;
//        private String fileId;
//        private LocalDateTime timestamp;
//        private String title;
//        private String comment;
//        private String Photos;
//        private ArrayList<Double> location;
//        private String bodyLocation;

        Gson gson = new Gson();
        String parsedLocation = gson.toJson(newRecord.getLocationArrayList());

        ContentValues values = new ContentValues();
        values.put(DBcontract.recordTable.COL_PARENTID, newRecord.getParentId());
        values.put(DBcontract.recordTable.COL_FILEID, newRecord.getFileId());
        values.put(DBcontract.recordTable.COL_TIMESTAMP, newRecord.getTitle());
        values.put(DBcontract.recordTable.COL_TITLE, newRecord.getTimestamp().toString());
        values.put(DBcontract.recordTable.COL_COMMENT, newRecord.getComment());
        values.put(DBcontract.recordTable.COL_PHOTOS, newRecord.getPhotos());
        values.put(DBcontract.recordTable.COL_LOCATION, parsedLocation);
        values.put(DBcontract.recordTable.COL_BODYLOCATION, newRecord.getBodyLocation());
        db.insert(DBcontract.recordTable.TABLE_NAME, null, values);
    }

    public Record getRecord(String recordID) {
        //TODO
        return new Record();
    }

    public int deleteRecord(String recordID) {
        //TODO
        return -1;
    }

    public ArrayList<Record> getRecordList(String problemID) {
        //TODO
        return new ArrayList<Record>();
    }

    public int editRecordTitle(String recordID, String newTitle) {
        //TODO
        return -1;

    }

    public int editRecordComment(String recordID, String newComment) {
        //TODO
        return -1;

    }

    public int editRecordPhoto(){
        //TODO
        return -1;

    }

    public int editRecordBodyLocation() {
        //TODO
        return -1;

    }

    public int editRecordGeoLocation(String recordID, LatLng location) {
        //TODO
        return -1;

    }

    public ArrayList<Record> searchRecordWtihKeyword(String problemID, String keyword) {
        //TODO
        return new ArrayList<Record>();

    }

    public ArrayList<Record> searchRecordWtihGeo(String problemID, String distance, LatLng location) {
        //TODO
        return new ArrayList<Record>();
    }

}

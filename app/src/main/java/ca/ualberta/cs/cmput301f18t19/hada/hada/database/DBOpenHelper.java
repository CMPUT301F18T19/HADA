package ca.ualberta.cs.cmput301f18t19.hada.hada.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.careProviderTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.patientTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.problemTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.recordTable;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "hada.db";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create careProvider table
        db.execSQL(
                "CREATE TABLE " + careProviderTable.TABLE_NAME + " (" +
                        careProviderTable.COL_USERID + " TEXT," +
                        careProviderTable.COL_PHONE + " TEXT," +
                        careProviderTable.COL_EMAIL + " TEXT," +
                        "PRIMARY KEY (" + careProviderTable.COL_USERID + ")" +
                        ")"
        );

        // create patients table
        db.execSQL(
                "CREATE TABLE " + patientTable.TABLE_NAME + " (" +
                        patientTable.COL_PARENTID + " TEXT," +
                        patientTable.COL_USERID + " TEXT," +
                        patientTable.COL_PHONE + " TEXT," +
                        patientTable.COL_EMAIL + " TEXT," +
                "PRIMARY KEY (" + patientTable.COL_USERID + ")" +
                ")"
        );

        // create problems table
        db.execSQL(
                "CREATE TABLE " + problemTable.TABLE_NAME + " (" +
                        problemTable.COL_PARENTID + " TEXT," +
                        problemTable.COL_FILEID + " TEXT," +
                        problemTable.COL_TITLE + " TEXT," +
                        problemTable.COL_DATE + " TEXT," +
                        problemTable.COL_DESC + " TEXT," +
                        "PRIMARY KEY (" + problemTable.COL_FILEID + ")" +
                        ")"
        );

        // create records table
        db.execSQL(
                "CREATE TABLE " + recordTable.TABLE_NAME + " (" +
                        recordTable.COL_PARENTID + " TEXT," +
                        recordTable.COL_FILEID + " TEXT," +
                        recordTable.COL_TIMESTAMP + " TEXT," +
                        recordTable.COL_TITLE + " TEXT," +
                        recordTable.COL_COMMENT + " TEXT," +
                        recordTable.COL_URI_PHOTOS + " TEXT," +
                        recordTable.COL_HTTP_PHOTOS + " TEXT," +
                        recordTable.COL_GEOLOCATION + " TEXT," +
                        recordTable.COL_BODDLOC + " TEXT," +
                        "PRIMARY KEY (" + recordTable.COL_FILEID + ")" +
                        ")"
        );

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

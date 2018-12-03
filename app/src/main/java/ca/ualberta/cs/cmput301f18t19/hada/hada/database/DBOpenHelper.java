package ca.ualberta.cs.cmput301f18t19.hada.hada.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.careProviderTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.patientTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.problemTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.recordTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.DBUserManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.DBProblemManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.DBRecordManager;


/**
 * SQLiteOpenHelper class that opens up connection and check configuration of the database
 * each time it is opened. This class should not be instantiated by any class other than
 * the DB manager classes.
 *
 * @version 1
 * @author Alex
 * @see DBcontract
 * @see DBUserManager
 * @see DBProblemManager
 * @see DBRecordManager
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "hada.db";

    /**
     * constructor for a database open helper
     * @param context
     */
    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This method is called when database is first created
     * @param db SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create careProvider table
        db.execSQL(
                "CREATE TABLE " + careProviderTable.TABLE_NAME + " (" +
                        careProviderTable.COL_USERID + " TEXT," +
                        careProviderTable.COL_PHONE + " TEXT," +
                        careProviderTable.COL_EMAIL + " TEXT," +
                        careProviderTable.COL_SYNCED + " INTEGER," +
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
                        patientTable.COL_SHORTCODE + " TEXT," +
                        careProviderTable.COL_SYNCED + " INTEGER," +
                        "PRIMARY KEY (" + patientTable.COL_USERID + ")," +
                        "FOREIGN KEY (" + patientTable.COL_PARENTID + ") " +
                        "REFERENCES " + careProviderTable.TABLE_NAME +
                        "(" + careProviderTable.COL_USERID + ")" +
                        " ON DELETE CASCADE)"
        );

        // create problems table
        db.execSQL(
                "CREATE TABLE " + problemTable.TABLE_NAME + " (" +
                        problemTable.COL_PARENTID + " TEXT," +
                        problemTable.COL_FILEID + " TEXT," +
                        problemTable.COL_TITLE + " TEXT," +
                        problemTable.COL_DATE + " TEXT," +
                        problemTable.COL_DESC + " TEXT," +
                        careProviderTable.COL_SYNCED + " INTEGER," +
                        "PRIMARY KEY (" + problemTable.COL_FILEID + ")," +
                        "FOREIGN KEY (" + problemTable.COL_PARENTID + ") " +
                        "REFERENCES " + patientTable.TABLE_NAME +
                        "(" + patientTable.COL_USERID + ")" +
                        " ON DELETE CASCADE)"

        );

        // create records table
        db.execSQL(
                "CREATE TABLE " + recordTable.TABLE_NAME + " (" +
                        recordTable.COL_PARENTID + " TEXT," +
                        recordTable.COL_FILEID + " TEXT," +
                        recordTable.COL_TIMESTAMP + " TEXT," +
                        recordTable.COL_TITLE + " TEXT," +
                        recordTable.COL_COMMENT + " TEXT," +
                        recordTable.COL_PHOTOS + " TEXT," +
                        recordTable.COL_LAT + " REAL," +
                        recordTable.COL_LON + " REAL," +
                        recordTable.COL_BODYLOCATION + " TEXT," +
                        careProviderTable.COL_SYNCED + " INTEGER," +
                        "PRIMARY KEY (" + recordTable.COL_FILEID + ")," +
                        "FOREIGN KEY (" + recordTable.COL_PARENTID + ") " +
                        "REFERENCES " + problemTable.TABLE_NAME +
                        "(" + problemTable.COL_FILEID + ")" +
                        " ON DELETE CASCADE)"
        );

    }

    /**
     * This method is called when database connection is configured
     * @param db SQLiteDatabase
     */
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    /**
     * This method is called when database schemas needs to be updated,
     * can be used during application maintenance phase, currently not
     * needed.
     * @param db SQLiteDatabase
     * @param oldVersion int
     * @param newVersion int
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

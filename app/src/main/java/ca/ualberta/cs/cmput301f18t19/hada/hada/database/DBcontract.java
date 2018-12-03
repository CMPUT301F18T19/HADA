package ca.ualberta.cs.cmput301f18t19.hada.hada.database;

import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.DBUserManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.DBProblemManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.DBRecordManager;

/**
 * Contract class that defines the database schemas needed for the application,
 * this class should never be instantiated. It is only a definition of schemas.
 * The DB managers are the wrapper classes used to interact with the database
 *
 * @author Alex
 * @see DBOpenHelper
 * @see DBUserManager
 * @see DBProblemManager
 * @see DBRecordManager
 */
public class DBcontract {

    /**
     * constructor is set to private as this class is a definition of DB schemas,
     * it should never be instantiated
     */
    private DBcontract() {}

    /**
     * definition of careProvider table
     */
    public static final class careProviderTable {
        public static final String TABLE_NAME = "careProvider";

        public static final String COL_USERID = "userID";
        public static final String COL_PHONE = "phone";
        public static final String COL_EMAIL = "email";
        public static final String COL_SYNCED = "syncFlag";
    }

    /**
     * definition of patient table
     */
    public static final class patientTable {
        public static final String TABLE_NAME = "patient";

        public static final String COL_PARENTID = "parentID";
        public static final String COL_USERID = "userID";
        public static final String COL_PHONE = "phone";
        public static final String COL_EMAIL = "email";
        public static final String COL_SHORTCODE = "shortCode";
        public static final String COL_SYNCED = "syncFlag";
    }

    /**
     * definition of problem table
     */
    public static final class problemTable {
        public static final String TABLE_NAME = "problem";

        public static final String COL_PARENTID = "parentID";
        public static final String COL_FILEID = "fileID";
        public static final String COL_TITLE = "title";
        public static final String COL_DATE = "date";
        public static final String COL_DESC = "description";
        public static final String COL_SYNCED = "syncFlag";
    }

    /**
     * definition of record table
     */
    public static final class recordTable {
        public static final String TABLE_NAME = "record";

        public static final String COL_PARENTID = "parentID";
        public static final String COL_FILEID = "fileID";
        public static final String COL_TIMESTAMP = "timestamp";
        public static final String COL_TITLE = "title";
        public static final String COL_COMMENT = "comment";
        public static final String COL_PHOTOS = "photos";
        public static final String COL_LAT = "loc_latitude";
        public static final String COL_LON = "loc_longitude";
        public static final String COL_BODYLOCATION = "bodyLocation";
        public static final String COL_SYNCED = "syncFlag";
    }

}

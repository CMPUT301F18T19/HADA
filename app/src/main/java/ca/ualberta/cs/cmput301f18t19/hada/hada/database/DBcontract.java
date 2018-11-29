package ca.ualberta.cs.cmput301f18t19.hada.hada.database;


public class DBcontract {

    // constructor is set to private since this class defines DB schema,
    // it should never be instantiated
    private DBcontract() {}

    public static final class careProviderTable {
        public static final String TABLE_NAME = "careProvider";

        public static final String COL_USERID = "userID";
        public static final String COL_PHONE = "phone";
        public static final String COL_EMAIL = "email";
    }

    public static final class patientTable {
        public static final String TABLE_NAME = "patient";

        public static final String COL_PARENTID = "parentID";
        public static final String COL_USERID = "userID";
        public static final String COL_PHONE = "phone";
        public static final String COL_EMAIL = "email";
    }

    public static final class problemTable {
        public static final String TABLE_NAME = "problem";

        public static final String COL_PARENTID = "parentID";
        public static final String COL_FILEID = "fileID";
        public static final String COL_TITLE = "title";
        public static final String COL_DATE = "date";
        public static final String COL_DESC = "description";
    }

    public static final class recordTable {
        public static final String TABLE_NAME = "record";

        public static final String COL_PARENTID = "parentID";
        public static final String COL_FILEID = "fileID";
        public static final String COL_TIMESTAMP = "timestamp";
        public static final String COL_TITLE = "title";
        public static final String COL_COMMENT = "comment";
        public static final String COL_URI_PHOTOS = "uriPhotos";
        public static final String COL_HTTP_PHOTOS = "httpPhotos";
        public static final String COL_GEOLOCATION = "geoLocation";
        public static final String COL_BODDLOC = "bodyLocation";
    }

}

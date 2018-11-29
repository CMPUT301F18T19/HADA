package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.patientTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBOpenHelper;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;

public class DBUserManager {
    private Context context;
    private SQLiteDatabase db;

    public DBUserManager(Context context){
        db = new DBOpenHelper(context).getWritableDatabase();
    }

    public void addPatient(Patient patient) {
        if (exists(patient))
            return;

        ContentValues values = new ContentValues();
        values.put(patientTable.COL_PARENTID, patient.getParentId());
        values.put(patientTable.COL_USERID, patient.getUserID());
        values.put(patientTable.COL_PHONE, patient.getPhoneNumber());
        values.put(patientTable.COL_EMAIL, patient.getEmailAddress());
        db.insert(patientTable.TABLE_NAME, null, values);
    }

    public Patient getPatient(String userID) {
        String selection = patientTable.COL_USERID + " = ?";
        String[] selectionArgs = { userID };

        Cursor c = db.query(
                patientTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (c.getCount() <= 0) {
            // patient is not found
            c.close();
            return null;
        }
        else {
            c.moveToFirst();
            Patient patient = new Patient(
                    userID,
                    c.getString(c.getColumnIndexOrThrow(patientTable.COL_PHONE)),
                    c.getString(c.getColumnIndexOrThrow(patientTable.COL_EMAIL))
            );
            c.close();
            return patient;
        }
    }

    public void deletePatient(String userID) {


    }

    private Boolean exists(Patient patient) {
        String selection = patientTable.COL_USERID + " = ?";
        String[] selectionArgs = { patient.getUserID() };

        Cursor c = db.query(
                patientTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        Integer count = c.getCount();
        c.close();
        return  (count > 0);

    }
}

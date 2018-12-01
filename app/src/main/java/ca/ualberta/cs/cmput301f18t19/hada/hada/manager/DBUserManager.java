package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.careProviderTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.patientTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBOpenHelper;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;

public class DBUserManager {
    private Context context;
    private SQLiteDatabase db;

    public DBUserManager(Context context){
        db = new DBOpenHelper(context).getWritableDatabase();
    }

    public void addPatient(Patient patient) {
        if (existsPatient(patient.getUserID()))
            return;

        ContentValues values = new ContentValues();
        values.put(patientTable.COL_PARENTID, patient.getParentId());
        values.put(patientTable.COL_USERID, patient.getUserID());
        values.put(patientTable.COL_PHONE, patient.getPhoneNumber());
        values.put(patientTable.COL_EMAIL, patient.getEmailAddress());
        db.insert(patientTable.TABLE_NAME, null, values);
    }

    public int deletePatient(String userID) {
        int numRowsDeleted = 0;
        if (!existsPatient(userID))
            return numRowsDeleted;

        String whereClause = patientTable.COL_USERID + " = ?";
        String[] whereArgs = { userID };
        numRowsDeleted = db.delete(patientTable.TABLE_NAME, whereClause, whereArgs);
        return numRowsDeleted;

    }

    public Patient getPatient(String userID) {
        Patient patient = null;
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
        if (c.getCount() > 0) {     // If there is results, else return null
            c.moveToFirst();        // get the first row, should be the only row
            patient = new Patient(
                    userID,
                    c.getString(c.getColumnIndexOrThrow(patientTable.COL_PHONE)),
                    c.getString(c.getColumnIndexOrThrow(patientTable.COL_EMAIL))
            );
        }
        c.close();
        return patient;
    }

    public ArrayList<Patient> getPatientList(String careProviderID) {
        ArrayList<Patient> patientList = new ArrayList<>();

        String selection = patientTable.COL_PARENTID + " = ?";
        String[] selectionArgs = { careProviderID };
        Cursor c = db.query(
                patientTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (c.getCount() > 0) {         // if there is results, else return empty list
            while (c.moveToNext()) {
                patientList.add(
                    new Patient(
                    c.getString(c.getColumnIndexOrThrow(patientTable.COL_USERID)),
                    c.getString(c.getColumnIndexOrThrow(patientTable.COL_PHONE)),
                    c.getString(c.getColumnIndexOrThrow(patientTable.COL_EMAIL))
                ));
            }
        }
        c.close();
        return patientList;
    }

    public void addCareProvider(CareProvider careProvider) {
        if (existsCP(careProvider.getUserID()))
            return;

        ContentValues values = new ContentValues();
        values.put(careProviderTable.COL_USERID, careProvider.getUserID());
        values.put(careProviderTable.COL_PHONE, careProvider.getPhoneNumber());
        values.put(careProviderTable.COL_EMAIL, careProvider.getEmailAddress());
        db.insert(careProviderTable.TABLE_NAME, null, values);
    }

    public int deleteCareProvider(String userID) {
        int numRowsDeleted = 0;
        if (!existsCP(userID))
            return numRowsDeleted;

        String selection = careProviderTable.COL_USERID + " = ?";
        String[] selectionArgs = { userID };
        numRowsDeleted = db.delete(careProviderTable.TABLE_NAME, selection, selectionArgs);
        return numRowsDeleted;
    }

    public CareProvider getCareProvider(String userID) {
        CareProvider careProvider = null;
        String selection = careProviderTable.COL_USERID + " = ?";
        String[] selectionArgs = { userID };
        Cursor c = db.query(
                careProviderTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (c.getCount() > 0) {     // If there is results, else return null
            c.moveToFirst();        // get the first row, should be the only row
            careProvider = new CareProvider(
                    userID,
                    c.getString(c.getColumnIndexOrThrow(patientTable.COL_PHONE)),
                    c.getString(c.getColumnIndexOrThrow(patientTable.COL_EMAIL))
            );
        }
        c.close();
        return careProvider;
    }

    public Boolean userExists(String userID) {
        return (!existsCP(userID) && !existsPatient(userID));
    }

    public void editPatientParentID(String patientID, String parentID) {
        ContentValues values = new ContentValues();
        values.put(patientTable.COL_PARENTID, parentID);

        String whereClause = patientTable.COL_USERID + " = ?";
        String[] whereArgs = { patientID };
        db.update(patientTable.TABLE_NAME, values, whereClause, whereArgs);

    }

    public void editPatientPhone(String patientID, String newNumber) {
        ContentValues values = new ContentValues();
        values.put(patientTable.COL_PHONE, newNumber);

        String whereClause = patientTable.COL_USERID + " = ?";
        String[] whereArgs = { patientID };
        db.update(patientTable.TABLE_NAME, values, whereClause, whereArgs);

    }

    public void editPatientEmail(String patientID, String newEmail) {
        ContentValues values = new ContentValues();
        values.put(patientTable.COL_EMAIL, newEmail);

        String whereClause = patientTable.COL_USERID + " = ?";
        String[] whereArgs = { patientID };
        db.update(patientTable.TABLE_NAME, values, whereClause, whereArgs);

    }


    public void editCareProviderPhone(String careProviderID, String newNumber) {
        ContentValues values = new ContentValues();
        values.put(careProviderTable.COL_PHONE, newNumber);

        String whereClause = careProviderTable.COL_USERID + " = ?";
        String[] whereArgs = { careProviderID };
        db.update(careProviderTable.TABLE_NAME, values, whereClause, whereArgs);

    }

    public void editCareProviderEmail(String careProviderID, String newEmail) {
        ContentValues values = new ContentValues();
        values.put(careProviderTable.COL_EMAIL, newEmail);

        String whereClause = careProviderTable.COL_USERID + " = ?";
        String[] whereArgs = { careProviderID };
        db.update(careProviderTable.TABLE_NAME, values, whereClause, whereArgs);

    }

    private Boolean existsPatient(String userID) {
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
        int count = c.getCount();
        c.close();
        return  (count > 0);
    }

    private Boolean existsCP(String userID) {
        String selection = careProviderTable.COL_USERID + " = ?";
        String[] selectionArgs = { userID };

        Cursor c = db.query(
                careProviderTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        int count = c.getCount();
        c.close();
        return  (count > 0);
    }
}

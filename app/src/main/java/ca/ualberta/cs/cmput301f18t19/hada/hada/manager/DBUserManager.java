/* CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 2018-10-29
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.careProviderTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.patientTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBOpenHelper;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.User;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.CareProvider;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Patient;

/**
 * Manager class that provides an interface for interacting with SQLite database.
 * Used for saving, loading, deleting patients and careProviders from/to the database.
 *
 * @author Alex
 * @see Patient
 * @see CareProvider
 * @see User
 * @see DBcontract
 * @see DBOpenHelper
 */
public class DBUserManager {
    private Context context;
    private SQLiteDatabase db;

    /**
     * Constructor that uses DBOpenHelper to open the database
     * @param context
     */
    public DBUserManager(Context context){
        db = new DBOpenHelper(context).getWritableDatabase();
    }

    /**
     * Adds a patient to the patientTable
     * @param patient, patient to be added
     */
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

    /**
     * deletes a patient from the patientTable if it exists, all problems and records
     * belonging to this patient is also deleted
     * @param userID, patient's userID
     * @return number of patients deleted
     */
    public int deletePatient(String userID) {
        int numRowsDeleted = 0;
        if (!existsPatient(userID))
            return numRowsDeleted;

        numRowsDeleted = db.delete(
                patientTable.TABLE_NAME,
                patientTable.COL_USERID + " = ?",
                new String[] { userID }
        );
        return numRowsDeleted;
    }

    /**
     * Loads a patient from the patientTable
     * @param  userID, patient's userID
     * @return patient, null if the patient is not found in the table
     */
    public Patient getPatient(String userID) {
        Patient patient = null;
        Cursor c = db.query(
                patientTable.TABLE_NAME,
                null,
                patientTable.COL_USERID + " = ?",
                new String[] { userID },
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

    /**
     * Loads a list patients for the given careProvider from the patientTable
     * @param  careProviderID
     * @return ArrayList<Patient>, empty list if no patient is found in the table
     */
    public ArrayList<Patient> getPatientList(String careProviderID) {
        ArrayList<Patient> patientList = new ArrayList<>();
        Cursor c = db.query(
                patientTable.TABLE_NAME,
                null,
                patientTable.COL_PARENTID + " = ?",
                new String[] { careProviderID },
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

    /**
     * Adds a careProvider to the careProviderTable
     * @param  careProvider, careProvider to be added
     */
    public void addCareProvider(CareProvider careProvider) {
        if (existsCP(careProvider.getUserID()))
            return;

        ContentValues values = new ContentValues();
        values.put(careProviderTable.COL_USERID, careProvider.getUserID());
        values.put(careProviderTable.COL_PHONE, careProvider.getPhoneNumber());
        values.put(careProviderTable.COL_EMAIL, careProvider.getEmailAddress());
        db.insert(careProviderTable.TABLE_NAME, null, values);
    }

    /**
     * Deletes a CareProvider from the careProviderTable
     * the careProvider's patients, their problems and records are also removed
     * @param  userID, careProvider's userID
     * @return patient, null if the patient is not found in the table
     */
    public int deleteCareProvider(String userID) {
        int numRowsDeleted = 0;
        if (!existsCP(userID))
            return numRowsDeleted;
        numRowsDeleted = db.delete(
                careProviderTable.TABLE_NAME,
                careProviderTable.COL_USERID + " = ?",
                new String[] { userID }
        );
        return numRowsDeleted;
    }

    /**
     * loads a CareProvider from the careProviderTable given its userID
     * @param  userID, careProvider's userID
     * @return careProvider, null if the careProvider is not found
     */
    public CareProvider getCareProvider(String userID) {
        CareProvider careProvider = null;
        Cursor c = db.query(
                careProviderTable.TABLE_NAME,
                null,
                careProviderTable.COL_USERID + " = ?",
                new String[] { userID },
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

    /**
     * edit a given patient's parentID(careProviderID)
     * @param  patientID patient's userID, parentID new parentID to be set
     * @return numbers of rows updated
     */
    public int editPatientParentID(String patientID, String parentID) {
        int rowsUpdated = 0;
        ContentValues values = new ContentValues();
        values.put(patientTable.COL_PARENTID, parentID);
        rowsUpdated = db.update(
                patientTable.TABLE_NAME,
                values,
                patientTable.COL_USERID + " = ?",
                new String[] { patientID }
        );
        return rowsUpdated;
    }

    /**
     * edit a given patient's phone number
     * @param  patientID patient's userID, newNumber new phone number to be set
     * @return numbers of rows updated
     */
    public int editPatientPhone(String patientID, String newNumber) {
        int rowsUpdated = 0;
        ContentValues values = new ContentValues();
        values.put(patientTable.COL_PHONE, newNumber);
        rowsUpdated = db.update(
                patientTable.TABLE_NAME,
                values,
                patientTable.COL_USERID + " = ?",
                new String[] { patientID }
        );
        return rowsUpdated;
    }

    /**
     * edit a given patient's email address
     * @param  patientID patient's userID, newEmail new email address to be set
     * @return numbers of rows updated
     */
    public int editPatientEmail(String patientID, String newEmail) {
        int rowsUpdated = 0;
        ContentValues values = new ContentValues();
        values.put(patientTable.COL_EMAIL, newEmail);
        rowsUpdated = db.update(
                patientTable.TABLE_NAME,
                values,
                patientTable.COL_USERID + " = ?",
                new String[] { patientID }
        );
        return rowsUpdated;
    }


    /**
     * edit a given careProvider's phone number
     * @param  careProviderID careProvider's userID, newEmail new phone number to be set
     * @return numbers of rows updated
     */
    public int editCareProviderPhone(String careProviderID, String newNumber) {
        int rowsUpdated = 0;
        ContentValues values = new ContentValues();
        values.put(careProviderTable.COL_PHONE, newNumber);
        rowsUpdated = db.update(
                careProviderTable.TABLE_NAME,
                values,
                careProviderTable.COL_USERID + " = ?",
                new String[] { careProviderID }
        );
        return rowsUpdated;

    }

    /**
     * edit a given careProvider's email address
     * @param  careProviderID careProvider's userID, newEmail new email address to be set
     * @return numbers of rows updated
     */
    public int editCareProviderEmail(String careProviderID, String newEmail) {
        int rowsUpdated = 0;
        ContentValues values = new ContentValues();
        values.put(careProviderTable.COL_EMAIL, newEmail);
        rowsUpdated = db.update(
                careProviderTable.TABLE_NAME,
                values,
                careProviderTable.COL_USERID + " = ?",
                new String[] { careProviderID }
        );
        return rowsUpdated;

    }

    /**
     * check if a user with userID exists in the database
     * @param  userID
     * @return true if user exists, false otherwise
     */
    public Boolean userExists(String userID) {
        return (!existsCP(userID) && !existsPatient(userID));
    }


    /**
     * check if a patient with userID exists in the database
     * @param  userID a patient's userID
     * @return true if patient exists, false otherwise
     */
    private Boolean existsPatient(String userID) {
        Cursor c = db.query(
                patientTable.TABLE_NAME,
                null,
                patientTable.COL_USERID + " = ?",
                new String[] { userID },
                null,
                null,
                null
        );
        int count = c.getCount();
        c.close();
        return  (count > 0);
    }

    /**
     * check if a careProvider with userID exists in the database
     * @param  userID a careProvider's userID
     * @return true if careProvider exists, false otherwise
     */
    private Boolean existsCP(String userID) {
        Cursor c = db.query(
                careProviderTable.TABLE_NAME,
                null,
                careProviderTable.COL_USERID + " = ?",
                new String[] { userID },
                null,
                null,
                null
        );
        int count = c.getCount();
        c.close();
        return  (count > 0);
    }
}

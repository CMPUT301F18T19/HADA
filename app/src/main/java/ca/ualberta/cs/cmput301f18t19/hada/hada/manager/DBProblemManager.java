package ca.ualberta.cs.cmput301f18t19.hada.hada.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBOpenHelper;
import ca.ualberta.cs.cmput301f18t19.hada.hada.database.DBcontract.problemTable;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;

/**
 * Manager class that provides an interface for interacting with SQLite database.
 * Used for saving, loading, deleting problems from/to the database.
 *
 * @author Alex
 * @see Problem
 * @see DBcontract
 * @see DBOpenHelper
 */
public class DBProblemManager {
    private SQLiteDatabase db;
    // formatter used to parse LocalDateTime to/from string
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    /**
     * Constructor that uses DBOpenHelper to open the database
     */
    public DBProblemManager(Context context){
        db = new DBOpenHelper(context).getWritableDatabase();
    }

    /**
     * Adds a problem to the problemTable
     * @param problem, problem to be added
     */
    public void addProblem(Problem problem) {
        if (existsProblem(problem.getFileId()))
            return;
        ContentValues values = new ContentValues();
        values.put(problemTable.COL_PARENTID, problem.getParentId());
        values.put(problemTable.COL_FILEID, problem.getFileId());
        values.put(problemTable.COL_TITLE, problem.getTitle());
        values.put(problemTable.COL_DATE, problem.getDate().toString());
        values.put(problemTable.COL_DESC, problem.getDesc());
        db.insert(problemTable.TABLE_NAME, null, values);
        setProblemSyncFlag(problem.getFileId(), false);
    }

    /**
     * Loads a problem from the problemTable
     * @param  fileID, problem's fileID
     * @return problem, null if the problem is not found in the table
     */
    public Problem getProblem(String fileID) {
        Problem problem = null;
        Cursor c = db.query(
                problemTable.TABLE_NAME,
                null,
                problemTable.COL_FILEID + " = ?",
                new String[] { fileID },
                null,
                null,
                null
        );
        if (c.getCount() > 0) {     // If there is results, else return null
            c.moveToFirst();        // get the first row, should be the only row
            problem = buildProblem(c);
        }
        c.close();
        return problem;
    }

    /**
     * Loads all problems of a given patient from the problemTable
     * @param  patientID, a patient's userID
     * @return problemList, empty list if no problem is found
     */
    public ArrayList<Problem> getProblemList(String patientID) {
        ArrayList<Problem> problemList = new ArrayList<>();
        Cursor c = db.query(
                problemTable.TABLE_NAME,
                null,
                problemTable.COL_PARENTID + " = ?",
                new String[] { patientID },
                null,
                null,
                null
        );
        if (c.getCount() > 0) {         // if there is results, else return empty list
            while (c.moveToNext()) {
                problemList.add(buildProblem(c));
            }
        }
        c.close();
        return problemList;
    }

    /**
     * Delete a problem with given fileID from the problemTable
     * @param  fileID, problem's fileID
     * @return number of rows deleted
     */
    public int deleteProblem(String fileID) {
        int numRowsDeleted = 0;
        if (!existsProblem(fileID))
            return numRowsDeleted;
        numRowsDeleted = db.delete(
                problemTable.TABLE_NAME,
                problemTable.COL_FILEID + " = ?",
                new String[] { fileID }
        );
        return numRowsDeleted;
    }

    /**
     * edit a problem's stored title given the fileID of the problem.
     * @param fileID a problem's fileID
     * @param  newTitle the new title to be set
     * @return number of rows modified.
     */
    public int editProblemTitle(String fileID, String newTitle) {
        int rowsUpdated = 0;
        ContentValues values = new ContentValues();
        values.put(problemTable.COL_TITLE, newTitle);

        rowsUpdated = db.update(
                problemTable.TABLE_NAME,
                values,
                problemTable.COL_FILEID + " = ?",
                new String[] { fileID }
        );
        setProblemSyncFlag(fileID, false);
        return rowsUpdated;
    }

    /**
     * edit a problem's stored date given the fileID of the problem.
     * @param fileID a problem's fileID
     * @param newDate a LocalDateTime obj of the new date
     * @return number of rows modified.
     */
    public int editProblemDate(String fileID, LocalDateTime newDate) {
        int rowsUpdated = 0;
        ContentValues values = new ContentValues();
        values.put(problemTable.COL_DATE, newDate.toString());

        rowsUpdated = db.update(
                problemTable.TABLE_NAME,
                values,
                problemTable.COL_FILEID + " = ?",
                new String[] { fileID }
        );
        setProblemSyncFlag(fileID, false);
        return rowsUpdated;
    }

    /**
     * edit a problem's stored description given the fileID of the problem.
     * @param fileID a problem's fileID
     * @param newDesc the new description to be set
     * @return number of rows modified.
     */
    public int editProblemDesc(String fileID, String newDesc) {
        int rowsUpdated = 0;
        ContentValues values = new ContentValues();
        values.put(problemTable.COL_DESC, newDesc);

        rowsUpdated = db.update(
                problemTable.TABLE_NAME,
                values,
                problemTable.COL_FILEID + " = ?",
                new String[] { fileID }
        );
        setProblemSyncFlag(fileID, false);
        return rowsUpdated;
    }

    /**
     * Given the patient's userID, matches keyword in all problems belonging to this
     * patient, keyword is matched in the title and description section. A list of
     * matching problems is returned.
     * NOTE: the method DOES NOT match keyword in each problem's records.
     * @param patientID a patient's userID
     * @param keyword the keyword to be matched in the problem title and description.
     * @return .
     */
    public ArrayList<Problem> searchProblemsWithKeyword(String patientID, String keyword) {
        ArrayList<Problem> resultList = new ArrayList<>();
        String selection =
                problemTable.COL_PARENTID + " =? " +
                "AND ((" + problemTable.COL_TITLE + " LIKE ?) " +
                "OR (" + problemTable.COL_DESC + " LIKE ?)" +
                ")";
        String[] selectionArgs = {
                patientID,
                "'%" + keyword + "%'",
                "'%" + keyword + "%'"
        };

        Cursor c = db.query(
                problemTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (c.getCount() > 0) {         // if there is results, else return empty list
            while (c.moveToNext()) {
                resultList.add(buildProblem(c));
            }
        }
        c.close();
        return resultList;
    }

    /**
     * check if a problem with fileID exists in the database
     * @param  fileID a problem's fileID
     * @return true if problem exists, false otherwise
     */
    private Boolean existsProblem(String fileID) {
        Cursor c = db.query(
                problemTable.TABLE_NAME,
                null,
                problemTable.COL_FILEID + " = ?",
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
     * given a cursor, build a problem using the row
     * the cursor is currently pointing to
     * @param  cursor a cursor object
     * @return a problem object
     */
    private Problem buildProblem(Cursor cursor) {
        Problem problem = new Problem(
                cursor.getString(cursor.getColumnIndexOrThrow(problemTable.COL_TITLE)),
                LocalDateTime.parse(
                        cursor.getString(cursor.getColumnIndexOrThrow(problemTable.COL_DATE)),
                        formatter),
                cursor.getString(cursor.getColumnIndexOrThrow(problemTable.COL_DESC))
        );
        problem.setParentId(cursor.getString(cursor.getColumnIndexOrThrow(problemTable.COL_PARENTID)));
        problem.setFileId(cursor.getString(cursor.getColumnIndexOrThrow(problemTable.COL_FILEID)));
        return problem;
    }

    /**
     * set the sync column for a given problem to syncVal
     * @param  problemID a problem's fileID
     * @param syncVal a boolean to set the sync column to
     */
    public void setProblemSyncFlag(String problemID, Boolean syncVal) {
        ContentValues value = new ContentValues();
        if (syncVal)                                // syncVal == true
            value.put(problemTable.COL_SYNCED, 1);
        else                                        // syncVal == false
            value.put(problemTable.COL_SYNCED, 0);
        db.update(
                problemTable.TABLE_NAME,
                value,
                problemTable.COL_FILEID + " =?",
                new String[] { problemID }
        );
    }

    /**
     * get a list of problems in the database that are not synced to
     * the ES server
     * @return list of problems that are not synced to the ES server
     */
    public ArrayList<Problem> getUnSyncedProblems() {
        ArrayList<Problem> resultList = new ArrayList<>();
        Cursor c = db.query(
                problemTable.TABLE_NAME,
                null,
                problemTable.COL_SYNCED + " = ?",
                new String[] { Integer.toString(0) },   // query rows with syncFlag of 0
                null,
                null,
                null
        );
        if (c.getCount() > 0) {         // if there is results, else return empty list
            while (c.moveToNext()) {
                resultList.add(buildProblem(c));
            }
        }
        c.close();
        return resultList;
    }
}

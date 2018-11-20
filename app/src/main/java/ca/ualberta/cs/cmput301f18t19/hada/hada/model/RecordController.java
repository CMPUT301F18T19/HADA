package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.utility.Listener;

/**
 * Controller for a given list of records.
 */
public class RecordController {

    /**
     * The list of records.
     */
    protected ArrayList<Record> recordList;
    /**
     * The list of listeners -- may be removed
     */
    protected ArrayList<Listener> listeners;

    /**
     * Instantiates a new Record controller with an empty list of records.
     */
    public RecordController(){
        this.recordList = new ArrayList<>();
    }

    /**
     * Returns a boolean for whether the list is empty.
     *
     * @return the boolean
     */
    public boolean isEmpty() {
        return this.recordList.isEmpty();
    }

    /**
     * Adds a record to the list.
     *
     * @param record the record
     */
    public void addRecord(Record record) {
        this.recordList.add(record);
    }

    /**
     * Returns a record from the list given an index.
     *
     * @param index the index
     * @return the record
     */
    public Record getRecord(int index) {
        return this.recordList.get(index);
    }

    /**
     * Returns the current size of the list.
     *
     * @return the size
     */
    public int getSize() {
        return this.recordList.size();
    }

    /**
     * Returns a boolean for whether a record is in the list.
     *
     * @param record the record
     * @return the boolean
     */
    public boolean recordInList(Record record) {
        return this.recordList.contains(record);
    }

    /**
     * Returns position for the given record in the list.
     *
     * @param record the record
     * @return the position
     */
    public int getPosition(Record record) {
        return this.recordList.indexOf(record);
    }

    /**
     * Deletes a given record from the list.
     *
     * @param record the record
     */
    public void deleteRecord(Record record) {
        this.recordList.remove(record);
    }

    /**
     * Inserts a record into the list.
     *
     * @param index  the index
     * @param record the record
     */
    public void insertRecord(int index, Record record) {
        this.recordList.add(index, record);
    }

    /**
     * Notify listeners. -- may be removed
     */
    public void notifyListeners(){
        for (Listener listener : listeners){
            if(listener != null){
                listener.update();}
        }
    }

    /**
     * Add listener.-- may be removed
     *
     * @param l the listenr
     */
    public void addListener(Listener l){
        listeners.add(l);
    }

    /**
     * Remove listener.-- may be removed
     *
     * @param l the listener
     */
    public void removeListener(Listener l){
        listeners.remove(l);
    }

    public ArrayList<Record> getRecordList(int index) {
        ArrayList<Problem> problems = new ProblemController().getProblemList(LoggedInSingleton.getInstance().getLoggedInID());
        Problem problem = problems.get(index);
        recordList = problem.getRecords();

        return recordList;
    }
}

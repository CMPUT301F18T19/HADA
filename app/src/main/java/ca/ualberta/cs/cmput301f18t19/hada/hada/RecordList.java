package ca.ualberta.cs.cmput301f18t19.hada.hada;

import java.util.ArrayList;

public class RecordList {

    private ArrayList<Record> recordList;

    RecordList(){
       this.recordList = new ArrayList<Record>();
    }

    public boolean isEmpty() {
        return this.recordList.isEmpty();
    }

    public void addRecord(Record record) {
        this.recordList.add(record);
    }

    public Record getRecord(int index) {
        return this.recordList.get(index);
    }

    public int getSize() {
        return this.recordList.size();
    }

    public boolean recordInList(Record record) {
        return this.recordList.contains(record);
    }

    public int getPosition(Record record) {
        return this.recordList.indexOf(record);
    }

    public void deleteRecord(Record record) {
        this.recordList.remove(record);
    }

    public void insertRecord(int index, Record record) {
        this.recordList.add(index, record);
    }
}

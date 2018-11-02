package ca.ualberta.cs.cmput301f18t19.hada.hada;

import java.util.ArrayList;

public class RecordListController {

    protected ArrayList<Record> recordList;
    protected ArrayList<Listener> listeners;

    RecordListController(){
        this.recordList = new ArrayList<>();
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

    public void notifyListeners(){
        for (Listener listener : listeners){
            if(listener != null){
                listener.update();}
        }
    }
    public void addListener(Listener l){
        listeners.add(l);
    }
    public void removeListener(Listener l){
        listeners.remove(l);
    }
}

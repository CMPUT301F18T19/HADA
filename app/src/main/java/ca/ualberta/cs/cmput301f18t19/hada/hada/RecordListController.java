package ca.ualberta.cs.cmput301f18t19.hada.hada;

public class RecordListController {


    private RecordList recordList = null;

    public RecordList getRecordList(Problem problem){
        recordList = problem.getRecords();
        return recordList;
    }

    public void addRecordEntry(Record record){
        recordList.addRecord(record);
        recordList.notifyListeners();
    }
    public void deleteRecordEntry(Record record){
        recordList.deleteRecord(record);
    }


}

package ca.ualberta.cs.cmput301f18t19.hada.hada;

import org.junit.Test;

import static org.junit.Assert.*;

public class RecordListTest {

    @Test
    public void testIsEmpty(){
        RecordList recordList = new RecordList();
        assertTrue(recordList.isEmpty());
    }

    @Test
    public void testAddRecord(){
        RecordList recordList = new RecordList();
        Record record = new Record();
        assertTrue(recordList.isEmpty());
        recordList.addRecord(record);
        assertFalse(recordList.isEmpty());
    }

    @Test
    public void testGetRecord(){
        RecordList recordList = new RecordList();
        Record record = new Record();
        assertTrue(recordList.isEmpty());
        recordList.addRecord(record);
        assertFalse(recordList.isEmpty());
        Record returnedRecord = recordList.getRecord(0);
        assertEquals(record, returnedRecord);

    }

    @Test
    public void testGetSize(){
        RecordList recordList = new RecordList();
        Record record = new Record();
        Record record1 = new Record();
        assertTrue(recordList.isEmpty());
        recordList.addRecord(record);
        assertEquals(1,recordList.getSize());
        recordList.addRecord(record1);
        assertEquals(2, recordList.getSize());
    }

    @Test
    public void testRecordInList(){
        RecordList recordList = new RecordList();
        Record record = new Record();
        Record record1 = new Record();
        Record record2 = new Record();

        assertTrue(recordList.isEmpty());

        recordList.addRecord(record);
        assertTrue(recordList.recordInList(record));

        recordList.addRecord(record1);
        assertTrue(recordList.recordInList(record1));

        assertFalse(recordList.recordInList(record2));
    }

    @Test
    public void testGetPosition(){
        RecordList recordList = new RecordList();
        Record record = new Record();
        Record record1 = new Record();
        recordList.addRecord(record);
        recordList.addRecord(record1);

        assertEquals(0, recordList.getPosition(record));
        assertEquals(1, recordList.getPosition(record1));

    }

    @Test
    public void testDeleteRecord(){
        RecordList recordList = new RecordList();
        Record record = new Record();
        Record record1 = new Record();
        recordList.addRecord(record);
        recordList.addRecord(record1);

        assertEquals(0, recordList.getPosition(record));
        assertEquals(1, recordList.getPosition(record1));

        recordList.deleteRecord(record);

        assertFalse(recordList.recordInList(record));
        assertEquals(0, recordList.getPosition(record1));
    }

    @Test
    public void testInsertRecord(){
        RecordList recordList = new RecordList();
        Record record = new Record();
        Record record1 = new Record();
        Record record2 = new Record();

        assertTrue(recordList.isEmpty());
        recordList.addRecord(record);
        recordList.addRecord(record1);
        assertFalse(recordList.isEmpty());

        assertEquals(1,recordList.getPosition(record1));

        recordList.insertRecord(1, record2);
        assertTrue(recordList.recordInList(record2));
        assertEquals(2,recordList.getPosition(record1));
        assertEquals(1, recordList.getPosition(record2));
    }

}
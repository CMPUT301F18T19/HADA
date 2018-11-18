package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProblemTest{

    @Test
    public void testSetTitle(){
        Problem problem = new Problem();
        problem.setTitle("Red mark on arm");
        assertEquals("Red mark on arm", problem.getTitle());
    }

    @Test
    public void testGetTitle(){
        Problem problem = new Problem();
        problem.setTitle("This is a test title");
        String returnedTitle =  problem.getTitle();
        assertEquals("This is a test title", returnedTitle);
    }

    @Test
    public void testSetDateStarted(){
        Problem problem = new Problem();
        LocalDateTime date = LocalDateTime.now();
        problem.setDate(date);
        assertEquals(date,problem.getDate());
    }

    @Test
    public void testGetDateStarted(){
        Problem problem = new Problem();
        LocalDateTime date = LocalDateTime.now();
        problem.setDate(date);
        LocalDateTime returnedDate = problem.getDate();
        assertEquals(date,returnedDate);
    }

    @Test
    public void testSetDesc(){
        Problem problem = new Problem();
        problem.setDesc("This is a test desc for this problem");
        assertEquals("This is a test desc for this problem", problem.getDesc());
    }

    @Test
    public void testGetDesc(){
        Problem problem = new Problem();
        problem.setDesc("This is a second test desc for this problem");
        String returnedDesc = problem.getDesc();
        assertEquals("This is a second test desc for this problem", returnedDesc);
    }

    @Test
    public void setRecordsTest(){
        Problem problem = new Problem();
        ArrayList<Record> records = new ArrayList();
        problem.setRecords(records);
        assertEquals("Should be same list",records, problem.getRecords());
    }

    @Test
    public void getRecordsTest(){
        Problem problem = new Problem();
        ArrayList<Record> records = new ArrayList<Record>();
        problem.setRecords(records);
        assertEquals("Should be same list",records, problem.getRecords());
    }


}


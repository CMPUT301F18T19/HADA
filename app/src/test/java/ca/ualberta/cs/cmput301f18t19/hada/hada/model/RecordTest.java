/*
 *  CMPUT 301 - Fall 2018
 *
 *  RecordTest.java
 *
 *  11/27/18 3:46 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

/**
 * Tests for the class Problem.
 *
 * @author Christopher Penner
 * @see Record
 */
public class RecordTest{

    private LatLng location = new LatLng(-1490.7240306939025, -3452.4085972491475);

    /**
     * Setup creates a pre defined Record to test getters
     */
    @NonNull
    private Record setup() {
        Record record = new Record(LocalDateTime.of(1975,
                Month.JANUARY, 31, 23, 59,59));
        record.setTitle("DefaultTitle");
        record.setComment("DefaultComment");
        record.setBodyLocation("test_uuid");
        String photoURL = "www.photo.URL";
        record.setPhotos(photoURL);
        record.setLocation(this.location);
        return record;
    }

    /**
     * Set timestamp test.
     */
    @Test
    public void setTimestampTest(){
        Record record = setup();
        LocalDateTime timestamp = LocalDateTime.now();
        record.setTimestamp(timestamp);
        assertEquals(record.getTimestamp(), timestamp);

    }

    /**
     * Set title test.
     */
    @Test
    public void TestsetTitle(){
        Record record = setup();
        record.setTitle("NewRecordTitle");
        assertEquals(record.getTitle(), "NewRecordTitle");

    }

    /**
     * Set comment test.
     */
    @Test
    public void TestSetComment(){
        Record record = setup();
        String comment = "This is the new comment!";
        record.setComment(comment);
        assertEquals("The comment should be the same", comment, record.getComment());
    }

    /**
     * Add photo test.
     */
    @Test
    public void TestAddPhoto(){
        Record record = setup();
        String photo1 = "10101";
        record.setPhotos(photo1);
        assertEquals("Photo id should be first in list", photo1, record.getPhotos());
        String photo2 = "10102";
        record.setPhotos(photo2);
        assertEquals("Photo id added should be second in list", photo2, record.getPhotos());
    }

    /**
     * Get geo location test.
     */
    @Test
    public void TestGetLocation(){
        Record record = setup();
        assertNotNull(record.getLocation());
        assertEquals(this.location, record.getLocation());
    }

    /**
     * Set geo location test.
     */
    @Test
    public void TestSetLocation(){
        Record record = setup();
        LatLng location  = new LatLng(-1527.7943684329543,-3489.459182813189);
        record.setLocation(location);
        assertEquals("Geolocation should be what was set", location, record.getLocation());
    }


    /**
     * Get body location test.
     */
    @Test
    public void TestGetBodyLocation(){
        Record record = setup();
        assertEquals("test_uuid", record.getBodyLocation());
    }

    /**
     * Set body location.
     */
    @Test
    public void TestSetBodyLocation(){
        Record record = setup();
        record.setBodyLocation("uuid goes here");
        assertEquals("uuid goes here", record.getBodyLocation());
    }

    /**
     * Get timestamp test.
     */
    @Test
    public void TestGetTimestamp(){
        Record record = setup();
        assertNotNull("Timestamp should not be null", record.getTimestamp());
        //Constructor with specific timestamp test
        LocalDateTime timestamp = LocalDateTime.of(1975,
                Month.JANUARY, 31, 23, 59,59);
        assertEquals(timestamp, record.getTimestamp());

    }

    /**
     * Get title test.
     */
    @Test
    public void TestGetTitle(){
        Record record = setup();
        assertEquals("DefaultTitle", record.getTitle());
    }

    /**
     * Get comment test.
     */
    @Test
    public void TestGetComment(){
        Record record = setup();
        assertEquals("DefaultComment", record.getComment());
    }

    /**
     * Get photos test.
     */
    @Test
    public void TestGetPhotos(){
        Record record = setup();
        assertEquals("www.photo.URL", record.getPhotos());
    }

}

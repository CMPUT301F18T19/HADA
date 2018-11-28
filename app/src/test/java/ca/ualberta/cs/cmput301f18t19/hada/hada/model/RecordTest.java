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

    /**
     * Setup creates a pre defined Record to test getters
     */
    @NonNull
    private Record setup() {
        Record record = new Record(LocalDateTime.of(1975,
                Month.JANUARY, 31, 23, 59,59));
        record.setTitle("DefaultTitle");
        record.setComment("DefaultComment");
        record.setBodyLocation(999,999);
        record.addPhoto("www.photo.URL");
        record.setGeoLocation(new Location("GeoTest"));
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
    public void TestsetComment(){
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
        record.addPhoto(photo1);
        assertEquals("Photo id should be first in list", photo1, record.getPhotos().get(1));
        String photo2 = "10102";
        record.addPhoto(photo2);
        assertEquals("Photo id added should be second in list", photo2, record.getPhotos().get(2));
    }

    /**
     * Remove photo test.
     */
    @Test
    public void TestRemovePhoto(){
        Record record = setup();
        String exception= "Not thrown";
        record.removePhoto("www.photo.URL");
        assertTrue("Photo1 should no longer be in list" , record.getPhotos().size() == 0);
    }

    /**
     * Set geo location test.
     */
    @Test
    public void TestSetGeoLocation(){
        Record record = setup();
        Location location  = new Location("test");
        record.setGeoLocation(location);
        assertEquals("Geolocation should be what was set", location, record.getGeoLocation());
    }

    /**
     * Set body location.
     */
    @Test
    public void TestSetBodyLocation(){
        Record record = setup();
        int bodyX = 0;
        int bodyY = 1;
        record.setBodyLocation(bodyX, bodyY);
        assertTrue("X coord should be set", bodyX == record.getBodyLocation().get(0));
        assertTrue("y coord should be set", bodyY == record.getBodyLocation().get(1));
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
        assertTrue(record.getPhotos().size() == 1);
        String photo = "www.photo2.URL";
        record.addPhoto(photo);
        assertEquals(record.getPhotos().get(1), photo);
        record.removePhoto("www.photo.URL");
        assertEquals(record.getPhotos().get(0), photo);
    }

    /**
     * Get geo location test.
     */
    @Test
    public void TestGetGeoLocation(){
        Record record = setup();
        assertNotNull(record.getGeoLocation());
        Location location = new Location("GeoTest");
        assertEquals(location, record.getGeoLocation());

    }

    /**
     * Get body location test.
     */
    @Test
    public void TestGetBodyLocation(){
        Record record = setup();
        long x = record.getBodyLocation().get(0);
        long y = record.getBodyLocation().get(1);
        assertEquals(999, x);
        assertEquals(999, y);

    }
}

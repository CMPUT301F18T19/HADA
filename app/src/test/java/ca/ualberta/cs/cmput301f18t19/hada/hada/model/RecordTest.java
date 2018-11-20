package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.location.Location;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * RecordTest
 *
 * Version 1.0
 *
 * October 25th 2018
 *
 *
 */
public class RecordTest{

    @Test
    public void setTimestampTest(){
        Record record = new Record();
        LocalDateTime timestamp = LocalDateTime.now();
        record.setTimestamp(timestamp);
        assertEquals("Timestamp returned should be same timestamp", record.getTimestamp(), timestamp);

    }

    @Test
    public void setTitleTest(){
        Record record = new Record();
        String title = "Title";
        record.setTitle(title);
        assertEquals("Titles should be equal", record.getTitle(), title);

    }

    @Test
    public void setCommentTest(){
        Record record = new Record();
        String comment = "This is the new comment!";
        record.setComment(comment);
        assertEquals("The comment should be the same", comment, record.getComment());
    }

    @Test
    public void addPhotoTest(){
        Record record = new Record();
        String photo1 = "10101";
        record.addPhoto(photo1);
        assertEquals("Photo id should be first in list", photo1, record.getPhotos().get(0));
        String photo2 = "10102";
        record.addPhoto(photo2);
        assertEquals("Photo id added should be second in list", photo2, record.getPhotos().get(1));
    }

    @Test
    public void removePhotoTest(){
        Record record = new Record();
        String photo1 = "10101";
        String photo2 = "10102";
        String exception = "Not thrown";
        try{
            record.removePhoto(photo1);
        }catch(IllegalStateException e){
            exception = "thrown";
        }
        assertTrue("Exception should have been thrown", exception.equals("thrown"));

        record.addPhoto(photo1);
        record.addPhoto(photo2);
        record.removePhoto(photo1);
        assertTrue("Photo1 should no longer be in list" , record.getPhotos().size() == 1);
        assertTrue("Photo1 should no longer be in the list", record.getPhotos().get(0) == photo2);

    }

    @Test
    public void setGeoLocationTest(){
        Record record = new Record();
        Location location  = new Location("test");
        record.setGeoLocation(location);
        assertEquals("Geolocation should be what was set", location, record.getGeoLocation());
    }

    @Test
    public void setBodyLocation(){
        Record record = new Record();
        int bodyX = 0;
        int bodyY = 1;
        record.setBodyLocation(bodyX, bodyY);
        assertTrue("X coord should be set", bodyX == record.getBodyLocation().get(0));
        assertTrue("y coord should be set", bodyY == record.getBodyLocation().get(1));
    }
    @Test
    public void getTimestampTest(){

        //Default constructor test
        Record record = new Record();
        assertNotNull("Timestamp should not be null", record.getTimestamp());

        //Constructor with specific timestamp test
        LocalDateTime timestamp = LocalDateTime.parse("2007-12-03T10:15:30");
        Record record2 = new Record(timestamp);
        assertEquals("Should be the same date as we just set", timestamp, record2.getTimestamp());

    }

    @Test
    public void getTitleTest(){
        Record record = new Record();
        assertNull("Title should be null", record.getTitle());
        String title = "Title";
        record.setTitle(title);
        assertEquals("Titles should be equal", title, record.getTitle());
    }

    @Test
    public void getCommentTest(){
        Record record = new Record();
        assertNull("Comment should be null", record.getComment());
        String comment = "test";
        record.setComment(comment);
        assertEquals("The comment should be test", comment, record.getComment());
    }

    @Test
    public void getPhotosTest(){
        Record record = new Record();
        assertNull("photos list should be null", record.getPhotos());
        String photo1 = "10101";
        record.addPhoto(photo1);
        assertTrue("Size of list should be 1", record.getPhotos().size() == 1);
        String photo2 = "10102";
        record.addPhoto(photo2);
        assertTrue("photo2 should be at end of list", record.getPhotos().get(1) == photo2);
        record.removePhoto(photo1);
        assertTrue("Photo2 should now be at front of list", record.getPhotos().get(0) == photo2);
    }

    @Test
    public void getGeoLocationTest(){
        Record record = new Record();
        assertNull("geolocation should be null",record.getGeoLocation());
        Location location  = new Location("test");
        record.setGeoLocation(location);
        assertEquals("Location should be what we set it to", location, record.getGeoLocation());

    }

    @Test
    public void getBodyLocationTest(){
        Record record = new Record();
        assertNull("body location should be null",record.getBodyLocation());
        int bodyX = 1;
        int bodyY = 9;
        record.setBodyLocation(bodyX, bodyY);
        assertTrue("X coord should be what it was set to", bodyX == record.getBodyLocation().get(0));
        assertTrue("y coord should be what it was set to", bodyY == record.getBodyLocation().get(1));

    }
}

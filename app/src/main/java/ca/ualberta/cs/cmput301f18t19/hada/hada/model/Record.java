package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.location.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Object which represents a record of a tracked problem for a given patient.
 *
 * @author Anders
 * @see Problem
 * @see RecordController
 * @see Patient
 * @see CareProvider
 */
public class Record{

    private LocalDateTime timestamp;
    private String title;
    private String comment;
    private ArrayList<String> photos; //A Base64 encoded String for a photo
    private Location geoLocation;
    private ArrayList<Integer> bodyLocation;


    /**
     * Instantiates a new Record object with the current time as a timestamp.
     */
    public Record() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Instantiates a new Record with a specific timestamp.
     *
     * @param timestamp the timestamp
     */
    public Record(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Sets timestamp of a record.
     *
     * @param timestamp the timestamp
     */
//setters
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Sets title of a record.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets comment of a record.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Add photo to a record. Photo in this instance is a Base64 encoded string.
     *
     * @param photo the photo
     */
    public void addPhoto(String photo) {
        if (this.photos == null) {
            photos = new ArrayList<String>();
        }
        this.photos.add(photo);
    }

    /**
     * Removes photo from list of photos.
     *
     * @param photo the photo
     */
    public void removePhoto(String photo) {
        if (this.photos == null) {
            throw new IllegalStateException();
        }
        if (this.photos.contains(photo)) {
            this.photos.remove(photo);
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Sets geo location of the record, given a Location object (supplied by Android).
     *
     * @param location the location
     */
    public void setGeoLocation(Location location) {
        this.geoLocation = location;
    }

    /**
     * Set body location, represented by x and y coordinates.
     *
     * @param x the x
     * @param y the y
     */
    public void setBodyLocation(int x, int y){
        this.bodyLocation = new ArrayList<Integer>();
        this.bodyLocation.add(x);
        this.bodyLocation.add(y);
    }

    /**
     * Returns the timestamp of the record
     *
     * @return the timestamp
     */
//getters
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Returns the title of the record.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the comment of the record.
     *
     * @return the comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Returns the list of photo strings.
     *
     * @return the photos
     */
    public ArrayList<String> getPhotos() {
        return this.photos;
    }

    /**
     * Returns the Location object associated with the record.
     *
     * @return the geo location
     */
    public Location getGeoLocation() {
        return this.geoLocation;
    }

    /**
     * Returns the x, y coords of the body location.
     *
     * @return the body location
     */
    public ArrayList<Integer> getBodyLocation() {
        return this.bodyLocation;
    }


    /**
     * Overrides the default toString() for this object
     *
     * @author Joe Potentier
     * @return A print friendly string
     */
    @Override
    public String toString(){
        return this.getTitle();
    }

}

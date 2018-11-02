package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.location.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The type Record.
 */
public class Record{

    private LocalDateTime timestamp;
    private String title;
    private String comment;
    private ArrayList<String> photos; //A Base64 encoded String for a photo
    private Location geoLocation;
    private ArrayList<Integer> bodyLocation;


    Record() {
        this.timestamp = LocalDateTime.now();
    }

    Record(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    //setters
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void addPhoto(String photo) {
        if (this.photos == null) {
            photos = new ArrayList<String>();
        }
        this.photos.add(photo);
    }

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

    public void setGeoLocation(Location location) {
        this.geoLocation = location;
    }

    public void setBodyLocation(int x, int y){
        this.bodyLocation = new ArrayList<Integer>();
        this.bodyLocation.add(x);
        this.bodyLocation.add(y);
    }

    //getters
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public String getTitle() {
        return this.title;
    }

    public String getComment() {
        return this.comment;
    }

    public ArrayList<String> getPhotos() {
        return this.photos;
    }

    public Location getGeoLocation() {
        return this.geoLocation;
    }

    public ArrayList<Integer> getBodyLocation() {
        return this.bodyLocation;
    }
}

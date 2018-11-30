package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.location.Location;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDateTime;
import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Object which represents a record of a tracked problem for a given patient.
 *
 * @author Anders
 * @see Problem
 * @see ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController
 * @see Patient
 * @see CareProvider
 * @version 2.0
 */
public class Record{

    /**
     * parentId is the id of it's parent problem
     * fileId is the id of the record on ES
     */
    private String parentId;
    @JestId
    private String fileId;

    private LocalDateTime timestamp;
    private String title;
    private String comment;
    private ArrayList<Uri> uriPhotos; //A Base64 encoded String for a photo
    private ArrayList<String> httpPhotos;
    private LatLng locationLatLng;
    //private ArrayList<Double> location;
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


    //setters

    /**
     * Sets the id of the parent object
     *
     * @param parentId the fileId of parent object
     */
    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    /**
     * Sets the id of the parent object
     *
     * @param fileId that is used for storing object
     */
    public void setFileId(String fileId){
        this.fileId = fileId;
    }

    /**
     * Sets timestamp of a record.
     *
     * @param timestamp the timestamp
     */
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
    public void addPhoto(Uri photo,String url) {
        if (this.uriPhotos == null) {
            uriPhotos = new ArrayList<>();
        }
        if (this.httpPhotos == null) {
            httpPhotos = new ArrayList<>();
        }
        this.uriPhotos.add(photo);
        this.httpPhotos.add(url);
    }

    /**
     * Removes photo from list of photos.
     *
     * @param photo the photo
     */
    public void removePhoto(Uri photo,String url) {
        if (this.uriPhotos == null) {
            throw new IllegalStateException();
        }
        if (this.httpPhotos == null) {
            throw new IllegalStateException();
        }
        if (this.uriPhotos.contains(photo) && this.httpPhotos.contains(url)) {
            this.uriPhotos.remove(photo);
            this.httpPhotos.remove(url);
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Sets geo location of the record, given a Location object (supplied by Android).
     *
     * @param latLng the location
     */
    public void setLocation(LatLng latLng) {

        this.locationLatLng = latLng;
        //this.location = new ArrayList<>();
        //this.location.add(this.locationLatLng.longitude);
        //this.location.add(this.locationLatLng.latitude);
    }

    /**
     * Set body location, represented by x and y coordinates.
     *
     * @param x the x
     * @param y the y
     */
    public void setBodyLocation(int x, int y){
        this.bodyLocation = new ArrayList<>();
        this.bodyLocation.add(x);
        this.bodyLocation.add(y);
    }



    //getters

    /**
     * Returns the parentId of the record
     *
     * @return the parentId
     */
    public String getParentId(){
        return this.parentId;
    }

    /**
     * Returns the parentId of the record
     *
     * @return the fileId
     */
    public String getFileId(){
        return this.fileId;
    }

    /**
     * Returns the timestamp of the record
     *
             * @return the timestamp
     */
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
    public ArrayList<Uri> getUriPhotos() {
        if (this.uriPhotos == null) {
            this.uriPhotos = new ArrayList<Uri>();
        }
        return this.uriPhotos;
    }

    /**
     * Returns the Location object associated with the record.
     *
     * @return the geo location
     */
    public LatLng getLocation() {

        return this.locationLatLng;
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

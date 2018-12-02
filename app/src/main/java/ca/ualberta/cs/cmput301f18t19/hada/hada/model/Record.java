package ca.ualberta.cs.cmput301f18t19.hada.hada.model;

import android.location.Location;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private String Photos;
    private ArrayList<Double> location;

    /**
     * Instantiates a new Record object with the current time as a timestamp.
     */
    public Record() {
        this.timestamp = LocalDateTime.now();
    }

    public String getPhotos() {
        return Photos;
    }

    public void setPhotos(String photos) {
        Photos = photos;
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
     * Sets geo location of the record, given a Location object (supplied by Android).
     *
     * @param latLng the location
     */
    public void setLocation(LatLng latLng) {
        location = new ArrayList<>();
        this.location.add(latLng.longitude);
        this.location.add(latLng.latitude);
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
     * Returns the Location object associated with the record.
     *
     * @return the geo location
     */
    public LatLng getLocation() {
        return new LatLng(location.get(1), location.get(0));
    }

    public ArrayList<Double> getLocationArrayList() { return this.location;}


    /**
     * Overrides the default toString() for this object
     *
     * @author Joe Potentier
     * @return A print friendly string
     */
    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String niceDate = this.timestamp.format(formatter);
        if(!this.title.isEmpty()){
            return this.title + "  |  " + niceDate;
        }
        return niceDate;
    }
}

package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.net.Uri;
import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

public class PhotoController {
    private Record record;
    public PhotoController(){}

    public ArrayList<Uri> getPhotos(Record record){
    ArrayList<Uri> uriList = new ArrayList<Uri>();
    uriList = record.getUriPhotos();
    return uriList;
    }

    public void addPhoto(Record record, Uri uri){
        //TODO upload image to imgur
        if (uri == null){
            return;
        }
        record.addPhoto(uri, "HTTP GO HERE");
    }
}

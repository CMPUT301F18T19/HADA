package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.net.Uri;
import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

public class PhotoController {
    private Record record;
    public PhotoController(){}

    public ArrayList<Uri> getPhotos(Record record){
        ArrayList<String> uriStringList;
        uriStringList = record.getUriPhotos();
        ArrayList<Uri> uriList = new ArrayList<Uri>();
        for (String uri: uriStringList){
            uriList.add(Uri.parse(uri));
        }
        return uriList;
    }

    public void addPhoto(Record record, Uri uri){
        //TODO upload image to imgur
        if (uri == null){
            return;
        }
        record.addPhoto(uri.toString(), "HTTP GO HERE");
    }
}

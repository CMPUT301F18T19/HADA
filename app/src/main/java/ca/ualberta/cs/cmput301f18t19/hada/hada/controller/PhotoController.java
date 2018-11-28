package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.net.Uri;
import android.util.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

public class PhotoController {
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

    public Record addPhoto(Record record, Uri uri){
        //TODO upload image to imgur
        if (uri == null){
            return record;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime timestamp = record.getTimestamp();
        Log.d("AddRecord", "New Record: title=" + record.getTitle()+ " timestamp=" +timestamp.format(formatter));
        record.addPhoto(uri.toString(), "HTTP GO HERE");
        return record;
    }
}

package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
        String http = uploadImage(uri);
        record.addPhoto(uri.toString(), http);
        return record;
    }
    // adapted from https://stackoverflow.com/questions/7111751/uploading-a-photo-via-imgur-on-android-programatically hrickards
    public String uploadImage(Uri uri){
        String imageUrl = null;
        Bitmap bitmap = BitmapFactory.decodeFile(uri.toString());

// Creates Byte Array from picture
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); // Not sure whether this should be jpeg or png, try both and see which works best

// opens connection and sends data
        try{
            URL url = new URL("http://api.imgur.com/3/image");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(Base64.encode(baos.toByteArray(), Base64.DEFAULT).toString(), "UTF-8");
            data += "&" + URLEncoder.encode("Authorization: Client-ID ", "UTF-8") + "=" + URLEncoder.encode("8d52b0b7c0cd5f1", "UTF-8");
            Log.d("PC",data);
            wr.write(data);
            wr.flush();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            conn.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                if (inputLine.contains("link")){
                    imageUrl = inputLine;
                    return imageUrl;
                }
            in.close();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return imageUrl;

}

}

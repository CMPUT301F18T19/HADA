package ca.ualberta.cs.cmput301f18t19.hada.hada.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.spec.ECField;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.cs.cmput301f18t19.hada.hada.BuildConfig;
import ca.ualberta.cs.cmput301f18t19.hada.hada.manager.ESPhotoManager;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Photos;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;

public class PhotoController {
    public PhotoController(){}

    public ArrayList<String> getPhotos(String photosID){
        ArrayList<String> uriList = new ArrayList<String>();
        try {
            Photos photo = new ESPhotoManager.GetPhotoTask().execute(photosID).get();
            uriList = photo.getUriPhotos();
        }catch (Exception e){
            Log.d("getPhoto","couldnt get photos");
            e.printStackTrace();
        }
        if (uriList.size() != 0){
            for(String photo : uriList){
                File file = new File(URI.create(photo).getPath());
                if (!(file.exists())) {
                    int index = uriList.indexOf(photo);
                    //TODO download not found photos

                }

            }
        }
        return uriList;
    }

    public Record addPhoto(Record record, Uri uri){
        //TODO upload image to imgur
        if (uri == null){
            return record;
        }
        Photos photo = new Photos();
        ArrayList<String> uriList = new ArrayList<String>();
        ArrayList<String> httpList = new ArrayList<String>();
        String http = "None";
        if (!(record.getPhotos() == null)) {
            String photosId = record.getPhotos();
            try {
                photo = new ESPhotoManager.GetPhotoTask().execute(photosId).get();
                uriList = photo.getUriPhotos();
                httpList = photo.getHttpPhotos();
                new ESPhotoManager.DeletePhotosTask().execute(photosId);
            } catch (Exception e) {
                Log.d("addPhoto", "Couldn't retrieve record list from ES");
                e.printStackTrace();
            }
        }
        uriList.add(uri.toString());
        //http = uploadImage(uri); //TODO uncomment this out when uploadImage works
        httpList.add(http);
        photo.setHttpPhotos(httpList);
        photo.setUriPhotos(uriList);
        photo.setParentId(record.getFileId());
        photo.setFileID(UUID.randomUUID().toString());
        record.setPhotos(photo.getFileID());
        new ESPhotoManager.AddPhotosTask().execute(photo);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime timestamp = record.getTimestamp();
        Log.d("AddRecord", "New Record: title=" + record.getTitle()+ " timestamp=" +timestamp.format(formatter));
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
            data += "&" + URLEncoder.encode("Authorization: Client-ID ", "UTF-8") + "=" + URLEncoder.encode(BuildConfig.ImgurClientId, "UTF-8");
            Log.d("PhotoController",data);
            wr.write(data);
            wr.flush();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            conn.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                Log.d("PhotoController",inputLine);
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

/*
 *  CMPUT 301 - Fall 2018
 *
 *  CustomPhotoAdapter.java
 *
 *  30/11/18 3:56 PM
 *
 *  This is a group project for CMPUT 301 course at the University of Alberta
 *  Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 *  Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;

public class CustomPhotoAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<Uri> imgs;

    public CustomPhotoAdapter(Context ctx){
        this.ctx = ctx;

    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return false;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_swipe_images,container,false);
        ImageView imageView = v.findViewById(R.id.swipe_images_image);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }

}

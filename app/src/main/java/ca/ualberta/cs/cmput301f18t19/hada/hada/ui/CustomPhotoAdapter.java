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

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.BitSet;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;

/**
 * Adapter used for ViewSlideshowActivity to view the slideshow of the record images for an individual problem
 *
 * @author Austin
 *
 */
class CustomPhotoAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<Bitmap> bitmaps;

    public CustomPhotoAdapter(Context context, ArrayList<Bitmap> bitmaps) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bitmaps = bitmaps;
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.activity_swipe_image, container, false);

        ImageView imageView = itemView.findViewById(R.id.swipeImageActivityImage);
        imageView.setImageBitmap(bitmaps.get(position));

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}






/*
public class CustomPhotoAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<Bitmap> bitmaps;
    Activity activity;
    View view;

    public CustomPhotoAdapter(Context ctx, ArrayList<Bitmap> bitmaps){
        this.bitmaps = bitmaps;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        this.view = view;
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.activity_swipe_image, container, false);

        ImageView image;
        image = itemView.findViewById(R.id.swipeImageActivityImage);
        DisplayMetrics dis = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
        image.setMinimumHeight(dis.heightPixels);
        image.setMinimumWidth(dis.widthPixels);

        try {
            Picasso.with(activity.getApplicationContext())
                    .load(bitmaps.get(position))
                    .into(image);
        }

/*
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_swipe_image,container,false);
        ImageView imageView = v.findViewById(R.id.swipeImageActivityImage);
        imageView.setImageBitmap(bitmaps.get(position));
        container.addView(v);
        return v;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }

}
*/

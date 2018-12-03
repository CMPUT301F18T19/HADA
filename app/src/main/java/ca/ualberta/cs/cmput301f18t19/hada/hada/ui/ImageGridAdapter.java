package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Adapter used to create a grid of images for the record being viewed
 *
 * @author Austin, Joe
 *
 */

public class ImageGridAdapter extends BaseAdapter {

    private Context ctx;
    private ArrayList<Bitmap> bitmaps;

    public ImageGridAdapter(Context c, ArrayList<Bitmap> bitmaps) {
        ctx = c;
        this.bitmaps = bitmaps;
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(ctx);
            imageView.setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            Log.v("1", "In if statement");

        } else {
            imageView = (ImageView) convertView;
        }


        imageView.setImageBitmap(bitmaps.get(position));
        Log.v("2", "Just assigned imageview");
        return imageView;

    }
}

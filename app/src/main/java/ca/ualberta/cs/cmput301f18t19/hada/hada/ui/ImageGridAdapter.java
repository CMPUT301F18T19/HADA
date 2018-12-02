package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageGridAdapter extends BaseAdapter {

    private Context ctx;
    private ArrayList<Bitmap> imgs;

    public ImageGridAdapter(Context c, ArrayList<Bitmap> imgs) {
        this.ctx = c;
        this.imgs = imgs;

    }

    @Override
    public int getCount() {
        return 0;
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
            imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(imgs.get(position));
        return imageView;

    }
}

package ca.ualberta.cs.cmput301f18t19.hada.hada.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ca.ualberta.cs.cmput301f18t19.hada.hada.R;

public class ViewSlideshow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slideshow);

        ViewPager viewPager = findViewById(R.id.viewSlideshowActivityViewPager);

    }
}

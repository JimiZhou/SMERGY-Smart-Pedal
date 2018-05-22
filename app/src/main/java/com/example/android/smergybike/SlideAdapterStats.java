package com.example.android.smergybike;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Gebruiker on 22/05/2018.
 */

public class SlideAdapterStats extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    public SlideAdapterStats(Context context) {
        this.context = context;
    }

    @Override
    public float getPageWidth(int position) {
        return (0.9f);
    }

    public int slide_images[] = {
            R.drawable.ic_alarm_clock,
            R.drawable.ic_led_bulb,
            R.drawable.ic_lightbulb_outline_black_24dp,
            R.drawable.ic_fridge,
            R.drawable.ic_laptop,
            R.drawable.ic_console,
            R.drawable.ic_cooler,
            R.drawable.ic_flatscreen_tv,
            R.drawable.ic_desktop_computer,
            R.drawable.ic_blender
    };

    public String slide_headings[] = {"ALARM CLOCK", "LED LIGHT BULB", "INCANDESCENT LIGHT BULB", "FRIDGE", "LAPTOP",  "GAMING CONSOLE", "FAN",
            "LCD TV", "GAMING DESKTOP", "BLENDER"};
    public String slide_descs[] = {"You can power this device for " + getRunTime(1),
            "You can power this device for " + getRunTime(2),
            "You can power this device for " + getRunTime(3),
            "You can power this device for " + getRunTime(4),
            "You can power this device for " + getRunTime(5),
            "You can power this device for " + getRunTime(6),
            "You can power this device for " + getRunTime(7),
            "You can power this device for " + getRunTime(8),
            "You can power this device for " + getRunTime(9),
            "You can power this device for " + getRunTime(10)};
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide_stats_layout,container,false);

        ImageView slideImageView = view.findViewById(R.id.slide_image2);
        TextView slideHeaderView = view.findViewById(R.id.slide_heading2);
        TextView slideDescsView = view.findViewById(R.id.slide_description2);

        slideImageView.setImageResource(slide_images[position]);
        slideHeaderView.setText(slide_headings[position]);
        slideDescsView.setText(slide_descs[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

    public String getRunTime(int i){
           String runtime = StatisticsFragment.calculateRunTime(i);
           return runtime;
    }
}

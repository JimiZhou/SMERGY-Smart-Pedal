package com.example.android.smergybike;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Gebruiker on 22/05/2018.
 */

public class SlideAdapterStats extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private Race race;
    private Player redPlayer;
    private Player bluePlayer;


    public SlideAdapterStats(Context context, Race race, Player redPlayer, Player bluePlayer) {
        this.context = context;
        this.race = race;
        this.redPlayer = redPlayer;
        this.bluePlayer = bluePlayer;
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
        TextView slideDescsRedView = view.findViewById(R.id.slide_descriptionRed);
        TextView slideDescsBlueView = view.findViewById(R.id.slide_descriptionBlue);

        slideImageView.setImageResource(slide_images[position]);
        slideHeaderView.setText(slide_headings[position]);
        slideDescsRedView.setText("" + calculateRunTime(position, false));
        slideDescsBlueView.setText("" + calculateRunTime(position, true));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }

    public String calculateRunTime(int i, boolean isBlue){
        long totalTime = (race.getTotalTime()/1000);
        long RunTime;
        String runtimestring;
        int energy;
        if(isBlue){
            energy = bluePlayer.getEnergy();
        }else{
            energy = redPlayer.getEnergy();
        }
        energy = 2000;
        if(i == 1){
            RunTime = (energy/7);
        }else if (i == 2){
            RunTime = (energy/10);
        }else if (i == 3){
            RunTime = (energy/40);
        }else if (i == 4){
            RunTime = (energy/50);
        }else if (i == 5){
            RunTime = (energy/100);
        }else if (i == 6){
            RunTime = (energy/150);
        }else if (i == 7){
            RunTime = (energy/200);
        }else if (i == 8){
            RunTime = (energy/250);
        }else if (i == 9){
            RunTime = (energy/300);
        }else{
            RunTime = (energy/500);
        }

        int minutes = (int) (RunTime) / 60;
        int seconds = (int) (RunTime) % 60;
        if(minutes == 0){
            runtimestring = "" + seconds + " seconds";
        }else{
            runtimestring = "" + minutes + " minutes \n" + seconds + " seconds";
        }

        return runtimestring;
    }
}

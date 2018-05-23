package com.example.android.smergybike.settingsFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.smergybike.R;

/**
 * Created by Joren on 19-5-2018.
 */
public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    public SlideAdapter(Context context) {
        this.context = context;
    }

    public int slide_images[] = {
            R.drawable.ic_calendar_color,
            R.drawable.ic_flag,
            R.drawable.ic_trophy,
            R.drawable.ic_settings_color
    };

    public String slide_headings[] = {"EVENTS", "RACES", "LEADERBOARD", "SETTINGS"};
    public String slide_descs[] = {
            "Create and save every event you go to. You are able to set the race time and see a seperate leaderboard. Every race will be saved under the active event you can change the active event in the settings tab.",
            "Start races in the race tab. \n Fill in the names of the participants and press start. The progressbars show the total energy the racers have generated. After the race you will get an overview of the devices the racers can turn on and the total energy they generated.",
            "In the leaderboard tab, you see the ranking of all players. By using the dropdown menu you can select specific events. By tapping on a player you can see the race overview again.",
            "In the settings tab you can connect to the pedals through bluetooth, create new events, delete events and change the active event. There is also an overview of all the possible milestones and the energy needed to receive them."
    };
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
        View view = inflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = view.findViewById(R.id.slide_image);
        TextView slideHeaderView = view.findViewById(R.id.slide_heading);
        TextView slideDescsView = view.findViewById(R.id.slide_description);

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
}

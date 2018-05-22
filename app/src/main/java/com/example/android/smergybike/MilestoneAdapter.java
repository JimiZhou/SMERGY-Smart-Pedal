package com.example.android.smergybike;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Joren on 20-5-2018.
 */
public class MilestoneAdapter extends ArrayAdapter {

    private String[] headers = {"ALARM CLOCK", "LED LIGHT BULB", "INCANDESCENT LIGHT BULB", "FRIDGE", "LAPTOP",  "GAMING CONSOLE", "FAN",
            "LCD TV", "GAMING DESKTOP", "BLENDER" };
    private String[] descriptions = {"To run for 10 seconds, it needs an energy of 0.07 kJ",
            "To run for 10 seconds, it needs an energy of 0.1 kJ",
            "To run for 10 seconds, it needs an energy of 0.4 kJ",
            "To run for 10 seconds, it needs an energy of 0.5 kJ",
            "To run for 10 seconds, it needs an energy of 1 kJ",
            "To run for 10 seconds, it needs an energy of 1.5 kJ",
            "To run for 10 seconds, it needs an energy of 2 kJ",
            "To run for 10 seconds, it needs an energy of 2.5 kJ",
            "To run for 10 seconds, it needs an energy of 3 kJ",
            "To run for 10 seconds, it needs an energy of 5 kJ"};
    private int[] images = {R.drawable.ic_alarm_clock,
            R.drawable.ic_led_bulb,
            R.drawable.ic_lightbulb_outline_black_24dp,
            R.drawable.ic_fridge,
            R.drawable.ic_laptop,
            R.drawable.ic_console,
            R.drawable.ic_cooler,
            R.drawable.ic_flatscreen_tv,
            R.drawable.ic_desktop_computer,
            R.drawable.ic_blender};

    public MilestoneAdapter(@NonNull Context context, int resource, String[] array) {
        super(context, resource, array);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.milestone_item, null);
        }

        ImageView imageView = v.findViewById(R.id.milestone_image);
        TextView headerView = v.findViewById(R.id.milestone_header);
        TextView descriptionView = v.findViewById(R.id.milestone_text);

        imageView.setImageResource(images[position]);
        headerView.setText(headers[position]);
        descriptionView.setText(descriptions[position]);

        return v;
    }
}

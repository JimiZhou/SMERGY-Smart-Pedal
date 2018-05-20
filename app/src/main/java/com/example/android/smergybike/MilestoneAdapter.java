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

    private String[] headers = {"LIGHTBULB", "LAPTOP", "GAME CONSOLE", "FRIDGE", "FAN",  "BLENDER"};
    private String[] descriptions = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit."};
    private int[] images = {R.drawable.ic_lightbulb_outline_black_24dp,
            R.drawable.ic_laptop,
            R.drawable.ic_console,
            R.drawable.ic_fridge,
            R.drawable.ic_cooler,
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

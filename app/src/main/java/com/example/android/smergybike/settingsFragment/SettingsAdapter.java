package com.example.android.smergybike.settingsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.smergybike.R;

import java.util.List;


/**
 * Created by Joren on 5-5-2018.
 */
public class SettingsAdapter extends  ArrayAdapter<String>{

        public SettingsAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public SettingsAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.settingslist_item, null);
            }

            TextView textView = v.findViewById(R.id.settings_list_text);
            textView.setText(getItem(position));

            ImageView imageView = v.findViewById(R.id.settings_list_icon);
            imageView.setImageResource(R.drawable.ic_keyboard_arrow_right_white_24px);
            return v;
        }


}

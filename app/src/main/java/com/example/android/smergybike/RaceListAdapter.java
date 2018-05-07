package com.example.android.smergybike;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.smergybike.localDatabase.DbModel;

import java.util.List;

/**
 * Created by Joren on 6-5-2018.
 */
public class RaceListAdapter extends ArrayAdapter<Race> {


    private TextView title;
    private ImageView starImage;
    private ImageView deleteImage;
    private List<Race> races;
    private DbModel dbModel;

    public RaceListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public RaceListAdapter(Context context, int resource, List<Race> items) {
        super(context, resource, items);
        races = items;
        dbModel = new DbModel(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item, null);
        }

        Race r = getItem(position);

        if (r != null) {
            title = v.findViewById(R.id.item_title);
            starImage = v.findViewById(R.id.starImage);
            deleteImage = v.findViewById(R.id.imageBtn);

            if (title != null) {
                Player blue = dbModel.getPlayer(r, true);
                Player red = dbModel.getPlayer(r, false);
                String text = blue.getName() + " vs. " + red.getName();
                title.setText(text);
            }

            if (starImage != null) {
                starImage.setImageResource(0);
            }

            if (deleteImage != null) {
                deleteImage.setTag(position);
                deleteImage.setImageResource(R.drawable.ic_delete_forever_black_24px);
            }
            deleteImage.setOnClickListener(deleteListener);
        }
        return v;
    }

    View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            final Race selectedRace = races.get(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Delete")
                    .setMessage("Are you sure you want to delete this event and all its races")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.out.println("deleted event");
                            DbModel dbModel = new DbModel(getContext());
                            dbModel.delete(selectedRace);
                            races.remove(selectedRace);
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Cancel", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };
}

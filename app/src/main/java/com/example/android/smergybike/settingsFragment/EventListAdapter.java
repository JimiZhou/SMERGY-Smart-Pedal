package com.example.android.smergybike.settingsFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.smergybike.Event;
import com.example.android.smergybike.Globals;
import com.example.android.smergybike.R;
import com.example.android.smergybike.localDatabase.DbModel;

import java.util.List;

/**
 * Created by Joren on 4-5-2018.
 */

public class EventListAdapter extends ArrayAdapter<Event> {

    private TextView title;
    private ImageView starImage;
    private ImageView deleteImage;
    private List<Event> events;

    public EventListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public EventListAdapter(Context context, int resource, List<Event> items) {
        super(context, resource, items);
        events = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item, null);
        }

        Event p = getItem(position);

        if (p != null) {
            title = v.findViewById(R.id.item_title);
            starImage = v.findViewById(R.id.starImage);
            deleteImage = v.findViewById(R.id.imageBtn);

            if (title != null) {
                title.setText(p.getTitle());
            }

            if (starImage != null) {
                starImage.setTag(position);
                if (Globals.getGlobals().getCurrentEvent() != null && p.getId() == Globals.getGlobals().getCurrentEvent().getId()){
                    starImage.setImageResource(R.drawable.ic_grade_black_24px);
                }else{
                    starImage.setImageResource(0);
                }
            }

            if (deleteImage != null) {
                deleteImage.setTag(position);
                deleteImage.setImageResource(R.drawable.ic_delete_forever_black_24px);
            }

            starImage.setOnClickListener(starListener);
            deleteImage.setOnClickListener(deleteListener);
        }
        return v;
    }

    View.OnClickListener starListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            ImageView star = (ImageView) view;
            int position = (Integer) view.getTag();
            Event selectedEvent = events.get(position);
            if(Globals.getGlobals().getCurrentEvent() == null){
                Globals.getGlobals().setCurrentEvent(selectedEvent);
                notifyDataSetChanged();
            }
            else if (selectedEvent.getId() != Globals.getGlobals().getCurrentEvent().getId()){
                Globals.getGlobals().setCurrentEvent(selectedEvent);
                notifyDataSetChanged();
            }
        }
    };

    View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            ImageView deleteImage = (ImageView) view;
            int position = (Integer) view.getTag();
            final Event selectedEvent = events.get(position);
            if (selectedEvent.getId() == Globals.getGlobals().getCurrentEvent().getId()){
                System.out.println("you can't delete an active event, create first a new event or select an other out of the list");
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Unable to delete event")
                        .setMessage("you can not delete an active event, start a new event or select another event")
                        .setPositiveButton("ok",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                System.out.println("Are you sure you want to delete?");
                //delete
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete this event and all its races")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                System.out.println("deleted event");
                                DbModel dbModel = new DbModel(getContext());
                                dbModel.delete(selectedEvent);
                                events.remove(selectedEvent);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }
    };

}

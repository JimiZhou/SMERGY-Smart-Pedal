package com.example.android.smergybike.settingsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.smergybike.Event;
import com.example.android.smergybike.R;
import com.example.android.smergybike.localDatabase.DbModel;

import java.util.List;


public class EventListFragment extends Fragment {

    private DbModel dbModel;
    List<Event> events;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbModel = new DbModel(getContext());
        events = dbModel.getAllEvents();
        events.remove(0); //removes all_event
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eventlist, container, false);
        EventListAdapter adapter = new EventListAdapter(getContext(),android.R.layout.simple_list_item_1, events);
        ListView list = view.findViewById(R.id.listView_events);
        TextView emptyText = view.findViewById(R.id.emptyEvents);
        list.setAdapter(adapter);
        list.setOnItemClickListener(mClickListener);
        list.setEmptyView(emptyText);
        getActivity().setTitle("Events");
        return view;
    }

    private AdapterView.OnItemClickListener mClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Event clickedEvent = events.get(position);
            //show all races
            RaceListFragment list_fragment = new RaceListFragment();
            Bundle arguments = new Bundle();
            arguments.putLong( "eventId" , clickedEvent.getId());
            list_fragment.setArguments(arguments);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout,list_fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };


}



package com.example.android.smergybike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.smergybike.localDatabase.DbModel;

import java.util.List;

public class RaceListFragment extends Fragment {

    private long eventId;
    private DbModel dbModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        eventId = arguments.getLong("eventId");
        dbModel = new DbModel(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_race_list, container, false);
        List<Race> races = dbModel.getRacesFromEvent(eventId);
        RaceListAdapter adapter = new RaceListAdapter(getContext(),android.R.layout.simple_list_item_1, races);
        ListView list = view.findViewById(R.id.listView_races);
        TextView emptyText = view.findViewById(R.id.emptyRaces);
        list.setAdapter(adapter);
//        list.setOnItemClickListener(mClickListener);
        list.setEmptyView(emptyText);
        getActivity().setTitle("Events");

        return view;
    }

}

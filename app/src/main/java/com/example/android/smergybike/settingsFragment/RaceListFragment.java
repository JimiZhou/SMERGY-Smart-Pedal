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

import com.example.android.smergybike.R;
import com.example.android.smergybike.Race;
import com.example.android.smergybike.StatisticsFragment;
import com.example.android.smergybike.localDatabase.DbModel;

import java.util.List;

public class RaceListFragment extends Fragment {

    private DbModel dbModel;
    private List<Race> races;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        long eventId = arguments.getLong("eventId");
        dbModel = new DbModel(getContext());
        races = dbModel.getRacesFromEvent(eventId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_race_list, container, false);
        RaceListAdapter adapter = new RaceListAdapter(getContext(),android.R.layout.simple_list_item_1, races);
        ListView list = view.findViewById(R.id.listView_races);
        TextView emptyText = view.findViewById(R.id.emptyRaces);
        list.setAdapter(adapter);
        list.setOnItemClickListener(mClickListener);
        list.setEmptyView(emptyText);
        getActivity().setTitle("Races");

        return view;
    }

    private AdapterView.OnItemClickListener mClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Race clickedRace = races.get(position);

            StatisticsFragment stats_fragment = new StatisticsFragment();
            Bundle arguments = new Bundle();
            arguments.putLong( "SelectedPlayer_raceId" , clickedRace.getId());
            stats_fragment.setArguments(arguments);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, stats_fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };

}

package com.example.android.smergybike.leaderboardFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.smergybike.Event;
import com.example.android.smergybike.Globals;
import com.example.android.smergybike.Player;
import com.example.android.smergybike.R;
import com.example.android.smergybike.Race;
import com.example.android.smergybike.localDatabase.DbModel;

import java.util.List;

public class LeaderboardFragment extends Fragment {

    private List<Player> players;
    private DbModel dbModel;
    private Spinner spinner;
    private RecyclerView recyclerView;
    LeaderboardAdapter adapter;

    public static LeaderboardFragment newInstance() {
        return new LeaderboardFragment();
    }

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbModel = new DbModel(getContext());
        players = dbModel.getAllPlayers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        // set up recycler listview
        recyclerView = view.findViewById(R.id.listView_leaderboard);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LeaderboardAdapter(players, getFragmentManager());
        recyclerView.setAdapter(adapter);

        // fill dropdown menu
       spinner = view.findViewById(R.id.dropdown_leaderboard);
//        List<String> eventTitles = dbModel.getEventTitles();
//        eventTitles.add("All");
        List<Event> events = dbModel.getAllEvents();
        ArrayAdapter dropdownAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, events.toArray());
        dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dropdownAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
                Event event = (Event)spinner.getSelectedItem();
                List<Event> e = dbModel.getAllEvents();
                List<Race> r = dbModel.getAllRaces();
                if (event.getId() == Globals.getGlobals().getAllId()){
                    players = dbModel.getAllPlayers();
                }else {
                    players = dbModel.getPlayersFromEvent(event);
                }
                adapter.updateList(players);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        getActivity().setTitle("Leaderboard");
        return view;
    }


}

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

import com.example.android.smergybike.Event;
import com.example.android.smergybike.Globals;
import com.example.android.smergybike.Player;
import com.example.android.smergybike.R;
import com.example.android.smergybike.localDatabase.DbModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LeaderboardFragment extends Fragment {

    private List<Player> players;
    private DbModel dbModel;
    private Spinner spinner_events;
    private Spinner spinner_raceTime;
    private RecyclerView recyclerView;
    private LeaderboardAdapter adapter;

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
        spinner_events = view.findViewById(R.id.dropdown_leaderboard);
        spinner_raceTime = view.findViewById(R.id.dropdown_racetime);
        List<Event> events = dbModel.getAllEvents();
        List<Long> raceTimes = getAllRaceTimes();
        List<String> RacetimesString = timetoStringArray(raceTimes);
        ArrayAdapter dropdownEventsAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, events.toArray());
        ArrayAdapter dropdownRaceTimesAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, RacetimesString.toArray());
        dropdownEventsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownRaceTimesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_events.setAdapter(dropdownEventsAdapter);
        spinner_raceTime.setAdapter(dropdownRaceTimesAdapter);
        spinner_events.setOnItemSelectedListener(eventsSpinnerListener);
        spinner_raceTime.setOnItemSelectedListener(raceTimeSpinnerListener);
        getActivity().setTitle("Leaderboard");
        return view;
    }

    private List<String> timetoStringArray(List<Long> raceTimes) {
        List<String> formattedTime = new ArrayList<>();
        for(long time : raceTimes){
            formattedTime.add(timeLongToString(time));
        }
        return formattedTime;
    }

    AdapterView.OnItemSelectedListener eventsSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Event event = (Event) spinner_events.getSelectedItem();
            if (event.getId() == Globals.getGlobals().getAllId()) {
                players = dbModel.getAllPlayers();
                spinner_raceTime.setVisibility(View.VISIBLE);
            } else {
                players = dbModel.getPlayersFromEvent(event);
                spinner_raceTime.setVisibility(View.INVISIBLE);
            }
            adapter.updateList(players);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };

    AdapterView.OnItemSelectedListener raceTimeSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String time = (String) spinner_raceTime.getSelectedItem();
            Long timeLong = timeStringToLong(time);
            List<Event> events = dbModel.getAllEventsWithRaceTime(timeLong);
            List<Player> players = new ArrayList<>();
            for(Event e : events){
                players.addAll(dbModel.getPlayersFromEvent(e));
            }
            adapter.updateList(players);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private List<Long> getAllRaceTimes() {
        List<Long> racetimesList = new ArrayList<>();
        List<Event> events = dbModel.getAllEvents();
        events.remove(0);
        long lastRaceLength = 0;
        for(Event e: events){
            long raceLength = e.getRaceLength();
            if(raceLength != lastRaceLength){
                racetimesList.add(raceLength);
                lastRaceLength = raceLength;
            }
        }
        return  racetimesList;
    }

    private long timeStringToLong(String timeString){
        String time[] = timeString.split("\\:");
        String minutesString = time[0];
        String secondsString = time[1];
        long minutesLong = Long.parseLong(minutesString) * 60000; //in milliseconds
        long secondsLong = Long.parseLong(secondsString) * 1000; //in milliseconds
        return minutesLong + secondsLong;
    }

    private String timeLongToString(long timeLong){
        int minutes = (int) (timeLong / 1000) / 60;
        int seconds = (int) (timeLong / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        return timeFormatted;
    }
}

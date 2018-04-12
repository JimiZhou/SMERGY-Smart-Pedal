package com.example.android.smergybike;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android.smergybike.localDatabase.DbModel;

import java.util.List;

public class Leaderboard extends Fragment {

    private List<Player> players;

    public static Leaderboard newInstance() {
        return new Leaderboard();
    }

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbModel dbModel = new DbModel(getContext());
        players = dbModel.getAllPlayers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        // set up recycler listview
        RecyclerView recyclerView = view.findViewById(R.id.listView_leaderboard);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        LeaderboardAdapter adapter = new LeaderboardAdapter(players);
        recyclerView.setAdapter(adapter);

        // fill dropdown menu
        Spinner spinner = view.findViewById(R.id.dropdown_leaderboard);
        ArrayAdapter<CharSequence> dropdownAdapter = ArrayAdapter.createFromResource(getContext(), R.array.leaderboard_array, android.R.layout.simple_spinner_item);
        dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dropdownAdapter);

        getActivity().setTitle("Leaderboard");
        return view;
    }

}

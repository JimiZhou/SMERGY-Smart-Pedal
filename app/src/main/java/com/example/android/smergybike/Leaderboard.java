package com.example.android.smergybike;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class Leaderboard extends Fragment {

    private ArrayList<Player> players;

    public static Leaderboard newInstance() {
        return new Leaderboard();
    }

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        ListView listView = view.findViewById(R.id.listView_leaderboard);
        players = new ArrayList<>();
        players.add(new Player("Joren", 1445));
        players.add(new Player("Frank", 1120));
        players.add(new Player("Lin", 1120));
        players.add(new Player("Jimi", 1004));
        players.add(new Player("Bart", 905));
        players.add(new Player("Eva", 847));
        players.add(new Player("Gorik", 341));

        LeaderboardAdapter adapter = new LeaderboardAdapter(getContext(), R.layout.leaderboard_item ,players);
        listView.setAdapter(adapter);
        return view;
    }

}

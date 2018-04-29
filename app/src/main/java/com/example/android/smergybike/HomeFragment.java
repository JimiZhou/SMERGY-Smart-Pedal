package com.example.android.smergybike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.smergybike.localDatabase.DbModel;

import java.util.List;


public class HomeFragment extends Fragment {

    private DbModel dbModel;
    private EditText editText_blue;
    private EditText editText_red;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbModel = new DbModel(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("SmergyBike");
        Button raceButton = view.findViewById(R.id.button_race);
        editText_blue = view.findViewById(R.id.edit_blue_name);
        editText_red = view.findViewById(R.id.edit_red_name);

        raceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Globals.getGlobals().getCurrentEvent() == null){
                    long eventId = dbModel.insertEvent(new Event("new event", 10 * 1000));
                    Globals.getGlobals().setCurrentEvent(dbModel.getEventById(eventId));
                }
                long blueID = dbModel.insertPlayer(new Player(editText_blue.getText().toString()));
                long redId = dbModel.insertPlayer(new Player(editText_red.getText().toString()));
                List<Player> players = dbModel.getAllPlayers();
                long raceId = dbModel.insertRace(new Race(dbModel.getPlayerById(blueID), dbModel.getPlayerById(redId), Globals.getGlobals().getCurrentEvent()));
                Globals.getGlobals().setCurrentRace(dbModel.getRaceById(raceId));

                RaceFragment race_fragment = new RaceFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, race_fragment);
                transaction.commit();
            }
        });
        return view;
    }



}

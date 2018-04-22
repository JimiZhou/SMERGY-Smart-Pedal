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
                Player blue = new Player(editText_blue.getText().toString());
                Player red = new Player(editText_red.getText().toString());
                long blueID = dbModel.insertPlayer(blue);
                long redId = dbModel.insertPlayer(red);
                List<Player> players = dbModel.getAllPlayers();
                Race newRace = new Race(dbModel.getPlayerById(blueID), dbModel.getPlayerById(redId));
                long raceId = dbModel.insertRace(newRace);

                RaceFragment race_fragment = new RaceFragment();
                Bundle arguments = new Bundle();
                arguments.putLong( "raceId" , raceId);
                race_fragment.setArguments(arguments);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, race_fragment);
                transaction.commit();
            }
        });
        return view;
    }



}

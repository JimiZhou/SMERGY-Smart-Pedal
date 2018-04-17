package com.example.android.smergybike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class Home extends Fragment {

    Button raceButton;

    public static Home newInstance() {
        return new Home();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("SmergyBike");
        raceButton = (Button) view.findViewById(R.id.button_race);
        raceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Race race_fragment = new Race();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, race_fragment);
                transaction.commit();
            }
        });
        return view;



    }



}

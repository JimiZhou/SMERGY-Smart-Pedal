package com.example.android.smergybike;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class RaceFragment extends Fragment {

    Button testbutton;
    ProgressBar bar1;
    ProgressBar bar2;

    public RaceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_race, container, false);
        getActivity().setTitle("SmergyBike");
        testbutton = view.findViewById(R.id.progress_button);
        bar1 = view.findViewById(R.id.progressRacer1);
        bar2 = view.findViewById(R.id.progressRacer2);
        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar1.setProgress(50);
                bar2.setProgress(25);
            }
        });
        return view;
    }

}

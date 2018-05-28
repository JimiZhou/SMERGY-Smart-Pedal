package com.example.android.smergybike;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CountdownFragment extends Fragment {

    private TextView countDown;

    public static CountdownFragment newInstance() {
        return new CountdownFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_countdown, container, false);
        countDown = view.findViewById(R.id.countdown_number);
        startCountDown();
        return view;
    }

    private void startCountDown() {
        CountDownTimer mCountDownTimer = new CountDownTimer(4000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                String time = "" + millisUntilFinished/1000;
                if (millisUntilFinished/1000 == 0){
                    countDown.setText("Go!");
                }else{
                    countDown.setText(time);
                }
            }

            @Override
            public void onFinish() {
                RaceFragment race_fragment = new RaceFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, race_fragment);
                transaction.commit();
            }
        }.start();
    }
}


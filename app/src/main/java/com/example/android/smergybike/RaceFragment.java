package com.example.android.smergybike;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.android.smergybike.bluetooth.Constants;
import com.example.android.smergybike.localDatabase.DbModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class RaceFragment extends Fragment {

    DbModel dbModel = new DbModel(getContext());
    long currentRaceId;
    Race currentRace;
    Player bluePlayer;
    Player redPlayer;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    long startTime = 0;
    long totalTime = 0;
    TextView timerTextView;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            totalTime = System.currentTimeMillis() - startTime;
            int seconds = (int) (totalTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            timerTextView.setText(String.format("%d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle arguments = getArguments();
        currentRaceId = arguments.getLong("raceId");
        currentRace = dbModel.getRaceById(currentRaceId);
        bluePlayer = dbModel.getPlayerById(currentRace.getPlayerblueId());
        redPlayer = dbModel.getPlayerById(currentRace.getPlayerRedId());
        Globals.getGlobals().getBluetoothController().setRaceHandler(mRaceHandler);
        //start timer
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_race, container, false);
        timerTextView = view.findViewById(R.id.timerTextView);
        getActivity().setTitle("SmergyBike");
        RoundCornerProgressBar blueBar = view.findViewById(R.id.blueBar);
        RoundCornerProgressBar redBar= view.findViewById(R.id.redBar);
        blueBar.setProgress(0.3f);
        redBar.setProgress(0.65f);
        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        textView4 = view.findViewById(R.id.textView4);
        textView5 = view.findViewById(R.id.textView5);
        textView6 = view.findViewById(R.id.textView6);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.actionbar_end_race, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.actionbar_endRace){
            //stop timer
            timerHandler.removeCallbacks(timerRunnable);
            currentRace.setTotalTime(totalTime);
            //TODO: update in database
            StatisticsFragment statistics_fragment = new StatisticsFragment();
            Bundle arguments = new Bundle();
            arguments.putLong( "raceId" , currentRaceId);
            statistics_fragment.setArguments(arguments);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, statistics_fragment);
            transaction.commit();
            return true;
        }
        return false;
    }

    public void updateView(String string){
        //double force = Double.parseDouble(string);
        textView1.setText("" + string + " J" );
        textView2.setText("" + " W");
        textView3.setText("" + " m");
        textView4.setText("" + " J");
        textView5.setText("" + " W");
        textView6.setText("" + " m");
        //redPlayer.addPower(Integer.parseInt(string));
        //TODO: add values to players attributes
        //TODO: update progress bars

    }

    @SuppressLint("HandlerLeak")
    public final Handler mRaceHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case Constants.UPDATE_VIEW:
                    Bundle bundle = msg.getData();
                    String string = bundle.getString("update");
                    updateView(string);
                    break;
            }
        }
    };
}

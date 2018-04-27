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
import com.example.android.smergybike.bluetooth.BluetoothController;
import com.example.android.smergybike.bluetooth.Constants;
import com.example.android.smergybike.localDatabase.DbModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class RaceFragment extends Fragment {

    DbModel dbModel = new DbModel(getContext());
    long currentRaceId;
    TextView textView1;
    long startTime = 0;
    TextView timerTextView;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
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
        Race currentRace = dbModel.getRaceById(currentRaceId);
        //TODO: get players in the current race
        BluetoothController.getBTController().setRaceHandler(mRaceHandler);
        //TODO: start timer
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
            //TODO: stop timer
            timerHandler.removeCallbacks(timerRunnable);
            //currentRace.setTotalTime();
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
        textView1.setText("force =  " + string);
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

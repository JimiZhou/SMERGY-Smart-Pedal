package com.example.android.smergybike;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class RaceFragment extends Fragment {

    DbModel dbModel = new DbModel(getContext());
    Race currentRace;
    Event currentEvent;
    Player bluePlayer;
    Player redPlayer;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView timerTextView;
    RoundCornerProgressBar blueBar;
    RoundCornerProgressBar redBar;
    CountDownTimer mCountDownTimer;
    long mTimeLeftInMillis;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        currentRace = Globals.getGlobals().getCurrentRace();
        currentEvent = Globals.getGlobals().getCurrentEvent();
        bluePlayer = dbModel.getPlayer(currentRace, true);
        redPlayer = dbModel.getPlayer(currentRace, false);
        Globals.getGlobals().getBluetoothController().setRaceHandler(mRaceHandler);
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(currentEvent.getRaceLength(), 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountDownText(millisUntilFinished);
                mTimeLeftInMillis = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00");
                mTimeLeftInMillis -= 1000;
                endRace();
            }
        }.start();
    }

    private void updateCountDownText(long millisUntilFinished) {
        int minutes = (int) (millisUntilFinished / 1000) / 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(timeLeftFormatted);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_race, container, false);
        timerTextView = view.findViewById(R.id.timerTextView);
        getActivity().setTitle("SmergyBike");
        blueBar = view.findViewById(R.id.blueBar);
        redBar= view.findViewById(R.id.redBar);
        blueBar.setProgress(0f);
        redBar.setProgress(0f);
        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        textView4 = view.findViewById(R.id.textView4);
        textView5 = view.findViewById(R.id.textView5);
        textView6 = view.findViewById(R.id.textView6);
        updateCountDownText(currentEvent.getRaceLength());
        startTimer();
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
            mCountDownTimer.cancel();
            endRace();
            StatisticsFragment statistics_fragment = new StatisticsFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, statistics_fragment);
            transaction.commit();
            return true;
        }
        return false;
    }

    private void endRace() {
        currentRace.setTotalTime(currentEvent.getRaceLength() - mTimeLeftInMillis);

        //update database
        dbModel.updateRace(currentRace);
        dbModel.updatePlayer(redPlayer);
        dbModel.updatePlayer(bluePlayer);
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

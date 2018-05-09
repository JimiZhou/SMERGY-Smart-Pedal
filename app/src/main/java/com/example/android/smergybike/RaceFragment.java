package com.example.android.smergybike;


import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    ImageView blueImageView;
    ImageView redImageView;
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
        blueImageView = view.findViewById(R.id.imageView2);
        redImageView = view.findViewById(R.id.imageView);
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
        // set players Highscore
        bluePlayer.setHighscore(bluePlayer.getTotalEnergy());
        redPlayer.setHighscore(redPlayer.getTotalEnergy());
        //update database
        dbModel.updateRace(currentRace);
        dbModel.updatePlayer(redPlayer);
        dbModel.updatePlayer(bluePlayer);
    }

    public void updateView(String[] string, boolean isBlue){
        //double force = Double.parseDouble(string);
        if(isBlue){
            textView1.setText("" + string[0] + " J" );
            textView2.setText("" + string[1] + " W");
            textView3.setText("" + string[2] +" m");
        }else{
            textView4.setText("" + string[0] + " J");
            textView5.setText("" + string[1] + " W");
            textView6.setText("" + string[2] + " m");
        }
    }

    @SuppressLint("HandlerLeak")
    public final Handler mRaceHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case Constants.UPDATE_VIEW:
                    Bundle bundle = msg.getData();
                    String string = bundle.getString("update");
                    parseData(string);
                    updateProgressBars();
                    break;
            }
        }
    };

    private void updateProgressBars() {
        int maxEnergy = dbModel.getMaxEnergy();
        blueBar.setProgress(bluePlayer.getTotalEnergy()/maxEnergy);
        redBar.setProgress(redPlayer.getTotalEnergy()/maxEnergy);
    }

    private void parseData(String data) {
        String bikeColor = data.replaceAll("[^a-zA-Z]+", "");
        data = data.replaceAll("[a-zA-Z]+", "");
        String[] output = data.split("\\-");
        for(String str: output){
            System.out.println(str);
        }
        if(bikeColor.equals("red")){
            assert(output.length > 3);
            updateView(output, false );
            updatePlayersParam(output, false);
        }else if(bikeColor.equals("blue")){
            updateView(output, true);
            updatePlayersParam(output, true);
        }
    }

    private void updatePlayersParam(String[] data, boolean isBlue) {
        if(isBlue){
            bluePlayer.addEnergy((int) Double.parseDouble(data[0]));
            bluePlayer.addPower((int) Double.parseDouble(data[1]));
            bluePlayer.setTotalDistance((int) Double.parseDouble(data[2]));
            updateImageViewsBlue();
        }else{
            redPlayer.addEnergy((int) Double.parseDouble(data[0]));
            redPlayer.addPower((int) Double.parseDouble(data[1]));
            redPlayer.setTotalDistance((int) Double.parseDouble(data[2]));
            updateImageViewRed();
        }
    }

    private void updateImageViewsBlue(){
        if(bluePlayer.getTotalPower() <= 60.0D){
             blueImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_lightbulb_outline_black_24dp));
        }else if((bluePlayer.getTotalPower() > 60.0D) && (bluePlayer.getTotalPower() <= 100.0D)){
             blueImageView.setImageDrawable((getResources().getDrawable(R.drawable.ic_laptop)));
        } else if((bluePlayer.getTotalPower() > 100.0D ) && (bluePlayer.getTotalPower() <= 150.0D)){
            blueImageView.setImageDrawable((getResources().getDrawable(R.drawable.ic_console)));
        } else if((bluePlayer.getTotalPower() > 150.0D ) && (bluePlayer.getTotalPower() <= 200.0D)){
            blueImageView.setImageDrawable((getResources().getDrawable(R.drawable.ic_fridge)));
        } else if((bluePlayer.getTotalPower() > 200.0D ) && (bluePlayer.getTotalPower() <= 250.0D)){
            blueImageView.setImageDrawable((getResources().getDrawable(R.drawable.ic_cooler)));
        } else if((bluePlayer.getTotalPower() > 250.0D )) {
            blueImageView.setImageDrawable((getResources().getDrawable(R.drawable.ic_blender)));
        }
    }
    private void updateImageViewRed(){
        if(redPlayer.getTotalPower() <= 60.0D){
            redImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_lightbulb_outline_black_24dp));
        }else if((redPlayer.getTotalPower() > 60.0D) && (redPlayer.getTotalPower() <= 100.0D)){
            redImageView.setImageDrawable((getResources().getDrawable(R.drawable.ic_laptop)));
        } else if((redPlayer.getTotalPower() > 100.0D ) && (redPlayer.getTotalPower() <= 150.0D)){
            redImageView.setImageDrawable((getResources().getDrawable(R.drawable.ic_console)));
        } else if((redPlayer.getTotalPower() > 150.0D ) && (redPlayer.getTotalPower() <= 200.0D)){
            redImageView.setImageDrawable((getResources().getDrawable(R.drawable.ic_fridge)));
        } else if((redPlayer.getTotalPower() > 200.0D ) && (redPlayer.getTotalPower() <= 250.0D)){
            redImageView.setImageDrawable((getResources().getDrawable(R.drawable.ic_cooler)));
        } else if((redPlayer.getTotalPower() > 250.0D )) {
            redImageView.setImageDrawable((getResources().getDrawable(R.drawable.ic_blender)));
        }
    }
}

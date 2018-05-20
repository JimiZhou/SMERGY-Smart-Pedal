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
    TextView textBlueSpeed;
    TextView textBlueForce;
    TextView textBluePower;
    TextView textBlueEnergy;
    TextView textRedSpeed;
    TextView textRedForce;
    TextView textRedPower;
    TextView textRedEnergy;
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
        textBlueSpeed = view.findViewById(R.id.text_blue_speed);
        textBlueForce = view.findViewById(R.id.text_blue_force);
        textBluePower = view.findViewById(R.id.text_blue_power);
        textBlueEnergy = view.findViewById(R.id.text_blue_energy);
        textRedSpeed = view.findViewById(R.id.text_red_speed);
        textRedForce = view.findViewById(R.id.text_red_force);
        textRedPower = view.findViewById(R.id.text_red_power);
        textRedEnergy = view.findViewById(R.id.text_red_energy);
        blueImageView = view.findViewById(R.id.imageView_red);
        redImageView = view.findViewById(R.id.imageView_blue);
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
            Bundle arguments = new Bundle();
            arguments.putLong( "currentRaceId" , Globals.getGlobals().getCurrentRace().getId());
            statistics_fragment.setArguments(arguments);
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
        bluePlayer.setHighscore(bluePlayer.getEnergy());
        redPlayer.setHighscore(redPlayer.getEnergy());
        //update database
        dbModel.updateRace(currentRace);
        dbModel.updatePlayer(redPlayer);
        dbModel.updatePlayer(bluePlayer);
    }

    public void updateView(boolean isBlue){
        //double force = Double.parseDouble(string);
        if(isBlue){
            textBlueSpeed.setText("" + bluePlayer.getPedalSpeed() + " m/s");
            textBlueForce.setText("" + bluePlayer.getForce() + " N");
            textBluePower.setText("" + bluePlayer.getPower() + " W");
            textBlueEnergy.setText("" + bluePlayer.getEnergy() + " kJ" );
        }else{
            textRedSpeed.setText("" + redPlayer.getPedalSpeed() + " m/s");
            textRedForce.setText("" + redPlayer.getForce() + " N");
            textRedPower.setText("" + redPlayer.getPower() + " W");
            textRedEnergy.setText("" + redPlayer.getEnergy() + " kJ");
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
        int maxPower = 500;
        blueBar.setProgress(bluePlayer.getEnergy());
        redBar.setProgress(redPlayer.getEnergy());
    }

    private void parseData(String data) {
        String bikeColor = data.replaceAll("[^a-zA-Z]+", "");
        data = data.replaceAll("[a-zA-Z]+", "");
        String[] output = data.split("\\-");
        for(String str: output){
            System.out.println(str);
        }
        if(bikeColor.equals("red")){
            assert(output.length > 4);
            updatePlayersParam(output, false);
            updateView(false );
        }else if(bikeColor.equals("blue")){
            updatePlayersParam(output, true);
            updateView(true);
        }
    }

    private void updatePlayersParam(String[] data, boolean isBlue) {
        if(isBlue){
            bluePlayer.setPedalSpeed((int) Double.parseDouble(data[0]));
            bluePlayer.setForce((int) Double.parseDouble(data[1]));
            bluePlayer.setPower((int) Double.parseDouble(data[2]));
            bluePlayer.setEnergy((int) Double.parseDouble(data[3]));
            updateImageViewsBlue();
        }else{
            redPlayer.setPedalSpeed((int) Double.parseDouble(data[0]));
            redPlayer.setForce((int) Double.parseDouble(data[1]));
            redPlayer.setPower((int) Double.parseDouble(data[2]));
            redPlayer.setEnergy((int) Double.parseDouble(data[3]));
            updateImageViewRed();
        }
    }

    private void updateImageViewsBlue(){
        if((bluePlayer.getEnergy() > 0) && (bluePlayer.getEnergy() <= 60)){
             blueImageView.setImageResource(R.drawable.ic_lightbulb_outline_black_24dp);
        }else if((bluePlayer.getEnergy() > 60) && (bluePlayer.getEnergy() <= 100)){
             blueImageView.setImageResource(R.drawable.ic_laptop);
        } else if((bluePlayer.getEnergy() > 100 ) && (bluePlayer.getEnergy() <= 150)){
            blueImageView.setImageResource(R.drawable.ic_console);
        } else if((bluePlayer.getEnergy() > 150) && (bluePlayer.getEnergy() <= 200)){
            blueImageView.setImageResource(R.drawable.ic_fridge);
        } else if((bluePlayer.getEnergy() > 200 ) && (bluePlayer.getEnergy() <= 250)){
            blueImageView.setImageResource(R.drawable.ic_cooler);
        } else if((bluePlayer.getEnergy() > 250)) {
            blueImageView.setImageResource(R.drawable.ic_blender);
        }
    }
    private void updateImageViewRed(){
        if((redPlayer.getEnergy() > 0) && (redPlayer.getEnergy() <= 60)){
            redImageView.setImageResource(R.drawable.ic_lightbulb_outline_black_24dp);
        }else if((redPlayer.getEnergy() > 60) && (redPlayer.getEnergy() <= 100)){
            redImageView.setImageResource(R.drawable.ic_laptop);
        } else if((redPlayer.getEnergy() > 100 ) && (redPlayer.getEnergy() <= 150)){
            redImageView.setImageResource(R.drawable.ic_console);
        } else if((redPlayer.getEnergy() > 150) && (redPlayer.getEnergy() <= 200)){
            redImageView.setImageResource(R.drawable.ic_fridge);
        } else if((redPlayer.getEnergy() > 200 ) && (redPlayer.getEnergy() <= 250)){
            redImageView.setImageResource(R.drawable.ic_cooler);
        } else if((redPlayer.getEnergy() > 250)) {
            redImageView.setImageResource(R.drawable.ic_blender);
        }
    }
}

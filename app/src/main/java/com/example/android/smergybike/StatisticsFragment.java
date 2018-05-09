package com.example.android.smergybike;

import android.os.Bundle;
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
import com.example.android.smergybike.localDatabase.DbModel;


public class StatisticsFragment extends Fragment {

    DbModel dbModel = new DbModel(getContext());
    Race showedRace;
    Player bluePlayer;
    Player redPlayer;

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle arguments = getArguments();
        if(arguments != null){
            long raceId = arguments.getLong("SelectedPlayer_raceId");
            showedRace = dbModel.getRaceById(raceId);
        }else{
            showedRace = Globals.getGlobals().getCurrentRace();
        }
        bluePlayer = dbModel.getPlayer(showedRace, true);
        redPlayer = dbModel.getPlayer(showedRace, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Statistics");
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        TextView winnerText = view.findViewById(R.id.title_text);
        setWinnerText(winnerText);
        TextView blueDistanceText = view.findViewById(R.id.distance_blue_text);
        TextView redDistanceText = view.findViewById(R.id.distance_red_text);
        TextView blueEnergyText = view.findViewById(R.id.energy_blue_text);
        TextView redEnergyText = view.findViewById(R.id.energy_red_text);
        TextView bluePowerText = view.findViewById(R.id.power_blue_text);
        TextView redPowerText = view.findViewById(R.id.power_red_text);
        blueDistanceText.setText(bluePlayer.getTotalDistance() + " m"); // race.getPlayerBlue().getTotalDistance()
        redDistanceText.setText(redPlayer.getTotalDistance() + " m");
        blueEnergyText.setText(bluePlayer.getTotalEnergy() + " J");
        redEnergyText.setText(redPlayer.getTotalEnergy() + " J");
        bluePowerText.setText(bluePlayer.getTotalPower() + " W");
        redPowerText.setText(redPlayer.getTotalPower() + " W");
        RoundCornerProgressBar progressRedPower = view.findViewById(R.id.redBar);
        int maxPower = 500;
        int maxEnergy = 500;
        int maxDistance = 500;
        progressRedPower.setProgress((float) redPlayer.getTotalPower()/maxPower);
        RoundCornerProgressBar progressBluePower = view.findViewById(R.id.progress_2);
        progressBluePower.setProgress((float) bluePlayer.getTotalPower()/maxPower);
        RoundCornerProgressBar progressRedEnergy = view.findViewById(R.id.progress_3);
        progressRedEnergy.setProgress((float) redPlayer.getTotalPower()/maxEnergy);
        RoundCornerProgressBar progressBlueEnergy = view.findViewById(R.id.progress_4);
        progressBlueEnergy.setProgress((float) bluePlayer.getTotalPower()/maxEnergy);
        RoundCornerProgressBar progressRedDistance = view.findViewById(R.id.progress_5);
        progressRedDistance.setProgress((float) redPlayer.getTotalPower()/maxDistance);
        RoundCornerProgressBar progressBlueDistance = view.findViewById(R.id.progress_6);
        progressBlueDistance.setProgress((float) bluePlayer.getTotalPower()/maxDistance);

        TextView timeTextView = view.findViewById(R.id.totalRaceTime);
        long time = showedRace.getTotalTime();
        int minutes = (int) (time / 1000) / 60;
        int seconds = (int) (time / 1000) % 60;
        timeTextView.setText(minutes + "min " + seconds + "sec"); // race.getTotalTime()
        return view;
    }

    private void setWinnerText(TextView winnerText) {
        if(redPlayer.getHighscore() > bluePlayer.getHighscore()){
            winnerText.setText("Red Wins");
        }else if(redPlayer.getHighscore() < bluePlayer.getHighscore()){
            winnerText.setText("Blue Wins");
        }else{
            winnerText.setText("It's a draw");
        }
    }

    // TODO: create actionbar_new_race button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.actionbar_new_race, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.actionbar_newRace){
            HomeFragment home_fragment = new HomeFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, home_fragment);
            transaction.commit();
            return true;
        }
        return false;
    }
}

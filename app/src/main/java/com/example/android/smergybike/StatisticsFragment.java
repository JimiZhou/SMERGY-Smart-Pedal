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

    private DbModel dbModel = new DbModel(getContext());
    private Race showedRace;
    private Player bluePlayer;
    private Player redPlayer;
    private boolean fromLeaderboard;


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
            fromLeaderboard = true;
        }else{
            showedRace = Globals.getGlobals().getCurrentRace();
            fromLeaderboard = false;
        }
        bluePlayer = dbModel.getPlayer(showedRace, true);
        redPlayer = dbModel.getPlayer(showedRace, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Overview");
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        TextView winnerText = view.findViewById(R.id.title_text);
        setWinnerText(winnerText);
        TextView blueEnergyText = view.findViewById(R.id.energy_blue_text);
        TextView redEnergyText = view.findViewById(R.id.energy_red_text);
        TextView bluePowerText = view.findViewById(R.id.power_blue_text);
        TextView redPowerText = view.findViewById(R.id.power_red_text);
        blueEnergyText.setText(bluePlayer.getEnergy() + " J");
        redEnergyText.setText(redPlayer.getEnergy() + " J");
        bluePowerText.setText(bluePlayer.getPower() + " W");
        redPowerText.setText(redPlayer.getPower() + " W");
        int maxPower = 500;
        int maxEnergy = 500;
        int maxDistance = 500;
        RoundCornerProgressBar progressBluePower = view.findViewById(R.id.progress_1);
        progressBluePower.setProgress((float) redPlayer.getPower()/maxPower);
        RoundCornerProgressBar progressRedPower = view.findViewById(R.id.progress_2);
        progressRedPower.setProgress((float) bluePlayer.getPower()/maxPower);
        RoundCornerProgressBar progressBlueEnergy = view.findViewById(R.id.progress_3);
        progressBlueEnergy.setProgress((float) redPlayer.getPower()/maxEnergy);
        RoundCornerProgressBar progressRedEnergy = view.findViewById(R.id.progress_4);
        progressRedEnergy.setProgress((float) bluePlayer.getPower()/maxEnergy);

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
        if(!fromLeaderboard){
            inflater.inflate(R.menu.actionbar_new_race, menu);
        }
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

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

    long currentRaceId;
    DbModel dbModel = new DbModel(getContext());
    Race currentRace;

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle arguments = getArguments();
        currentRaceId= arguments.getLong("raceId");
        currentRace = dbModel.getRaceById(currentRaceId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Statistics");
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        TextView winner = view.findViewById(R.id.title_text);
        winner.setText("Red Wins");
        TextView blueDistanceText = view.findViewById(R.id.distance_blue_text);
        TextView redDistanceText = view.findViewById(R.id.distance_red_text);
        TextView blueEnergyText = view.findViewById(R.id.energy_blue_text);
        TextView redEnergyText = view.findViewById(R.id.energy_red_text);
        TextView bluePowerText = view.findViewById(R.id.power_blue_text);
        TextView redPowerText = view.findViewById(R.id.power_red_text);
        blueDistanceText.setText("1300 m"); // race.getPlayerBlue().getTotalDistance()
        redDistanceText.setText("1250 m");
        blueEnergyText.setText("2450 J");
        redEnergyText.setText("1340 J");
        bluePowerText.setText("234 W");
        redPowerText.setText("1934 W");
        RoundCornerProgressBar progressRedPower = view.findViewById(R.id.redBar);
        progressRedPower.setProgress(0.9f);
        RoundCornerProgressBar progressBluePower = view.findViewById(R.id.progress_2);
        progressBluePower.setProgress(0.1f);
        RoundCornerProgressBar progressRedEnergy = view.findViewById(R.id.progress_3);
        progressRedEnergy.setProgress(0.5f);
        RoundCornerProgressBar progressBlueEnergy = view.findViewById(R.id.progress_4);
        progressBlueEnergy.setProgress(1f);
        RoundCornerProgressBar progressRedDistance = view.findViewById(R.id.progress_5);
        progressRedDistance.setProgress(0.8f);
        RoundCornerProgressBar progressBlueDistance = view.findViewById(R.id.progress_6);
        progressBlueDistance.setProgress(0.9f);

        TextView time = view.findViewById(R.id.totalRaceTime);
        time.setText("5min 34sec"); // race.getTotalTime()
        return view;
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

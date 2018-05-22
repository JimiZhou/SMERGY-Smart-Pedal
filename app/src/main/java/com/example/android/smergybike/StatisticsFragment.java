package com.example.android.smergybike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
    private static Race showedRace;
    private static Player bluePlayer;
    private static Player redPlayer;
    private boolean fromLeaderboard;
    private ViewPager mSlideViewPager;
    private SlideAdapterStats slideAdapter;


    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Globals.getGlobals().setCurrentRace(null);
        Bundle arguments = getArguments();
        if(arguments.getLong("SelectedPlayer_raceId", -1) != -1){
            long raceId = arguments.getLong("SelectedPlayer_raceId", -1);
            showedRace = dbModel.getRaceById(raceId);
            fromLeaderboard = true;
        }else if (arguments.getLong("currentRaceId", -1) != -1){
            long raceId = arguments.getLong("currentRaceId");
            showedRace = dbModel.getRaceById(raceId);;
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
        blueEnergyText.setText(bluePlayer.getEnergy() + " kJ");
        redEnergyText.setText(redPlayer.getEnergy() + " kJ");
        int maxEnergy = 500;
        RoundCornerProgressBar progressBlueEnergy = view.findViewById(R.id.progress_3);
        progressBlueEnergy.setProgress((float) redPlayer.getPower()/maxEnergy);
        RoundCornerProgressBar progressRedEnergy = view.findViewById(R.id.progress_4);
        progressRedEnergy.setProgress((float) bluePlayer.getPower()/maxEnergy);

        TextView timeTextView = view.findViewById(R.id.totalRaceTime);
        long time = showedRace.getTotalTime();
        int minutes = (int) (time / 1000) / 60;
        int seconds = (int) (time / 1000) % 60;
        timeTextView.setText(minutes + "min " + seconds + "sec"); // race.getTotalTime()

        // TODO: TEST THIS IMAGESLIDER
        mSlideViewPager = view.findViewById(R.id.SlideViewPager2);
        slideAdapter = new SlideAdapterStats(view.getContext());
        mSlideViewPager.setAdapter(slideAdapter);
        mSlideViewPager.setClipToPadding(false);
        mSlideViewPager.setPageMargin(20);

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

    public static String calculateRunTime(int i){
       long totalTime = (showedRace.getTotalTime()/1000);
       long RunTime;
       String runtimestring;
       int energy = 2000;
       /*  if(redPlayer.getHighscore() > bluePlayer.getHighscore()){
            energy = (redPlayer.getEnergy() *1000);
        }else {
            energy = (bluePlayer.getEnergy()*1000);
        }
        */
        if(i == 1){
            RunTime = (energy/7);
        }else if (i == 2){
            RunTime = (energy/10);
        }else if (i == 3){
            RunTime = (energy/40);
        }else if (i == 4){
            RunTime = (energy/50);
        }else if (i == 5){
            RunTime = (energy/100);
        }else if (i == 6){
            RunTime = (energy/150);
        }else if (i == 7){
            RunTime = (energy/200);
        }else if (i == 8){
            RunTime = (energy/250);
        }else if (i == 9){
            RunTime = (energy/300);
        }else{
            RunTime = (energy/500);
        }

        int minutes = (int) (RunTime) / 60;
        int seconds = (int) (RunTime) % 60;
        if(minutes == 0){
            runtimestring = "" + seconds + " seconds";
        }else{
            runtimestring = "" + minutes + " minutes " + seconds + " seconds";
        }

        return runtimestring;
    }


}

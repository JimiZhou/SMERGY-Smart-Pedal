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

        TextView textRedName = view.findViewById(R.id.playerRedName);
        TextView textBlueName = view.findViewById(R.id.playerBlueName);
        textRedName.setText(redPlayer.getName());
        textBlueName.setText(bluePlayer.getName());

        TextView blueEnergyText = view.findViewById(R.id.energy_blue_text);
        TextView redEnergyText = view.findViewById(R.id.energy_red_text);
        blueEnergyText.setText(bluePlayer.getEnergy() + " kJ");
        redEnergyText.setText(redPlayer.getEnergy() + " kJ");
        RoundCornerProgressBar progressBlueEnergy = view.findViewById(R.id.progress_3);
        progressBlueEnergy.setProgress((float) redPlayer.getEnergy());
        RoundCornerProgressBar progressRedEnergy = view.findViewById(R.id.progress_4);
        progressRedEnergy.setProgress((float) bluePlayer.getEnergy());

        TextView timeTextView = view.findViewById(R.id.totalRaceTime);
        long time = showedRace.getTotalTime();
        int minutes = (int) (time / 1000) / 60;
        int seconds = (int) (time / 1000) % 60;
        timeTextView.setText(minutes + "min " + seconds + "sec"); // race.getTotalTime()


        mSlideViewPager = view.findViewById(R.id.SlideViewPager2);
        slideAdapter = new SlideAdapterStats(view.getContext(), showedRace, bluePlayer, redPlayer);
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




}

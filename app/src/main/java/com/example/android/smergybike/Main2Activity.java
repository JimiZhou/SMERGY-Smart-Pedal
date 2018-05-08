package com.example.android.smergybike;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.smergybike.leaderboardFragment.LeaderboardFragment;
import com.example.android.smergybike.localDatabase.DbModel;
import com.example.android.smergybike.settingsFragment.SettingsFragment;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //sharedPreferences globals
        Globals.getGlobals().setAllId(prefs.getLong("allid", 0));

        //loads setupdate when app gets installed for the first time
        if (!prefs.getBoolean("firstTime", false)) {
            DbModel dbModel = new DbModel(this);
            dbModel.databaseSetupData();
            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.putLong("allid", Globals.getGlobals().getAllId());
            editor.apply();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.commit();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = HomeFragment.newInstance();
                    break;
                case R.id.navigation_leaderboard:
                    selectedFragment = LeaderboardFragment.newInstance();
                    break;
                case R.id.navigation_settings:
                    selectedFragment = SettingsFragment.newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        }

        });
    }
}

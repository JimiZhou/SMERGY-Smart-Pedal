package com.example.android.smergybike.settingsFragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.smergybike.R;

public class MilestonesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestones);
        String[] array = new String[10];
        MilestoneAdapter adapter = new MilestoneAdapter(this, android.R.layout.simple_list_item_1,array);
        ListView listView = findViewById(R.id.milestone_listview);
        listView.setAdapter(adapter);
    }

}

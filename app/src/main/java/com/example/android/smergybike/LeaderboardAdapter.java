package com.example.android.smergybike;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Joren on 29-3-2018.
 */
public class LeaderboardAdapter extends ArrayAdapter<Player> {

    public LeaderboardAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public LeaderboardAdapter(Context context, int resource, List<Player> items) {
        super(context, resource, items);
    }

//    public LeaderboardAdapter(Context context, ArrayList<Player> players){
//        super(context, R.layout.leaderboard_item, players);
//        this.dataSet = players;
//        this.context = context;
//    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.leaderboard_item, null);
        }
        Player player = getItem(position);

        if (player != null) {
            TextView number = v.findViewById(R.id.item_number);
            TextView name = v.findViewById(R.id.item_name);
            TextView score = v.findViewById(R.id.item_score);

            if (number != null) {
                number.setText("1");
            }

            if (name != null) {
                name.setText(player.getName());
            }

            if (score != null) {
                String scoreString = "" +  player.getHighscore();
                score.setText(scoreString);
            }
        }

        return v;
    }
}

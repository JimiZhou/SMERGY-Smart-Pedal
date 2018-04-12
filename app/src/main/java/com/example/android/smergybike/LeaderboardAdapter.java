package com.example.android.smergybike;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Joren on 29-3-2018.
 */
public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    //private String[] mDataset;
    private List<Player> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView itemNumber;
        public TextView itemScore;
        public TextView itemName;

        public ViewHolder(View v) {
            super(v);
            itemNumber = itemView.findViewById(R.id.item_number);
            itemScore = itemView.findViewById(R.id.item_score);
            itemName = itemView.findViewById(R.id.item_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LeaderboardAdapter(List<Player> players) {
        mDataset = players;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LeaderboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Player item = mDataset.get(position);
        if (item != null) {
            int rank = position + 1;
            holder.itemNumber.setText("" + rank);
            holder.itemName.setText(item.getName());
            holder.itemScore.setText("" + item.getHighscore());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}







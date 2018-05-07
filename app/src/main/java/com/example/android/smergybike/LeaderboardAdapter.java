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
class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView itemNumber;
    public TextView itemScore;
    public TextView itemName;
    private RecyclerViewItemClickListener itemClickListener;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemNumber = itemView.findViewById(R.id.item_number);
        itemScore = itemView.findViewById(R.id.item_score);
        itemName = itemView.findViewById(R.id.item_name);
    }

    public void setItemClickListener(RecyclerViewItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition());
    }
}
public class LeaderboardAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Player> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public LeaderboardAdapter(List<Player> players) {
        mDataset = players;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Player item = mDataset.get(position);
        if (item != null) {
            int rank = position + 1;
            holder.itemNumber.setText("" + rank);
            holder.itemName.setText(item.getName());
            holder.itemScore.setText("" + item.getHighscore());
        }
        holder.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                System.out.println("clicked on " + mDataset.get(position).getName());
                //transaction to race
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void updateList(List<Player> data) {
        mDataset = data;
    }
}







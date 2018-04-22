package com.example.android.smergybike;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Joren on 18-4-2018.
 */

@Entity(foreignKeys = {
        @ForeignKey(entity = Player.class,
                parentColumns = "id",
                childColumns = "player_blue",
                onDelete = CASCADE),
        @ForeignKey(entity = Player.class,
                parentColumns = "id",
                childColumns = "player_red",
                onDelete = CASCADE)})

public class Race {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id")
    private long id;
    @ColumnInfo (name = "player_blue")
    private long playerblueId;
    @ColumnInfo (name = "player_red")
    private long playerRedId;
    @ColumnInfo (name = "totaltime")
    private long totalTime;
    @Ignore
    private Date timestamp;

    public Race(){
        playerblueId = -1;
        playerRedId = -1;
        totalTime = 0;
    }
    @Ignore
    public Race(Player blue, Player red) {
        playerblueId = blue.getId();
        playerRedId = red.getId();
        totalTime = 0;
        timestamp = new Date(System.currentTimeMillis());
    }

    public long getPlayerblueId() {
        return playerblueId;
    }

    public void setPlayerblueId(long playerblueId) {
        this.playerblueId = playerblueId;
    }

    public long getPlayerRedId() {
        return playerRedId;
    }

    public void setPlayerRedId(long playerRedId) {
        this.playerRedId = playerRedId;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}



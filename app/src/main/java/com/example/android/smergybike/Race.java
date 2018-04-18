package com.example.android.smergybike;

import java.sql.Date;

/**
 * Created by Joren on 18-4-2018.
 */
public class Race {
    private int id;
    private Player playerblue;
    private Player playerRed;
    private long totalTime;
    private Date timestamp;

    public Race(Player blue, Player red) {
        playerblue = blue;
        playerRed = red;
        totalTime = 0;
        timestamp = new Date(System.currentTimeMillis());
    }

    public Player getPlayerblue() {
        return playerblue;
    }

    public void setPlayerblue(Player playerblue) {
        this.playerblue = playerblue;
    }

    public Player getPlayerRed() {
        return playerRed;
    }

    public void setPlayerRed(Player playerRed) {
        this.playerRed = playerRed;
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
}



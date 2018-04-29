package com.example.android.smergybike;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

/**
 * Created by Joren on 29-4-2018.
 */
@Entity
public class Event {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "raceLength")
    private long raceLength;
    @Ignore
    private ArrayList<Race> races;

    public Event(String title, long raceLength) {
        this.title = title;
        this.raceLength = raceLength;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getRaceLength() {
        return raceLength;
    }

    public void setRaceLength(long raceLength) {
        this.raceLength = raceLength;
    }
}

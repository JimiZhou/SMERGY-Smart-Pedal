package com.example.android.smergybike;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Joren on 18-4-2018.
 */

@Entity(foreignKeys = {
        @ForeignKey(entity =  Event.class,
                parentColumns = "id",
                childColumns = "event",
                onDelete = CASCADE)})

public class Race {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id")
    private long id;
    @ColumnInfo (name = "totaltime")
    private long totalTime;
    @ColumnInfo (name = "event")
    private long eventId;

    public Race( long totalTime, long eventId) {
        this.totalTime = totalTime;
        this.eventId = eventId;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long event) {
        this.eventId = event;
    }
}



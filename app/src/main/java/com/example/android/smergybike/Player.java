package com.example.android.smergybike;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Joren on 29-3-2018.
 */
@Entity(foreignKeys ={
        @ForeignKey(entity = Race.class,
                parentColumns = "id",
                childColumns = "raceId",
                onDelete = CASCADE),
        @ForeignKey(entity = Event.class,
                parentColumns = "id",
                childColumns = "eventId",
                onDelete = CASCADE),
})
public class Player {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "highscore")
    private int highscore;
    @ColumnInfo(name = "totalPower")
    private int totalPower;
    @ColumnInfo(name = "totalEnergy")
    private int totalEnergy;
    @ColumnInfo(name = "totalDistance")
    private int totalDistance;
    @ColumnInfo(name = "raceId")
    private long raceId;
    @ColumnInfo(name = "eventId")
    private long eventId;
    @ColumnInfo(name = "colorBlue")
    private boolean colorBlue;

    public Player(String name, int highscore, int totalPower, int totalEnergy, int totalDistance, long raceId, long eventId, boolean colorBlue){
        this.name = name;
        this.highscore = highscore;
        this.totalEnergy = totalEnergy;
        this.totalPower = totalPower;
        this.totalDistance = totalDistance;
        this.raceId = raceId;
        this.eventId = eventId;
        this.colorBlue = colorBlue;
    }

//    @Ignore
//    public Player(String m_name, int m_highscore){
//        name = m_name;
//        highscore = m_highscore;
//        totalEnergy = 0;
//        totalPower = 0;
//        totalDistance = 0;
//    }
//
//    @Ignore
//    public Player(String m_name){
//        name = m_name;
//        highscore = 0;
//        totalEnergy = 0;
//        totalPower = 0;
//        totalDistance = 0;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String m_name) {
        name = m_name;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int m_highscore) {
        highscore = m_highscore;
    }

    public int getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(int m_totalPower) {
        totalPower = m_totalPower;
    }

    public int getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(int m_totalEnergy) {
        totalEnergy = m_totalEnergy;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int m_totalDistance) {
        totalDistance = m_totalDistance;
    }

    public long getRaceId() {
        return raceId;
    }

    public void setRaceId(long raceId) {
        this.raceId = raceId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public boolean isColorBlue() {
        return colorBlue;
    }

    public void setColorBlue(boolean colorBlue) {
        this.colorBlue = colorBlue;
    }

    public int addPower(int value){
        totalPower += value;
        return totalPower;
    }

    public int addEnergy(int value){
        totalEnergy += value;
        return totalEnergy;
    }
}

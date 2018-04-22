package com.example.android.smergybike;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Joren on 29-3-2018.
 */
@Entity
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

    public Player(String name, int highscore, int totalPower, int totalEnergy, int totalDistance){
        this.name = name;
        this.highscore = highscore;
        this.totalEnergy = totalEnergy;
        this.totalPower = totalPower;
        this.totalDistance = totalDistance;
    }

    @Ignore
    public Player(String m_name, int m_highscore){
        name = m_name;
        highscore = m_highscore;
        totalEnergy = 0;
        totalPower = 0;
        totalDistance = 0;
    }

    @Ignore
    public Player(String m_name){
        name = m_name;
        highscore = 0;
        totalEnergy = 0;
        totalPower = 0;
        totalDistance = 0;
    }

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


}

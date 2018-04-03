package com.example.android.smergybike;

/**
 * Created by Joren on 29-3-2018.
 */
public class Player {

    private int id;
    private String name;
    private int highscore;
    private int totalPower;
    private int totalEnergy;
    private int totalDistance;

    public Player(String m_name, int m_highscore){
        name = m_name;
        highscore = m_highscore;
        totalEnergy = 0;
        totalPower = 0;
        totalDistance = 0;
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

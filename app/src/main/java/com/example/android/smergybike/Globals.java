package com.example.android.smergybike;

import android.app.Application;

import com.example.android.smergybike.bluetooth.BluetoothController;

/**
 * Created by Joren on 29-4-2018.
 */
public class Globals extends Application {

    private static Globals sInstance;
    private BluetoothController bluetoothController;
    private Event currentEvent;
    private Race currentRace;
    private long allId;
    private boolean BTconnected;
    private String blueName;
    private String redName;

    public Globals(){
       sInstance = this;
       bluetoothController = new BluetoothController();
       currentEvent = null;
       currentRace = null;
       allId = 0;
       BTconnected = false;
       blueName = null;
       redName = null;
    }

    public static Globals getGlobals() {
        return sInstance;
    }

    public BluetoothController getBluetoothController() {
        return bluetoothController;
    }

    public void setBluetoothController(BluetoothController bluetoothController) {
        this.bluetoothController = bluetoothController;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public Race getCurrentRace() {
        return currentRace;
    }

    public void setCurrentRace(Race currentRace) {
        this.currentRace = currentRace;
    }

    public long getAllId() {
        return allId;
    }

    public void setAllId(long allId) {
        this.allId = allId;
    }

    public boolean isBTconnected() {
        return BTconnected;
    }

    public void setBTconnected(boolean BTconnected) {
        this.BTconnected = BTconnected;
    }

    public String getBlueName() {
        return blueName;
    }

    public void setBlueName(String blueName) {
        this.blueName = blueName;
    }

    public String getRedName() {
        return redName;
    }

    public void setRedName(String redName) {
        this.redName = redName;
    }
}

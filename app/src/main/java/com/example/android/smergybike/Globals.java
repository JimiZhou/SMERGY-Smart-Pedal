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

    public Globals(){
       sInstance = this;
       bluetoothController = new BluetoothController();
       currentEvent = null;
       currentRace = null;
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
}

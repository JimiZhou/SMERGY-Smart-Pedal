package com.example.android.smergybike.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by Joren on 28-2-2018.
 */

public class ConnectThread extends Thread {

    private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private final BluetoothSocket mSocket;
    private final BluetoothDevice mDevice;

    public ConnectThread(BluetoothDevice device){
        BluetoothSocket tmp = null;
        mDevice = device;
        try {
            System.out.println(myUUID.toString());
            tmp = mDevice.createRfcommSocketToServiceRecord(myUUID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's create() method failed", e);
        }
        mSocket = tmp;
    }

    public BluetoothSocket getBluetoothSocket(){
        return mSocket;
    }

    public void run(){
        try {
            mSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                mSocket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
                System.out.println("Could not close the client socket");
            }
            return;
        }
        System.out.println("succesfully connected to bluetooth device");
    }
}

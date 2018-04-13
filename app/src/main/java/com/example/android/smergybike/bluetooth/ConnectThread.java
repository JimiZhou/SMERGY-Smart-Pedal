package com.example.android.smergybike.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
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
    private Handler mHandler;

    public ConnectThread(BluetoothDevice device, Handler handler){
        mHandler = handler;
        BluetoothSocket tmp = null;
        try {
            System.out.println(myUUID.toString());
            tmp = device.createRfcommSocketToServiceRecord(myUUID);
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
                System.out.println("Unable to connect to device");
                Message msg = mHandler.obtainMessage(Constants.FAILED_TO_CONNECT);
                mHandler.sendMessage(msg);
                mSocket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }
        System.out.println("succesfully connected to bluetooth device");
        Message msg = mHandler.obtainMessage(Constants.CONNECTED);
        mHandler.sendMessage(msg);
    }
}

package com.example.android.smergybike;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;


import java.util.Set;
import java.util.UUID;

/**
 * Created by Joren on 28-2-2018.
 */

public class BluetoothController {

    private static final UUID myUUID = UUID.fromString("5f3b4765-1ef9-4e32-b66a-76c18eeb9cf4");

    private final BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> pairedDevices;
    BluetoothSocket socket = null;

    public BluetoothController(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public String[] getAllPairedDevices(){
        pairedDevices = mBluetoothAdapter.getBondedDevices();
        String[] deviceArray = new String[pairedDevices.size()];
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            int i = 0;
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                System.out.println("name: " + deviceName + " MAC: " + deviceHardwareAddress);
                deviceArray[i] = deviceName +  "\n" + deviceHardwareAddress;
                i++;
            }
        }
        return deviceArray;
    }

    public void connectDevice(String macAddress){
        BluetoothDevice BluetoothDevice = mBluetoothAdapter.getRemoteDevice(macAddress);
        ConnectThread BTconnectThread = new ConnectThread(BluetoothDevice);
        socket = BTconnectThread.getBluetoothSocket();
        BTconnectThread.start();
        try {
            BTconnectThread.join();
        }catch (InterruptedException ex){
            System.out.println("unable to join connectThread");
        }
    }

    public void manageConnection(Handler mHandler){
        ConnectedThread BTconnectedThread = new ConnectedThread(socket, mHandler);
        BTconnectedThread.start();
    }


}

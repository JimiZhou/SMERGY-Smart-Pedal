package com.example.android.smergybike.bluetooth;

import android.annotation.SuppressLint;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.Set;
import java.util.UUID;

/**
 * Created by Joren on 28-2-2018.
 */

public class BluetoothController extends Application {

    private static final UUID myUUID = UUID.fromString("5f3b4765-1ef9-4e32-b66a-76c18eeb9cf4");
    private ConnectedThread BTconnectedThread = null;
    private final BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private BluetoothSocket socket = null;
    private static BluetoothController sInstance;
    //private Handler mHandler;
    private Context mContext;
    public static String force = "0";

    public BluetoothController(){
        sInstance = this;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

//    public void setHandler(Handler handler){
//        mHandler = handler;
//    }

    public void setContext(Context context){
        mContext = context;
    }

    public static BluetoothController getBTController() {
        return sInstance;
    }

    public BluetoothAdapter getBTAdapter (){
        return mBluetoothAdapter;
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

    public int connectDevice(String macAddress){
        BluetoothDevice BluetoothDevice = mBluetoothAdapter.getRemoteDevice(macAddress);
        if(BluetoothDevice != null) {
            ConnectThread BTconnectThread = new ConnectThread(BluetoothDevice, mHandler);
            socket = BTconnectThread.getBluetoothSocket();
            BTconnectThread.start();
            try {
                BTconnectThread.join();
            }catch (InterruptedException ex){
                System.out.println("unable to join connectThread");
            }
            return 1;
        }
        return -1;
    }

    public void manageConnection(){
        BTconnectedThread = new ConnectedThread(socket, mHandler);
        BTconnectedThread.start();
    }

    public void CancelConnection(){
        BTconnectedThread.cancel();
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case Constants.FAILED_TO_CONNECT:
                    Toast.makeText(mContext, "Failed to connect", Toast.LENGTH_SHORT).show();
                    break;
                case Constants.CONNECTED:
                    Toast.makeText(mContext, "connection successful", Toast.LENGTH_SHORT).show();
                    break;
                case Constants.MESSAGE_READ:
                    Bundle bundle = msg.getData();
                    String string = bundle.getString("message");
                    force = string;
                    System.out.println(string);
                    break;
            }
        }
    };
}


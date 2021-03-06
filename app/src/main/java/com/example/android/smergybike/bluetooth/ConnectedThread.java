package com.example.android.smergybike.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.android.smergybike.Globals;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

/**
 * Created by Joren on 28-2-2018.
 */

public class ConnectedThread extends Thread{
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private byte[] mmBuffer; // mmBuffer store for the stream
    private Handler mmHandler;

    public ConnectedThread(BluetoothSocket socket, Handler handler) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        mmHandler = handler;


        // Get the input and output streams; using temp objects because
        // member streams are final.
        try {
            tmpIn = socket.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating input stream", e);
        }
        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating output stream", e);
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        mmBuffer = new byte[1024];
        System.out.println("bufferlength: " + mmBuffer.length);
        int numBytes; // bytes returned from read()
        StringBuilder stringBuilder = new StringBuilder();
        // Keep listening to the InputStream until an exception occurs.
        while (true) {
            try {
                // Read from the InputStream.
                numBytes = mmInStream.read(mmBuffer);
                String readMessage = new String(mmBuffer, 0, numBytes);
                for(int i = 0; i < readMessage.length(); i++)
                {
                    char c = readMessage.charAt(i);
                    if (c == '/'){
                        String finalMessage = stringBuilder.toString();
                        stringBuilder.setLength(0);
                        Message msg = mmHandler.obtainMessage(Constants.MESSAGE_READ);
                        Bundle bundle = new Bundle();
                        bundle.putString("message", finalMessage);
                        msg.setData(bundle);
                        mmHandler.sendMessage(msg);
                    }
                    else{
                        stringBuilder.append(c);
                    }
                }
            } catch (IOException e) {
                Log.d(TAG, "Connection lost", e);
                Globals.getGlobals().setBTconnected(false);
                break;
            }
        }
    }

    public void write(byte[] buffer) {
        try {
            mmOutStream.write(buffer);

            // Share the sent message back to the UI Activity
            mmHandler.obtainMessage(Constants.MESSAGE_WRITE, -1, -1, buffer)
                    .sendToTarget();
        } catch (IOException e) {
            Log.e(TAG, "Exception during write", e);
        }
    }


    // Call this method from the main activity to shut down the connection.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}

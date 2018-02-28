package com.example.android.smergybike;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BluetoothController BTcontroller = new BluetoothController();
        final Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                System.out.println("in handler");
                byte[] readBuf = (byte[]) msg.obj;
                String readMessage = new String(readBuf, 0, msg.arg1);
                System.out.println("handler: " + readMessage);
                TextView text = (TextView) findViewById(R.id.readData);
                text.setText(readMessage);
            }
        };
        //BTcontroller.getAllPairedDevices();

        ArrayAdapter<String> BluetoothDeviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, BTcontroller.getAllPairedDevices());
        ListView listView = (ListView) findViewById(R.id.deviceListView);
        listView.setAdapter(BluetoothDeviceAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String info = ((TextView) view).getText().toString();
                String address = info.substring(info.length() - 17);
                System.out.println(address);
                BTcontroller.connectDevice(address);
                BTcontroller.manageConnection(mHandler);
                //Intent intent = new Intent( getApplicationContext() , MessageActivity.class);
                //startActivity(intent);
            }
        });
    }
}

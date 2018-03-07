package com.example.android.smergybike;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;



public class MainActivity extends AppCompatActivity {

    private double speed;
    private double energy;
    private double power;
    private ArrayList avgpower;
    private Button mStartButton;
    private long starttime;
    private long stoptime;
    private Button mStopButton;
    private BluetoothController BTcontroller;

    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            System.out.println("in handler");
            Bundle bundle = msg.getData();
            String string = bundle.getString("message");
            double value = Double.parseDouble(string);
            speed = value;
            String string2 = ("Speed: " + speed + "km/h");
            System.out.println("handler: " + string);
            TextView text = (TextView) findViewById(R.id.readData);
            text.setText(string2);
            calculateVermogen();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        avgpower = new ArrayList();

        BTcontroller = new BluetoothController();
        mStartButton = (Button) findViewById(R.id.start_button);
        mStopButton = (Button) findViewById(R.id.stop_button);
        //BTcontroller.getAllPairedDevices();

        setListeners();

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
            }
        });
    }

    public void calculateVermogen() {

        double vermogenRood1 = Math.pow(speed, 3.0D);
        double vermogenRood2 = Math.pow(speed, 2.0D);
        double vermogenRood3 = speed;

        double vermogenRoodStore = 7.0D * vermogenRood1 / 24000.0D + 161.0D * vermogenRood2 / 800.0D - 287.0D * vermogenRood3 / 120.0D;
        this.power = (Math.floor(vermogenRoodStore * 100.0D) / 100.0D);

        avgpower = new ArrayList<>();
        avgpower.add(power);
        String string = ("Power:" + this.power);
        TextView text = (TextView) findViewById(R.id.readPower);
        text.setText(string);


    }

    public void calculateEnergy() {
        long elapsedMilliSeconds = stoptime - starttime;
        long elapsedSeconds = (elapsedMilliSeconds / 1000);

        double energieRoodStore = getAverage(avgpower) / elapsedSeconds;     // gedurende 1 minuut fietsen
        this.energy = (Math.floor(energieRoodStore * 100.0D) / 100.0D);


        String string = (" Average Power:" + getAverage(avgpower));
        TextView text = (TextView) findViewById(R.id.readPower);
        text.setText(string);
        String string2 = ("energy:" + this.energy);
        TextView text2 = (TextView) findViewById(R.id.readEnergy);
        text2.setText(string2);

    }

    public void setListeners() {

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starttime = SystemClock.elapsedRealtime();
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stoptime = SystemClock.elapsedRealtime();
                calculateEnergy();
                BTcontroller.CancelConnection();
            }
        });
    }

    public double getAverage(List<Double> sum) {
    double total = 0;
        for (int i = 0; i < sum.size(); i++) {
            total = total + sum.get(i);

        }
        return total/sum.size();

    }
}

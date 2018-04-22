package com.example.android.smergybike;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.smergybike.bluetooth.BluetoothController;

import java.util.Arrays;

public class SettingsFragment extends Fragment {

    private BluetoothController BTcontroller;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        String[] values = new String[]{"Connect Bluetooth","Start new Event"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1, Arrays.asList(values));
        ListView listView = view.findViewById(R.id.listView_settings);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(mClickListener);
        getActivity().setTitle("Settings");
        return view;
    }

     private AdapterView.OnItemClickListener mClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            switch (position){
                case 0:
                    //'connect bluetooth' pressed
                    connectBluetooth();
                    break;
                case 1:
                    //new event
                    break;
            }
        }
    };

    private void connectBluetooth(){
        BluetoothAdapter mBluetoothAdapter =  BluetoothController.getBTController().getBTAdapter();
        //BluetoothController.getBTController().setHandler(mHandler);
        BluetoothController.getBTController().setContext(getContext());
        if (mBluetoothAdapter == null){
            //device doesn't support Bluetooth
            android.widget.Toast.makeText(getContext(), "Bluetooth is not available", android.widget.Toast.LENGTH_LONG).show();
        }
        else if (!mBluetoothAdapter.isEnabled()){
            android.content.Intent enableBtIntent = new android.content.Intent(android.bluetooth.BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }else {
            showPairedDevicesDialog();
        }
    }

    private void showPairedDevicesDialog(){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setTitle("select paired device").setItems(BluetoothController.getBTController().getAllPairedDevices(), new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int which) {
                String[] deviceArray = BluetoothController.getBTController().getAllPairedDevices();
                String info = deviceArray[which];
                String address = info.substring(info.length() - 17);
                System.out.println(address);
                if (BluetoothController.getBTController().connectDevice(address) < 0){
                    android.widget.Toast.makeText(getContext(), "Unable to find device", android.widget.Toast.LENGTH_LONG).show();
                }
                else{
                    BluetoothController.getBTController().manageConnection();
                }
            }
        });
        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }



}
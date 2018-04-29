package com.example.android.smergybike;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.smergybike.bluetooth.BluetoothController;
import com.example.android.smergybike.localDatabase.DbModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class SettingsFragment extends Fragment {

    private BluetoothController BTcontroller;
    private DbModel dbModel;
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BTcontroller = Globals.getGlobals().getBluetoothController();
        dbModel = new DbModel(getContext());
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
                    createNewEvent();
                    break;
            }
        }
    };

    private void createNewEvent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View mview = getLayoutInflater().inflate(R.layout.dialog_event, null);
        builder.setView(mview);
        final AlertDialog dialog = builder.create();
        final EditText editTextTitle = mview.findViewById(R.id.event_title);
        final EditText editTextTime = mview.findViewById(R.id.event_duration);
        Button button = mview.findViewById(R.id.btnCreate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editTextTitle.getText().toString().isEmpty() && !editTextTime.getText().toString().isEmpty()) {
                    DateFormat formatter = new SimpleDateFormat("mm:ss");
                    Date date = null;
                    try {
                        date = formatter.parse(editTextTime.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (date != null) {
                        long eventId = dbModel.insertEvent(new Event(editTextTitle.getText().toString(), date.getTime()));
                        Globals.getGlobals().setCurrentEvent(dbModel.getEventById(eventId));
                        Toast.makeText(getContext(), "New event started", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }
                else{
                    Toast.makeText(getContext(),"please fill in empty fields", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();

    }

    private void connectBluetooth(){
        BluetoothAdapter mBluetoothAdapter =  BTcontroller.getBTAdapter();
        //BluetoothController.getBTController().setHandler(mHandler);
        BTcontroller.setContext(getContext());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("select paired device").setItems(BTcontroller.getAllPairedDevices(), new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int which) {
                String[] deviceArray = BTcontroller.getAllPairedDevices();
                String info = deviceArray[which];
                String address = info.substring(info.length() - 17);
                System.out.println(address);
                if (BTcontroller.connectDevice(address) < 0){
                    Toast.makeText(getContext(), "Unable to find device",Toast.LENGTH_LONG).show();
                }
                else{
                    BTcontroller.manageConnection();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}

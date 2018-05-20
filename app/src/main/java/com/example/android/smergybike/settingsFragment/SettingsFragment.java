package com.example.android.smergybike.settingsFragment;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.smergybike.Event;
import com.example.android.smergybike.Globals;
import com.example.android.smergybike.MilestonesActivity;
import com.example.android.smergybike.R;
import com.example.android.smergybike.TutorialActivity;
import com.example.android.smergybike.bluetooth.BluetoothController;
import com.example.android.smergybike.localDatabase.DbModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
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
        String[] bluetooth_values = new String[]{"Connect Bluetooth"};
        String[] event_values = new String[]{"Start new Event", "List Events"};
        String[] tutorial_values = new String[] {"Start Tutorial", "Milestones Overview"};
        SettingsAdapter adapter_bt = new SettingsAdapter(getContext(),android.R.layout.simple_list_item_1, Arrays.asList(bluetooth_values));
        SettingsAdapter adapter_event = new SettingsAdapter(getContext(),android.R.layout.simple_list_item_1, Arrays.asList(event_values));
        SettingsAdapter adapter_tutorial = new SettingsAdapter(getContext(), android.R.layout.simple_list_item_1, Arrays.asList(tutorial_values));
        ListView listView_bt = view.findViewById(R.id.listView_settings_bluetooth);
        ListView listView_event = view.findViewById(R.id.listView_settings_event);
        ListView listView_tutorial = view.findViewById(R.id.listView_settings_tutorial);
        listView_bt.setAdapter(adapter_bt);
        listView_event.setAdapter(adapter_event);
        listView_tutorial.setAdapter(adapter_tutorial);
        listView_bt.setOnItemClickListener(mClickListener_bluetooth);
        listView_event.setOnItemClickListener(mClickListener_event);
        listView_tutorial.setOnItemClickListener(mClickListener_tutorial);
        getActivity().setTitle("Settings");
        return view;
    }

     private AdapterView.OnItemClickListener mClickListener_bluetooth = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            switch (position){
                case 0:
                    connectBluetooth();
            }
        }
    };

    private AdapterView.OnItemClickListener mClickListener_event = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            switch (position){
                case 0:
                    createNewEvent();
                    break;
                case 1:
                    showEventList();
                    break;
            }
        }
    };

    private AdapterView.OnItemClickListener mClickListener_tutorial = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            switch (position){
                case 0:
                    Intent tutorialIntent = new Intent(getActivity(), TutorialActivity.class);
                    startActivity(tutorialIntent);
                    break;
                case 1:
                    Intent milestoneIntent = new Intent(getActivity(), MilestonesActivity.class);
                    startActivity(milestoneIntent);
                    break;
            }
        }
    };


    private void showEventList() {
        EventListFragment list_fragment = new EventListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,list_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

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
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        long time = cal.get(Calendar.MINUTE) * 60000 + cal.get(Calendar.SECOND) * 1000; // number of minutes * number of milli in minute + number of sec ...
                        long eventId = dbModel.insertEvent(new Event(editTextTitle.getText().toString(), time));
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

    public void connectBluetooth(){
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
        builder.setCancelable(true);
        builder.setTitle("select paired device").setItems(BTcontroller.getAllPairedDevices(), new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int which) {
                final int position = which;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View mview = getLayoutInflater().inflate(R.layout.dialog_loading, null);
                builder.setView(mview);
                final AlertDialog dialog_loading = builder.create();
                dialog_loading.show();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        showLoadingDialog(position);
                        dialog_loading.dismiss();
                    }
                });
                thread.start();
            }
        });
        AlertDialog listDialog = builder.create();
        listDialog.show();
    }

    private void showLoadingDialog(int which) {
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


}

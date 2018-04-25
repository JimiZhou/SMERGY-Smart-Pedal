package com.example.android.smergybike;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.smergybike.bluetooth.BluetoothController;
import com.example.android.smergybike.bluetooth.Constants;
import com.example.android.smergybike.localDatabase.DbModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class RaceFragment extends Fragment {

    DbModel dbModel = new DbModel(getContext());
    long currentRaceId;
    Button testbutton;
    ProgressBar bar1;
    ProgressBar bar2;
    double force;
    TextView textViewForce;
    boolean loop = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle arguments = getArguments();
        currentRaceId= arguments.getLong("raceId");
        Race currentRace = dbModel.getRaceById(currentRaceId);
        BluetoothController.getBTController().setRaceHandler(mRaceHandler);
        //start timer
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_race, container, false);
        getActivity().setTitle("SmergyBike");
        testbutton = view.findViewById(R.id.progress_button);
        textViewForce = view.findViewById(R.id.time_racer1);
        bar1 = view.findViewById(R.id.progressRacer1);
        bar1.setMax(200);
        bar2 = view.findViewById(R.id.progressRacer2);

      /**        getActivity().runOnUiThread(new Runnable(){


            @Override
            public void run() {



            }
        });
**/
        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                force = Double.parseDouble(BluetoothController.force);
//                textViewForce.setText("force =  " + BluetoothController.force);
//                bar1.setProgress((int) force);
//                bar2.setProgress(25);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.actionbar_next_button, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.actionbar_next){
            //stop timer
            //currentRace.setTotalTime();
            // update in database
            StatisticsFragment statistics_fragment = new StatisticsFragment();
            Bundle arguments = new Bundle();
            arguments.putLong( "raceId" , currentRaceId);
            statistics_fragment.setArguments(arguments);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, statistics_fragment);
            transaction.commit();
            return true;
        }
        return false;
    }

    public void updateView(String string){
        force = Double.parseDouble(string);
        textViewForce.setText("force =  " + string);
        bar1.setProgress((int) force);
        bar2.setProgress(25);
    }

    @SuppressLint("HandlerLeak")
    public final Handler mRaceHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case Constants.UPDATE_VIEW:
                    Bundle bundle = msg.getData();
                    String string = bundle.getString("update");
                    updateView(string);
                    break;
            }
        }
    };
}

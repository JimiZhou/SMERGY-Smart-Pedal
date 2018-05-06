package com.example.android.smergybike;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.smergybike.localDatabase.DbModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment {

    private DbModel dbModel;
    private EditText editText_blue;
    private EditText editText_red;
    Event currentEvent;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbModel = new DbModel(getContext());
        currentEvent = Globals.getGlobals().getCurrentEvent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("SmergyBike");
        Button raceButton = view.findViewById(R.id.button_race);
        editText_blue = view.findViewById(R.id.edit_blue_name);
        editText_red = view.findViewById(R.id.edit_red_name);
        TextView raceTimeView = view.findViewById(R.id.race_duration);
        TextView eventTextView = view.findViewById(R.id.event_indicator);
        if (currentEvent != null) {
            long time = currentEvent.getRaceLength();
            int minutes = (int) (time / 1000) / 60;
            int seconds = (int) (time / 1000) % 60;
            currentEvent.getRaceLength();
            raceTimeView.setText("Race time:  " + minutes + "min " + seconds + "sec");
            eventTextView.setText(Globals.getGlobals().getCurrentEvent().getTitle());
        }
        raceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentEvent == null){
                    createNewEvent();
                }else{
                    createRace();
                }
            }
        });
        return view;
    }

    private void createRace() {
        long blueID = dbModel.insertPlayer(new Player(editText_blue.getText().toString()));
        long redId = dbModel.insertPlayer(new Player(editText_red.getText().toString()));
        List<Player> players = dbModel.getAllPlayers();
        long raceId = dbModel.insertRace(new Race(dbModel.getPlayerById(blueID), dbModel.getPlayerById(redId), Globals.getGlobals().getCurrentEvent()));
        Globals.getGlobals().setCurrentRace(dbModel.getRaceById(raceId));

        RaceFragment race_fragment = new RaceFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, race_fragment);
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
                        createRace();
                    }
                }
                else{
                    Toast.makeText(getContext(),"please fill in empty fields", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();

    }

}

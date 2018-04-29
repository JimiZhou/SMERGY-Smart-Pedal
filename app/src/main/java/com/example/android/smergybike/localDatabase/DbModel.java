package com.example.android.smergybike.localDatabase;

import android.content.Context;

import com.example.android.smergybike.Event;
import com.example.android.smergybike.Player;
import com.example.android.smergybike.Race;

import java.util.List;

/**
 * Created by Joren on 12-4-2018.
 */
public class DbModel {

    private PlayerDao mplayerDao;
    private RaceDao mraceDao;
    private EventDao mEventDao;
    private List<Player> returnValue;
    private Player returnPlayer;
    private long returnValueId;
    private Race returnRace;
    private Event returnEvent;


    public DbModel(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        mplayerDao = db.playerDao();
        mraceDao = db.raceDao();
        mEventDao = db.eventDao();
    }

    private void setReturnValue(List<Player> value){
        returnValue = value;
    }

    public long insertPlayer(final Player player) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnValueId = mplayerDao.insert(player);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnValueId;
    }

    public List<Player> getAllPlayers() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
               setReturnValue(mplayerDao.getAll());

            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public Player getPlayerById(final long id){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnPlayer = mplayerDao.getPlayerById(id);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnPlayer;
    }

    public long insertRace(final Race race) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnValueId = mraceDao.insert(race);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnValueId;
    }

    public void deleteAllPlayers(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mplayerDao.deleteAllPlayers();
            }
        });
        thread.start();
    }

    public Race getRaceById(final long id){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnRace = mraceDao.getRaceById(id);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnRace;
    }

    public void deleteAllRaces(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mraceDao.deleteAllRaces();
            }
        });
        thread.start();
    }

    public long insertEvent(final Event event) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnValueId = mEventDao.insert(event);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnValueId;
    }

    public Event getEventById(final long id){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnEvent = mEventDao.getEventById(id);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnEvent;
    }

    public void loadDummyData(){
        deleteAllPlayers();
        deleteAllRaces();
        insertPlayer(new Player("Joren", 1445,0,0,0));
        insertPlayer(new Player("Lin", 1120,0,0,0));
        insertPlayer(new Player("Jimi", 1004,0,0,0));
        insertPlayer(new Player("Bart", 905,0,0,0));
        insertPlayer(new Player("Eva", 847,0,0,0));
        insertPlayer(new Player("Gorik", 341,0,0,0));
    }
}

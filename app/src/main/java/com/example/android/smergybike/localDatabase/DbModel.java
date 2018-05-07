package com.example.android.smergybike.localDatabase;

import android.content.Context;

import com.example.android.smergybike.Event;
import com.example.android.smergybike.Globals;
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
    private List<Player> returnPlayerList;
    private Player returnPlayer;
    private long returnValueId;
    private int returnMax;
    private Race returnRace;
    private Event returnEvent;
    private List<Event> returnEventList;
    private List<Race> returnRaceList;
    private List<String> returnEventTitles;


    public DbModel(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        mplayerDao = db.playerDao();
        mraceDao = db.raceDao();
        mEventDao = db.eventDao();
    }

    private void setReturnValue(List<Player> value){
        returnPlayerList = value;
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
        return returnPlayerList;
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

    public void updatePlayer(final Player player){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mplayerDao.update(player);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getMaxPower(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnMax = mplayerDao.getMaxPower();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnMax;
    }

    public int getMaxEnergy() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnMax = mplayerDao.getMaxEnergy();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnMax;
    }

    public int getMaxDistance() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnMax = mplayerDao.getMaxDistance();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnMax;
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
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Race> getAllRaces(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnRaceList = mraceDao.getAll();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnRaceList;
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

    public void updateRace(final Race race){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mraceDao.update(race);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllRaces(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mraceDao.deleteAllRaces();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public void deleteAllEvents(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mEventDao.deleteAllEvents();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getAllEvents(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnEventList = mEventDao.getAll();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnEventList;
    }

    public List<Race> getRacesFromEvent(final long eventId){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnRaceList = mraceDao.getRacesFromEvent(eventId);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnRaceList;
    }
    public List<Player> getPlayersFromEvent(final Event event){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnPlayerList = mplayerDao.getPlayersFromEvent(event.getId());
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnPlayerList;
    }

    public List<Player> getPlayersFromRace(final Race race){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnPlayerList = mplayerDao.getPlayersFromRace(race.getId());
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnPlayerList;
    }

    public Player getPlayer(final Race race, final boolean isBlue){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                returnPlayer = mplayerDao.getPlayer(race.getId(), isBlue);
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

    public void delete(final Event event){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mEventDao.delete(event);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(final Race race){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mraceDao.delete(race);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(final Player player){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mplayerDao.delete(player);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void databaseSetupData(){
        deleteAllPlayers();
        deleteAllRaces();
        deleteAllEvents();
        long e = insertEvent(new Event("All", 60000));
        Globals.getGlobals().setAllId(e);
//        long p1 = insertPlayer(new Player("Joren", 1445,100,44,1000));
//        long p2 = insertPlayer(new Player("Lin", 1120,521,44,1000));
//        long p3 = insertPlayer(new Player("Jimi", 1004,151,44,1000));
//        long p4 = insertPlayer(new Player("Bart", 905,75,44,1000));
//        long p5 = insertPlayer(new Player("Eva", 847,99,44,1000));
//        long p6 = insertPlayer(new Player("Gorik", 341,14,44,1000));
//        insertRace(new Race(p1, p2, e));
//        insertRace(new Race(p3, p4, e));
//        insertRace(new Race(p5, p6, e));
    }
}

package com.example.android.smergybike.localDatabase;

import android.content.Context;

import com.example.android.smergybike.Player;

import java.util.List;

/**
 * Created by Joren on 12-4-2018.
 */
public class DbModel {

    private PlayerDao mplayerDao;
    private List<Player> returnValue;

    public DbModel(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        mplayerDao = db.playerDao();
    }

    private void setReturnValue(List<Player> value){
        returnValue = value;
    }

    public void insert(final Player player) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mplayerDao.insert(player);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public void deleteAllPlayers(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mplayerDao.deleteAll();
            }
        });
        thread.start();
    }


    public void loadDummyData(){
        deleteAllPlayers();
        insert(new Player("Joren", 1445,0,0,0));
        insert(new Player("Lin", 1120,0,0,0));
        insert(new Player("Jimi", 1004,0,0,0));
        insert(new Player("Bart", 905,0,0,0));
        insert(new Player("Eva", 847,0,0,0));
        insert(new Player("Gorik", 341,0,0,0));
    }
}
